package com.jfgdeveloper.instagramclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jfgdeveloper.instagramclone.di.application.InstagramClone
import com.jfgdeveloper.instagramclone.ui.IgViewModel
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
}

@Composable
fun InstagramApp() {
    val vm = hiltViewModel<IgViewModel>()
    val controller = rememberNavController()

    NavHost(navController = controller, startDestination = Screens.SigUp.route){

        composable(Screens.SigUp.route){
            SigUpScreen(controller = controller, viewModel = vm)
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