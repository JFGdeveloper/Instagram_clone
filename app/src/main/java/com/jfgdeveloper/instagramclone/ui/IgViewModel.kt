package com.jfgdeveloper.instagramclone.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.jfgdeveloper.instagramclone.data.Event
import com.jfgdeveloper.instagramclone.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

const val USERS = "users"

@HiltViewModel
class IgViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
): ViewModel() {

    var signedIn by mutableStateOf(false)
    var inProgress by mutableStateOf(false)
    val userData by mutableStateOf<UserData?>(null)
    var popupNotification by mutableStateOf<Event<String>?>(null) // la uso para el event


    fun onSingUp(userName: String, email: String, pass: String) {

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
                            //todo creamos el user
                        }else{
                            handledException(task.exception,"SignUp Failed")
                        }
                        inProgress = false

                    }
                }

            }.addOnFailureListener{}

    }

    // creamos un funcion para mostrar excepciones
    fun handledException(exception: Exception? = null, custommessage: String = ""){
        exception?.printStackTrace()
        val error = exception?.localizedMessage ?: ""
        val message = if (custommessage.isEmpty()) error else "$custommessage: $error"
        popupNotification = Event(message)
    }
}