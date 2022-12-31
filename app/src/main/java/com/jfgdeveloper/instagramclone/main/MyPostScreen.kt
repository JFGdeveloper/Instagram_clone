package com.jfgdeveloper.instagramclone.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jfgdeveloper.instagramclone.presentation.screens.auth.IgViewModel

@Composable
fun MyPostScreen(controller: NavController,vm: IgViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "MY POST")
        }
        BottomNavigationMenu(controller = controller, itemSelected = BottomNavigationItem.POST)
    }
}