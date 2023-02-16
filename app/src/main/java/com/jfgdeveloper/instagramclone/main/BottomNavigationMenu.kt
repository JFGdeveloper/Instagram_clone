package com.jfgdeveloper.instagramclone.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jfgdeveloper.instagramclone.R
import com.jfgdeveloper.instagramclone.Screens

enum class BottomNavigationItem(val icon: Int, val screens: Screens){
    POST(R.drawable.ic_post,Screens.MyPost),
    FEED(R.drawable.ic_home,Screens.Feed),
    SEARCH(R.drawable.ic_search,Screens.Search)
}


@Composable
fun BottomNavigationMenu(controller: NavController, itemSelected: BottomNavigationItem) {
    Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(4.dp)
                .background(MaterialTheme.colors.primary)
    ) {
        for (item in BottomNavigationItem.values()){
            Image(
                    painter = painterResource(id = item.icon),
                    contentDescription = null,
                    modifier = Modifier.padding(5.dp).size(40.dp).weight(1f)
                        .clickable {
                            navigateTo(controller,item.screens)
                        },
                    colorFilter = if (itemSelected == item) ColorFilter.tint(Color.Black)
                    else ColorFilter.tint(Color.Gray)
            )


        }

    }
}