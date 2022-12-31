package com.jfgdeveloper.instagramclone.main

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.jfgdeveloper.instagramclone.Screens
import com.jfgdeveloper.instagramclone.presentation.screens.auth.IgViewModel

@Composable
fun NotificationMessage(vm: IgViewModel) {
    val stateNotification = vm.popupNotification
    val messageNotification = stateNotification?.getContentOrNull()

    if (messageNotification != null){
        Toast.makeText(LocalContext.current,messageNotification,Toast.LENGTH_LONG).show()
    }else{
        Log.d("javi","la notification es nula")

    }

}

@Composable
fun MyProgressBar() {
    Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray.copy(alpha = 0.5f)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}


fun navigateTo(navController: NavController,screens: Screens) {
    navController.navigate(screens.route){
        popUpTo(screens.route)
        launchSingleTop= true
    }

}

@Composable
fun CheckSignedIn(controller: NavController,vm: IgViewModel){
    var alreadyLoggedIn by remember { mutableStateOf(false)}
    val signed = vm.signedIn

    if (signed && !alreadyLoggedIn){
        alreadyLoggedIn = true
        controller.navigate(Screens.Feed.route){
            popUpTo(0)

        }
    }
}