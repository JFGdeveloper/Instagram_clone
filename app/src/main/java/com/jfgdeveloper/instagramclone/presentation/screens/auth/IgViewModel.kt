package com.jfgdeveloper.instagramclone.presentation.screens.auth

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import com.jfgdeveloper.instagramclone.data.Event
import com.jfgdeveloper.instagramclone.data.PostData
import com.jfgdeveloper.instagramclone.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

const val USERS = "users"
const val POST = "post"

@HiltViewModel
class IgViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage,
): ViewModel() {

    var signedIn by mutableStateOf(false)
    var inProgress by mutableStateOf(false)
    var userData by mutableStateOf<UserData?>(null)
    var popupNotification by mutableStateOf<Event<String>?>(null) // la uso para el event

    // Lo creamos especifico para la pantalla del pos
    var refreshPostProgress by mutableStateOf(false)
    var posts = mutableStateOf<List<PostData>>(listOf())



    // SIEMPRE VOY A TENER EN EL USERDATA EL USER GRACIAS AL getUserData()
    init {
        Log.d("javi","init igViewModel")
        //auth.signOut()
        val currentUser = auth.currentUser
        signedIn = currentUser != null
        currentUser?.uid?.let {
            getUserData(it)
        }



    }


    fun onSingUp(userName: String, email: String, pass: String) {

        if (userName.isEmpty() or email.isEmpty() or pass.isEmpty()){
            handledException(custommessage = "Los campos no pueden estar vacios")
            return
        }

        // arrancamos el proceso
        inProgress = true

        // accedemos a la db para ver si ya existe el usuario
        db.collection(USERS)
            .whereEqualTo("username",userName)
            .get()
            .addOnSuccessListener {document ->
                // checkeamos que no esta en la db el user
                if (document.size() > 0){
                    handledException(custommessage = "Username already exists")
                    inProgress = false
                }else{
                    auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{ task ->
                        if (task.isSuccessful){
                            signedIn = true
                            createOrUpdateProfile(userName = userName) // ACTUALIZO O CREO EL USER
                            handledException(custommessage = "Ok")
                        }else{
                            // ME COMPRUEBA SI EL LOGIN EXISTE
                            handledException(task.exception,"SignUp Failed por un email no valido")

                        }
                        inProgress = false

                    }
                }

            }.addOnFailureListener{}

    }

    fun onLogin(email: String,pass: String){
        if (email.isEmpty() || pass.isEmpty()){
            handledException(custommessage = "Los campos no pueden estar vacios")
            return
        }

        inProgress = true
        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener { task ->
            // SI ESTO ES EXITOSO RELLENO MI USERDATA PARA LA UI
            if (task.isSuccessful){
                auth.currentUser?.uid?.let {
                    handledException(custommessage = "Login successful")
                    getUserData(it) // ACTUALIZA LA VARIABLE USERDATA
                }
                signedIn = true
                inProgress = false
            }else{
                handledException(task.exception,"Login failed en el usercurrent")
                inProgress= false
            }
        }.addOnFailureListener {
            handledException(it,"Login failed con email y pass")
            inProgress = false
        }
    }

    private fun createOrUpdateProfile(
        name: String? = null,
        userName: String? = null,
        bio: String? = null,
        imgUrl: String? = null
    ) {
        // creo el objeto y el ID
        val userId = auth.currentUser?.uid
        val user = UserData(
                userId = userId,
                name = name ?: userData?.name,
                username = userName?: userData?.username,
                bio = bio ?: userData?.bio,
                imageUrl = imgUrl ?: userData?.imageUrl,
                following = userData?.following
        )

        // COMPRUEBO EL ID
        userId?.let { uid->
            inProgress = true

            // SI EXISTE ACTUALIZO  SI NO LO METO EN LA BD
            db.collection(USERS).document(uid).get().addOnSuccessListener { document ->
                if (document.exists()){
                    document.reference.update(user.toMap()).addOnSuccessListener {
                        this.userData = user
                        inProgress = false
                    }.addOnFailureListener{
                        handledException(it,"Cannot update user")
                        inProgress = false

                    }
                    // LO INGRESO EN LA BD
                }else{
                    db.collection(USERS).document(uid).set(user)
                    getUserData(uid)
                    inProgress = false
                }

            }.addOnFailureListener{ exception->
                handledException(exception, "Cannot create user")
                inProgress = false
            }

        }

    }

    private fun getUserData(uid: String) {
        inProgress = true
        db.collection(USERS).document(uid).get().addOnSuccessListener {
            val user = it.toObject<UserData>()
            userData = user
            inProgress = false
            refreshPost()
        }.addOnFailureListener{
            handledException(it,"Cannot retrieve user")
            inProgress = false
        }
    }




    // creamos un funcion para mostrar excepciones
    fun handledException(exception: Exception? = null, custommessage: String = ""){
        exception?.printStackTrace()
        val error = exception?.localizedMessage ?: ""
        val message = if (custommessage.isEmpty()) error else "$custommessage: $error"
        popupNotification = Event(message)
    }


    fun updateProfileUser(name: String,userName: String,bio: String){
        createOrUpdateProfile(name,userName,bio)
    }



    private fun uploadImage(uri: Uri, onSuccess: (Uri)->Unit){
        inProgress = true

        val storageRef = storage.reference
        val uuid= UUID.randomUUID()
        val imageRef = storageRef.child("images/$uuid")
        val uploadTask = imageRef.putFile(uri) // esto la sube a firebase creo

        uploadTask.addOnSuccessListener {
            val resul = it.metadata?.reference?.downloadUrl
            resul?.addOnSuccessListener(onSuccess)
        }.addOnFailureListener{
            handledException(it)
            inProgress = false
        }

    }

    fun uploadProfileImage(uri:Uri){
        uploadImage(uri){
            createOrUpdateProfile(imgUrl = it.toString())
        }
    }


    fun onLogOut(){
        auth.signOut()
        signedIn = false
        userData = null
        popupNotification = Event("Logout")
    }

    // onPostSuccess lo usare para navegar a otra pantalla
    fun onNewPost(uri: Uri,description: String,onPostSuccess: ()->Unit){
        uploadImage(uri = uri){
            onCreatePost(uri,description,onPostSuccess)
        }
    }


    private fun onCreatePost(uri: Uri,description: String,onPostSuccess: () -> Unit){
        inProgress = true
        val currentUi = auth.currentUser?.uid
        val currentUsername = userData?.username
        val currentImage = userData?.imageUrl

        if (currentUi != null){
            val randomUI = UUID.randomUUID()
            val post = PostData(
                    postId = randomUI.toString(),
                    userId = currentUi,
                    username = currentUsername,
                    userImage = currentImage,
                    postImage = uri.toString(),
                    postDescription = description,
                    time = System.currentTimeMillis()
                    )

            db.collection(POST).document(randomUI.toString()).set(post).addOnSuccessListener {
                popupNotification = Event("nuevo post: success")
                inProgress = false
                refreshPost()
                onPostSuccess()
            }.addOnFailureListener{
                handledException(custommessage = "Error: ${it.message}")
                inProgress = false
            }

        }else{
            handledException(custommessage = "Error: unavailable user data")
            onLogOut()
            inProgress = false
        }
    }// oncreate post

    private fun refreshPost(){
        val currentUi = auth.currentUser?.uid
        if (currentUi != null){
            refreshPostProgress = true
            // saco todos los post con el id del usuario y lo paso a mi lista de arriba
            db.collection(POST).whereEqualTo("userId",currentUi).get().addOnSuccessListener {
                covertPost(it,posts) // lo convierte a java
                refreshPostProgress = false
            }.addOnFailureListener {
                handledException(custommessage = "ERROR: ${it.message}")
                refreshPostProgress = false
            }


        }else{
            handledException(custommessage = "ERROR: unavailable user ")
            onLogOut()
        }
    }

    private fun covertPost(document: QuerySnapshot,myPost: MutableState<List<PostData>>) {
        val newPost = mutableListOf<PostData>()
        document.forEach { document ->
            val post = document.toObject<PostData>()
            newPost.add(post)
        }
        val sortedPost = newPost.sortedByDescending { it.time }
        myPost.value = sortedPost
    }
}