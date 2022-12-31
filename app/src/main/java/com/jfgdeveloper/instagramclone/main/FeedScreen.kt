package com.jfgdeveloper.instagramclone.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.jfgdeveloper.instagramclone.Screens
import com.jfgdeveloper.instagramclone.presentation.screens.auth.IgViewModel

@Composable
fun FeedScreen(controller: NavHostController, vm: IgViewModel) {
   Column(modifier = Modifier.fillMaxSize()) {
       Column(modifier = Modifier.weight(1f)) {
           Text(text = "Feed Screen")
       }
       BottomNavigationMenu(controller = controller, itemSelected = BottomNavigationItem.FEED)
   }
}