package com.jfgdeveloper.instagramclone.presentation.screens.auth

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jfgdeveloper.instagramclone.Screens
import com.jfgdeveloper.instagramclone.main.CommonImage
import com.jfgdeveloper.instagramclone.main.CustomDivider
import com.jfgdeveloper.instagramclone.main.MyProgressBar
import com.jfgdeveloper.instagramclone.main.navigateTo

@Composable
fun ProfileScreen(controller: NavController,vm: IgViewModel) {
    Log.d("javi","oncreate ProfileScreen")
    val isLoading = vm.inProgress
    if (isLoading){
        MyProgressBar()
    }else{

        val userData = vm.userData
        val name = rememberSaveable{ mutableStateOf(userData?.name ?: "") }
        val username = rememberSaveable{ mutableStateOf(userData?.username ?: "") }
        val bio = rememberSaveable{ mutableStateOf(userData?.bio ?: "") }

        ProfileContent(
                vm = vm,
                name = name.value,
                username = username.value,
                bio = bio.value,
                onNameChange = {name.value = it},
                onUsernameChange = { username.value = it},
                onBioChange = { bio.value = it},
                onBack = { navigateTo(controller,Screens.MyPost) },
                onSave = {vm.updateProfileUser(name = name.value, userName = username.value,bio= bio.value)},
                onLogout = {
                    vm.onLogOut()
                    navigateTo(controller,Screens.Login)
                }

        )
        
    }

}


@Composable
fun ProfileContent(
    vm: IgViewModel,
    name: String,
    username: String,
    bio: String,
    onNameChange: (String)->Unit,
    onUsernameChange: (String)->Unit,
    onBioChange: (String)-> Unit,
    onSave: ()-> Unit,
    onBack: ()-> Unit,
    onLogout: ()-> Unit
) {
    val rememberScroll = rememberScrollState()
    val imgUrl = vm.userData?.imageUrl
    
    Column(modifier = Modifier
        .verticalScroll(rememberScroll)
        .padding(8.dp)) {
        
        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Back", modifier = Modifier.clickable { onBack() })
            Text(text = "Save", modifier = Modifier.clickable { onSave() })
        }

        CustomDivider()

        ProfileImage(imageUrl = imgUrl, vm = vm)


        CustomDivider()

        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "name", modifier = Modifier.width(100.dp))
            TextField(value = name, onValueChange = onNameChange, colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
            ))
        }
        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Username", modifier = Modifier.width(100.dp))
            TextField(value = username, onValueChange = onUsernameChange, colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
            ))
        }
        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.Top
        ) {
            Text(text = "Bio", modifier = Modifier.width(100.dp))
            TextField(value = bio, onValueChange = onBioChange, colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.LightGray.copy(alpha = 0.4f),
                    textColor = Color.Black),
                      singleLine = false,
                      modifier = Modifier.height(150.dp)
            )
        }

        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Logout", modifier = Modifier.clickable { onLogout() })
        }
    }
    
}

@Composable
fun ProfileImage(imageUrl: String?, vm: IgViewModel){

    val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = {it ->
                it?.let {
                    vm.uploadProfileImage(uri = it)
                }

            }
    )

    Box(modifier = Modifier.height(IntrinsicSize.Min)){
        Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable {launcher.launch("image/*") },
                horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Card(modifier = Modifier.size(100.dp), shape = CircleShape) {
                CommonImage(data = imageUrl)
            }
            Text(text = "Change image picture")

        }
        val isLoading = vm.inProgress
        if (isLoading) MyProgressBar()
    }
}