package com.jfgdeveloper.instagramclone.main

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jfgdeveloper.instagramclone.R
import com.jfgdeveloper.instagramclone.Screens
import com.jfgdeveloper.instagramclone.presentation.screens.auth.IgViewModel

@Composable
fun MyPostScreen(controller: NavController,vm: IgViewModel) {
    Log.d("javi","oncreate MypostScreen")
    val userData = vm.userData
    val isLoading = vm.inProgress

    Column() {
        Column() {
            Row() {
                ProfileImage(img = userData?.imageUrl) {}
                Text(
                        text = "15\nPost",
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                        textAlign = TextAlign.Center
                )
                Text(
                        text = "15\nFollowers",
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                        textAlign = TextAlign.Center
                )
                Text(
                        text = "15\nFollowing",
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                        textAlign = TextAlign.Center
                )
            }

            Column(modifier = Modifier.padding(8.dp)) {
                val usernameDisplay = if (userData?.username == null) "" else "@${userData.username}"
                Text(text = userData?.name ?: "", fontWeight = FontWeight.Bold)
                Text(text = usernameDisplay)
                Text(text = userData?.bio ?: "")
            }

        }

        
        OutlinedButton(
                onClick = { navigateTo(controller,Screens.Profile) },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                elevation = ButtonDefaults.elevation(),
                shape = RoundedCornerShape(10)
        ) {
            Text(text = "Edit Profile", color = Color.Black)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Post list")
        }
        BottomNavigationMenu(controller = controller, itemSelected = BottomNavigationItem.POST)
    }

    if (isLoading) CircularProgressIndicator()
}

@Composable
fun ProfileImage(img: String?, onClick: ()-> Unit) {

    Box(modifier = Modifier
        .padding(16.dp)
        .clickable { onClick() }){
        UserImageCard(userImg = img, modifier = Modifier
            .padding(3.dp)
            .size(80.dp))

        Card(
                modifier = Modifier
                    .size(32.dp)
                    .padding(3.dp)
                    .align(Alignment.BottomEnd),
                shape = CircleShape,
                border = BorderStroke(2.dp, color = Color.White),

                ) {
            Image(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = null,
                    modifier =Modifier.background(color = Color.Blue),
                    colorFilter = ColorFilter.tint(Color.Black)
            )
        }




    }

}