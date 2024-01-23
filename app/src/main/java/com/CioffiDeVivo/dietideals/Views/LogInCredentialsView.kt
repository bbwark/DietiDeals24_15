package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.Components.ViewTitle
import com.CioffiDeVivo.dietideals.Components.pulsateClick
import com.CioffiDeVivo.dietideals.DataModels.LoginEvent
import com.CioffiDeVivo.dietideals.DataModels.User
import com.CioffiDeVivo.dietideals.DietiDealsViewModel
import com.CioffiDeVivo.dietideals.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LogInCredentialsView(viewModel: DietiDealsViewModel, navController: NavController){

    val userLoginState by viewModel.userState.collectAsState()
    val isEnabled by remember { mutableStateOf(true) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        ViewTitle(title = stringResource(id = R.string.welcome))
        Spacer(modifier = Modifier.height(30.dp))
        InputTextField(
            value = userLoginState.email,
            onValueChanged = { viewModel.loginAction(LoginEvent.EmailChanged(it)) },
            label = stringResource(R.string.email)
        )
        InputTextField(
            value = userLoginState.password,
            onValueChanged = { viewModel.loginAction(LoginEvent.PasswordChanged(it)) },
            label = stringResource(R.string.password)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { /*TODO*/ },
            enabled = if(viewModel.user.email.isNotEmpty() && viewModel.user.password.isNotEmpty()){
                                                                     isEnabled
                                                                     }else{
                                                                          !isEnabled
                                                                          },
            modifier = Modifier
                .size(width = 330.dp, height = 50.dp)
                .pulsateClick(),
            content = {
                Text(stringResource(R.string.logIn), fontSize = 20.sp)
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextButton(onClick = { /*TODO*/ }) {
            Text(
                "Reset password",
            )
        }
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                "Do you have not an Account? ",

                )
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    "Create an Account",
                )
            }
        }
    }
}

@Composable
fun LogInCredentialsComposables(
    user: User
){

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = user.email,
        onValueChange = { user.email = it },
        singleLine = true,
        trailingIcon = {
            Icon(
                Icons.Rounded.Clear,
                contentDescription = null,
                modifier = Modifier.clickable{user.email = ""}
            )
        },
        modifier = Modifier.width(320.dp),
        label = { Text("Email") },
    )
    Spacer(modifier = Modifier.height(10.dp))
    OutlinedTextField(
        value = user.password,
        onValueChange = { user.password = it },
        label = { Text("Password") },
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if(passwordVisible){
                Icons.Filled.Visibility
            }else{
                Icons.Filled.VisibilityOff
            }
            val description = if (passwordVisible) "Hide password" else "Show password"
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector  = image, description)
            }
        },
        modifier = Modifier.width(320.dp),
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun LogInCredentialsPreview(){
    LogInCredentialsView(viewModel = DietiDealsViewModel(), navController = rememberNavController())
}