package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.Components.DetailsViewTopBar
import com.CioffiDeVivo.dietideals.Components.InputTextField
import com.CioffiDeVivo.dietideals.Components.PasswordsTextfields
import com.CioffiDeVivo.dietideals.Components.pulsateClick
import com.CioffiDeVivo.dietideals.DataModels.User
import com.CioffiDeVivo.dietideals.DietiDealsViewModel
import com.CioffiDeVivo.dietideals.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LogInCredentialsView(viewModel: DietiDealsViewModel, navController: NavHostController){

    val userLoginState by viewModel.userState.collectAsState()

    DetailsViewTopBar(
        caption = stringResource(R.string.welcome),
        destinationRoute = "",
        navController = navController
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Spacer(modifier = Modifier.height(30.dp))
        LoginInputs(
            user = userLoginState,
            onEmailChange = { viewModel.updateEmail(it) },
            onPasswordChange = { viewModel.updatePassword(it) },
            onDeleteEmail = { viewModel.deleteEmail() }
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { /*TODO*/ },
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
fun LoginInputs(
    user: User,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onDeleteEmail: (String) -> Unit
){
    InputTextField(
        value = user.email,
        onValueChanged = { onEmailChange(it) },
        label = stringResource(R.string.email),
        trailingIcon = Icons.Filled.Clear,
        onDelete = { onDeleteEmail(it) },
        modifier = modifierStandard
    )
    PasswordsTextfields(
        user = user,
        onPasswordChange = onPasswordChange,
        label = stringResource(R.string.password),
        supportingText = ""
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun LogInCredentialsPreview(){
    LogInCredentialsView(viewModel = DietiDealsViewModel(), navController = rememberNavController())
}