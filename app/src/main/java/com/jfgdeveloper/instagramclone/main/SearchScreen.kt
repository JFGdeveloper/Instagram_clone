package com.jfgdeveloper.instagramclone.main

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jfgdeveloper.instagramclone.presentation.screens.auth.IgViewModel

@Composable
fun SearchScreen(controller: NavController,vm: IgViewModel) {
    Log.d("javi","oncreate SearchScreen")
    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "SEARCH SCREEN")
        }
        BottomNavigationMenu(controller = controller, itemSelected = BottomNavigationItem.SEARCH)
    }

}