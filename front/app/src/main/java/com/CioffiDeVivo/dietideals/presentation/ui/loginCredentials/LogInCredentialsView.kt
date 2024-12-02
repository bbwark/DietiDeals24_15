package com.CioffiDeVivo.dietideals.presentation.ui.loginCredentials

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.InputTextField
import com.CioffiDeVivo.dietideals.animations.pulsateClick
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.data.validations.ValidationState
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.presentation.ui.loading.LoadingView
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.modifierStandard
import com.CioffiDeVivo.dietideals.presentation.ui.retry.RetryView

@Composable
fun LogInCredentialsView(viewModel: LogInCredentialsViewModel, navController: NavController) {

    val loginUiState by viewModel.logInCredentialsUiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit){
        viewModel.setUiEmailState()
    }

    LaunchedEffect(Unit) {
        viewModel.validationLogInEvent.collect { event ->
            when (event) {
                is ValidationState.Success -> {
                }
                else -> {
                    Toast.makeText(context, "Invalid Field", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    when(loginUiState){
        is LogInCredentialsUiState.Loading -> LoadingView()
        is LogInCredentialsUiState.Error -> RetryView(onClick = {
            navController.popBackStack()
            navController.navigate(Screen.LogInCredentials.route)
        })
        is LogInCredentialsUiState.Success -> {
            if(navController.currentBackStackEntry?.destination?.route != Screen.Home.route){
                navController.navigate(Screen.Home.route){
                    popUpTo(navController.graph.startDestinationId){ inclusive = true }
                }
            }
        }
        is LogInCredentialsUiState.LogInParams -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LoginInputs(
                    userState = loginUiState,
                    onEmailChange = { viewModel.loginOnAction(LoginEvent.EmailChanged(it)) },
                    onPasswordChange = { viewModel.loginOnAction(LoginEvent.PasswordChanged(it)) },
                    onDeleteEmail = { viewModel.loginOnAction(LoginEvent.EmailDeleted) }
                )
                Button(
                    onClick = { viewModel.loginOnAction(LoginEvent.Submit) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 25.dp, end = 25.dp, bottom = 8.dp)
                        .height(50.dp)
                        .pulsateClick(),
                    content = {
                        Text(stringResource(R.string.logIn), fontSize = 20.sp)
                    }
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifierStandard
                ) {
                    Text(
                        text = "Do you have not an Account? ",
                        fontSize = 12.sp
                    )
                    TextButton(onClick = { navController.navigate(Screen.Register.route) }) {
                        Text(
                            text = "Create an Account",
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun LoginInputs(
    userState: LogInCredentialsUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onDeleteEmail: (String) -> Unit
){
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    InputTextField(
        value = (userState as LogInCredentialsUiState.LogInParams).email,
        onValueChanged = { onEmailChange(it) },
        label = stringResource(R.string.email),
        isError = userState.emailErrorMsg != null,
        trailingIcon = Icons.Filled.Clear,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        onTrailingIconClick = { onDeleteEmail(it) },
        supportingText = userState.emailErrorMsg,
        modifier = modifierStandard
    )
    InputTextField(
        value = userState.password,
        onValueChanged = { onPasswordChange(it) },
        label = stringResource(R.string.password),
        isError = userState.passwordErrorMsg != null,
        onTrailingIconClick = { passwordVisible = !passwordVisible },
        supportingText = userState.passwordErrorMsg,
        visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = if(passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
        modifier = modifierStandard
    )
}