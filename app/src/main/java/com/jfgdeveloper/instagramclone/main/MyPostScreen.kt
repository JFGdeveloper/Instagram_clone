package com.jfgdeveloper.instagramclone.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jfgdeveloper.instagramclone.presentation.screens.auth.IgViewModel

@Composable
fun MyPostScreen(controller: NavController,vm: IgViewModel) {
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
                onClick = {},
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
}

@Composable
fun ProfileImage(img: String?, onClick: ()-> Unit) {

}