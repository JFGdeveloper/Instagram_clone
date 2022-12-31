package com.jfgdeveloper.instagramclone.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.navigation.NavController
import com.jfgdeveloper.instagramclone.R
import com.jfgdeveloper.instagramclone.Screens
import com.jfgdeveloper.instagramclone.main.CheckSignedIn
import com.jfgdeveloper.instagramclone.main.MyProgressBar
import com.jfgdeveloper.instagramclone.main.navigateTo
import com.jfgdeveloper.instagramclone.presentation.screens.auth.IgViewModel

@Composable
fun SigUpScreen(controller: NavController, vm: IgViewModel) {

    // navega a la pantalla directa como ejm home
    CheckSignedIn(controller = controller, vm = vm)

    var userNameState by remember { mutableStateOf("")}
    var emailState by remember { mutableStateOf("")}
    var passState by remember { mutableStateOf("")}
    val focus = LocalFocusManager.current

    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .verticalScroll(rememberScrollState()),
               horizontalAlignment = Alignment.CenterHorizontally
        ) {
            
            Image(
                    painter = painterResource(id = R.drawable.ig_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .width(250.dp)
                        .padding(top = 16.dp)
                        .padding(8.dp)
            )
            
            Text(
                    text = "SignUp",
                    modifier = Modifier.padding(top = 8.dp),
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 30.sp
            )

            OutlinedTextField(value = userNameState, onValueChange = { userNameState = it }, label = { Text(
                    text = "Username", modifier = Modifier.padding(8.dp)
            )})
            OutlinedTextField(value = emailState, onValueChange = { emailState = it }, label = { Text(
                    text = "Email", modifier = Modifier.padding(8.dp)
            )})
            OutlinedTextField(
                    value = passState,
                    onValueChange = { passState = it },
                    label = { Text(text = "Pass")},
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.padding(8.dp)
            )
            
            Button(onClick = {
                focus.clearFocus(true)
                             vm.onSingUp(
                                 userNameState,
                                 emailState,
                                 passState
                             )
            }, modifier = Modifier.padding(8.dp)) {
                Text(text = "SignUp")
            }
            
            Text(
                    text = "¿Already a count, Go to Login -> ",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            navigateTo(controller, Screens.Login)
                        },
                    color = Color.Blue
            )

        }//column
        if (vm.inProgress){
            MyProgressBar()
        }
    }//box


}