package com.jfgdeveloper.instagramclone.main

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jfgdeveloper.instagramclone.R
import com.jfgdeveloper.instagramclone.Screens
import com.jfgdeveloper.instagramclone.data.PostData
import com.jfgdeveloper.instagramclone.presentation.screens.auth.IgViewModel

data class PostRow(
    var post1: PostData? = null,
    var post2: PostData? = null,
    var post3: PostData? = null
){
    fun isFull() = post1 !=null && post2 != null && post3 != null
    fun addPost(post: PostData){
        if (post1 == null){
            post1 = post
        }else if (post2 == null){
            post2 = post
        }else if (post3 == null){
            post3 = post
        }
    }
}

@Composable
fun MyPostScreen(controller: NavController,vm: IgViewModel) {

    val laucher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = {
                val encode= Uri.encode(it.toString())
                val route = Screens.NewPost.crateRout(encode)
                controller.navigate(route)
            }
    )

    Log.d("javi","oncreate MypostScreen")
    val userData = vm.userData
    val isLoading = vm.inProgress
    val postLoading = vm.refreshPostProgress
    val posts = vm.posts.value

    Column() {
        Column() {
            Row() {
                ProfileImage(img = userData?.imageUrl) {
                    laucher.launch("image/*")
                }
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

        PostList(
                isContextLoading = isLoading,
                postLoading = postLoading,
                posts = posts,
                modifier = Modifier.weight(1f).padding(1.dp).fillMaxSize(),
                onPostClick ={post ->
                    navigateTo(
                            navController = controller,
                            screens = Screens.SinglePost,
                            NavParam("post",post)
                    )
                }
        )

        BottomNavigationMenu(controller = controller, itemSelected = BottomNavigationItem.POST)
    }

    if (isLoading) MyProgressBar()
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


// solucion para la doble list
@Composable
fun PostList(
    isContextLoading: Boolean,
    postLoading: Boolean,
    posts: List<PostData>,
    modifier: Modifier,
    onPostClick: (PostData)->Unit
) {

    if (postLoading){
        MyProgressBar()
    }else if (posts.isEmpty()){
        Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ) {
            if (!isContextLoading) Text(text = "Post unavailable")
        }

    }else{
        LazyColumn(modifier = modifier) {
            // convertir nuestra list de post del viewModel en una postrow()
            val rows = arrayListOf<PostRow>()
            var currentRow = PostRow()
            rows.add(currentRow)
            for (post in posts){
                if (currentRow.isFull()){
                    currentRow = PostRow()
                    rows.add(currentRow)
                }
                currentRow.addPost(post)
            }

            items(items = rows){
                PostRow(item = it, onClick = onPostClick)
            }
        }
    }

}

@Composable
fun PostRow(item: PostRow,onClick: (PostData)->Unit) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)) {
        PostImage(
                imageUrl = item.post1?.postImage,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        item.post1?.let {
                            onClick(it)
                        }
                    }
        )
        PostImage(
                imageUrl = item.post2?.postImage,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        item.post2?.let {
                            onClick(it)
                        }
                    }
        )
        PostImage(
                imageUrl = item.post3?.postImage,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        item.post3?.let {
                            onClick(it)
                        }
                    }
        )
    }

}

@Composable
fun PostImage(imageUrl: String?, modifier: Modifier) {
    Box(modifier = modifier){
        var modifier = Modifier
            .fillMaxSize()
            .padding(1.dp)
        if (imageUrl == null){
            modifier = modifier.clickable(enabled = false) { }
        }

        CommonImage(data = imageUrl, modifier = modifier, contentScale = ContentScale.Crop)
    }

}