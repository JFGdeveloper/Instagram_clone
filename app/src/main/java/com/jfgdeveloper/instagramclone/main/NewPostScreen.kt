package com.jfgdeveloper.instagramclone.main

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.jfgdeveloper.instagramclone.presentation.screens.auth.IgViewModel

@Composable
fun NewPostScreen(viewModel: IgViewModel,navController: NavController,encodeUrl: String) {

    val image by remember{ mutableStateOf(encodeUrl) }
    var description by remember { mutableStateOf("") }
    val scroll = rememberScrollState()
    val focus = LocalFocusManager.current

    Column(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(scroll)) {
        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Cancel", modifier = Modifier.clickable { navController.popBackStack() })
            Text(text = "Post", modifier = Modifier.clickable {
                focus.clearFocus()
                viewModel.onNewPost(
                        uri = Uri.parse(image),
                        description = description,
                        onPostSuccess = {navController.popBackStack()}
                )
            })

        }

        CustomDivider()
        Image(
                painter = rememberImagePainter(data = image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 100.dp),
                contentScale = ContentScale.FillWidth
        )

        Row(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    singleLine = false,
                    label = { Text(text = "Description")},
                    colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            textColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)

            )
        }
    }

    val imProgress = viewModel.inProgress
    if (imProgress) MyProgress()

}