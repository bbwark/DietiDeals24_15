package com.CioffiDeVivo.dietideals.presentation.ui.loginCredentials

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.InputTextField
import com.CioffiDeVivo.dietideals.animations.pulsateClick
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.domain.validations.ValidationState
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.modifierStandard

@Composable
fun LogInCredentialsView(viewModel: LogInCredentialsViewModel, navController: NavHostController) {

    val userLoginState by viewModel.userLogInState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.validationLogInEvent.collect { event ->
            when (event) {
                is ValidationState.Success -> {
                    Toast.makeText(context, "Successful Log In", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    Toast.makeText(context, "Invalid Field", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginInputs(
            userState = userLoginState,
            onEmailChange = { viewModel.loginOnAction(LoginEvent.EmailChanged(it)) },
            onPasswordChange = { viewModel.loginOnAction(LoginEvent.PasswordChanged(it)) },
            onDeleteEmail = { viewModel.loginOnAction(LoginEvent.EmailDeleted(it)) }
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { viewModel.loginOnAction(LoginEvent.Submit()) },
            modifier = Modifier
                .size(width = 330.dp, height = 50.dp)
                .pulsateClick(),
            content = {
                Text(stringResource(R.string.logIn), fontSize = 20.sp)
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Do you have not an Account? ",

                )
            TextButton(
                onClick = {
                    /* TODO navigate to register view*/
                }
            ) {
                Text(
                    "Create an Account",
                )
            }
        }
    }
}
@Composable
fun LoginInputs(
    userState: LogInState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onDeleteEmail: (String) -> Unit
){
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    InputTextField(
        value = userState.email,
        onValueChanged = { onEmailChange(it) },
        label = stringResource(R.string.email),
        isError = userState.emailErrorMsg != null,
        trailingIcon = Icons.Filled.Clear,
        onTrailingIconClick = { onDeleteEmail(it) },
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

@Preview(showBackground = true)
@Composable
fun LogInCredentialsPreview(){
    LogInCredentialsView(viewModel = LogInCredentialsViewModel(application = Application()), navController = rememberNavController())
}