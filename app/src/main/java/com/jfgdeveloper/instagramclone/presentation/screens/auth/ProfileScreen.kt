package com.jfgdeveloper.instagramclone.presentation.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jfgdeveloper.instagramclone.Screens
import com.jfgdeveloper.instagramclone.main.CustomDivider
import com.jfgdeveloper.instagramclone.main.MyProgressBar
import com.jfgdeveloper.instagramclone.main.navigateTo

@Composable
fun ProfileScreen(controller: NavController,vm: IgViewModel) {
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
                onSave = {},
                onLogout = {}

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

        Column(modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .background(Color.Gray)) {

        }

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