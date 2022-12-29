package com.jfgdeveloper.instagramclone.main

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.jfgdeveloper.instagramclone.ui.IgViewModel

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