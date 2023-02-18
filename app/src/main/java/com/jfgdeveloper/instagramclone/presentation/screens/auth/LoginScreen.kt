package com.jfgdeveloper.instagramclone.presentation.screens.auth

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jfgdeveloper.instagramclone.R
import com.jfgdeveloper.instagramclone.Screens
import com.jfgdeveloper.instagramclone.main.CheckSignedIn
import com.jfgdeveloper.instagramclone.main.MyProgressBar
import com.jfgdeveloper.instagramclone.main.navigateTo

@Composable
fun LoginScreen(controller: NavHostController, vm: IgViewModel) {
    Log.d("javi","oncreate LoginScreen")

    // navega directo a la pantalla por ejem home
    CheckSignedIn(controller = controller, vm = vm)

    val focus = LocalFocusManager.current

    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .verticalScroll(rememberScrollState()),
               horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var emailState by remember { mutableStateOf("")}
            var passState by remember { mutableStateOf("")}

            Image(
                    painter = painterResource(id = R.drawable.ig_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .width(250.dp)
                        .padding(top = 16.dp)
                        .padding(8.dp)
            )

            Text(
                    text = "Login",
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier.padding(8.dp),
                    fontSize = 32.sp
            )


            OutlinedTextField(
                    value = emailState,
                    onValueChange = { emailState = it },
                    label = { Text(text = "Email", modifier = Modifier.padding(8.dp)) }
            )
            OutlinedTextField(
                    value = passState,
                    onValueChange = { passState = it },
                    label = { Text(text = "Pass")},
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.padding(8.dp)
            )

            Button(onClick = {
                focus.clearFocus(force = true)
                vm.onLogin(email = emailState, pass = passState)
            }, modifier = Modifier.padding(8.dp)) {
                Text(text = "SignUp")
            }

            Text(
                    text = "Already here, go to SingUp-> ",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            navigateTo(navController = controller, screens = Screens.SigUp)
                        },
                    color = Color.Blue
            )


        }// column

        if (vm.inProgress){
            MyProgressBar()
        }

    }//Box


}