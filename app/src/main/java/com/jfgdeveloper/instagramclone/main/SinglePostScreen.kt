package com.jfgdeveloper.instagramclone.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.jfgdeveloper.instagramclone.R
import com.jfgdeveloper.instagramclone.data.PostData
import com.jfgdeveloper.instagramclone.presentation.screens.auth.IgViewModel
import kotlinx.coroutines.handleCoroutineException


@Composable
fun SinglePostScreen(controller: NavController,vm: IgViewModel, post: PostData) {
  post.userId?.let { 
      Column(modifier = Modifier
          .fillMaxWidth()
          .wrapContentHeight()
          .padding(8.dp)) {
          Text(text = "Back", modifier = Modifier.clickable { controller.popBackStack() })
          CustomDivider()

          SinglePostDisplay(controller,vm, post)

      }



  }
}


// no necesito column al mostrarlo dentro de una column
@Composable
fun SinglePostDisplay(controller: NavController, vm: IgViewModel, post: PostData) {

    val user = vm.userData
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)){
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Card(shape = CircleShape ,modifier = Modifier
                .size(32.dp)
                .padding(8.dp),
                 border = BorderStroke(1.dp, color = Color.Blue)
            ) {
                Image(painter = rememberImagePainter(data = post.postImage), contentDescription = null)
            }
            Text(text = user?.name ?: "")
            Text(text = ".")

            if (user?.userId == post.userId){
                // no mostrar nada
            }else{
                Text(text = "Follow", color = Color.Blue, modifier = Modifier.clickable {
                    // seguir a usuario
                })
            }
        }

    }// primer box imagen perfil y name

    Box(modifier = Modifier
        .border(
                border = BorderStroke(1.dp, Color.Blue),
                shape = RoundedCornerShape(8.dp)
        )
        .padding(3.dp)){

        val modifier = Modifier
            .fillMaxWidth()
            .height(145.dp)
        CommonImage(
                data = post.postImage,
                modifier = modifier,
                contentScale = ContentScale.FillWidth
        )
    }

    Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
                painter = painterResource(id = R.drawable.ic_favorite),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = Color.Blue),
                modifier = Modifier.size(24.dp)
        )
        Text(text = " ${post.like?.size ?: 0} Likes")
    }

    Row(modifier = Modifier.padding(8.dp)) {
        Text(text = user?.username ?: "", fontWeight = FontWeight.Bold)
        Text(text = post?.postDescription ?: "", modifier = Modifier.padding(start = 8.dp))
    }

    
    Text(text = "10 comments" , color = Color.Gray, modifier = Modifier.padding(8.dp))


}