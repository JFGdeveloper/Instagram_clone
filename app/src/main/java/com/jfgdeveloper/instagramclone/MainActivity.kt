package com.jfgdeveloper.instagramclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jfgdeveloper.instagramclone.main.*
import com.jfgdeveloper.instagramclone.presentation.screens.auth.IgViewModel
import com.jfgdeveloper.instagramclone.presentation.screens.auth.LoginScreen
import com.jfgdeveloper.instagramclone.presentation.screens.auth.ProfileScreen
import com.jfgdeveloper.instagramclone.ui.SigUpScreen
import com.jfgdeveloper.instagramclone.ui.theme.InstagramCloneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstagramCloneTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                ) {
                    InstagramApp()
                }
            }
        }
    }
}

sealed class Screens (val route: String){
    object SigUp: Screens("sigUp")
    object Login: Screens("login")
    object Feed: Screens("feed")
    object MyPost: Screens("myPost")
    object Search: Screens("search")
    object Profile: Screens("profile")
    object NewPost: Screens("newPost/{imageUri}"){
        fun crateRout(uri:String)= "newPost/$uri"
    }
}

@Composable
fun InstagramApp() {
    val vm = hiltViewModel<IgViewModel>()
    val controller = rememberNavController()

    // mostramos el toast
    NotificationMessage(vm = vm)

    NavHost(navController = controller, startDestination = Screens.SigUp.route){

        composable(Screens.SigUp.route){
            SigUpScreen(controller = controller, vm = vm)
        }

        composable(Screens.Login.route){
            LoginScreen(controller = controller, vm = vm)
        }

        composable(Screens.Feed.route){
            FeedScreen(controller = controller, vm = vm)
        }

        composable(Screens.MyPost.route){
           MyPostScreen(controller = controller, vm = vm)
        }

        composable(Screens.Search.route){
            SearchScreen(controller = controller, vm = vm)
        }

        composable(Screens.Profile.route){
            ProfileScreen(controller = controller, vm = vm)
        }

        composable(Screens.NewPost.route){ navBackStackEntry ->
            val imageUri = navBackStackEntry.arguments?.getString("imageUri")
            imageUri?.let {
                NewPostScreen(viewModel = vm, navController = controller, encodeUrl = it)
            }
        }


    }



}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    InstagramCloneTheme {
        InstagramApp()
    }
}