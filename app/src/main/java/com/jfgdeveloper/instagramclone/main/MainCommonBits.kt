package com.jfgdeveloper.instagramclone.main

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.jfgdeveloper.instagramclone.R
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
        controller.navigate(Screens.MyPost.route){
            popUpTo(0)

        }
    }
}


// para poder usarlo en varias necesidades
@Composable
fun CommonImage(
    data:String?,
    modifier: Modifier = Modifier.wrapContentSize(),
    contentScale: ContentScale = ContentScale.Crop
){
    val painter = rememberImagePainter(data = data )
    Image(painter = painter, contentDescription = null, contentScale = contentScale, modifier = modifier)
    if (painter.state is ImagePainter.State.Loading){
        CircularProgressIndicator()
    }

}

@Composable
fun UserImageCard(
    userImg: String?,
    modifier: Modifier = Modifier
        .padding(8.dp)
        .size(64.dp)
) {

    Card(modifier = modifier, shape = CircleShape) {

        if (userImg.isNullOrEmpty()){
            Image(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.Gray)
            )
        }else{
            CommonImage(data = userImg)
        }

    }


}

@Composable
fun MyProgress() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Transparent), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun CustomDivider(modifier: Modifier = Modifier) {
    Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp).alpha(0.3f), thickness = 1.dp, color = Color.Gray)

}