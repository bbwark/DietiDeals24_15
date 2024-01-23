package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
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
import com.CioffiDeVivo.dietideals.Components.InputTextField
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
            label = stringResource(R.string.email),
            trailingIcon = Icons.Filled.Clear,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
        )
        InputTextField(
            value = userLoginState.password,
            onValueChanged = { viewModel.loginAction(LoginEvent.PasswordChanged(it)) },
            label = stringResource(R.string.password),
            trailingIcon = Icons.Filled.Clear,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun LogInCredentialsPreview(){
    LogInCredentialsView(viewModel = DietiDealsViewModel(), navController = rememberNavController())
}