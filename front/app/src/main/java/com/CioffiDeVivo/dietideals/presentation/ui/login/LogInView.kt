package com.CioffiDeVivo.dietideals.presentation.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.ViewTitle
import com.CioffiDeVivo.dietideals.animations.pulsateClick
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.GoogleButton
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.presentation.ui.loading.LoadingView
import com.CioffiDeVivo.dietideals.presentation.ui.retry.RetryView

@Composable
fun LoginView(viewModel: LogInViewModel ,navController: NavController) {

    val isUserAuthenticated by viewModel.isUserAuthenticated.collectAsState()
    val logInUiState by viewModel.logInUiState.collectAsState()

    LaunchedEffect(isUserAuthenticated){
        if(isUserAuthenticated != null){
            if(isUserAuthenticated == true){
                if (navController.currentBackStackEntry?.destination?.route != Screen.Home.route) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                }
            }
        }
    }

    when(logInUiState){
        is LogInUiState.Loading -> LoadingView()
        is LogInUiState.Error -> RetryView(
            onClick = {
                navController.popBackStack()
                navController.navigate(Screen.Login.route)
            }
        )
        is LogInUiState.SuccessWithGoogle -> {
            if(navController.currentBackStackEntry?.destination?.route != Screen.Home.route){
                navController.navigate(Screen.Home.route){
                    popUpTo(navController.graph.startDestinationId){ inclusive = true }
                }
            }
        }
        is LogInUiState.Success -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp)

            ) {
                ViewTitle(title = stringResource(id = R.string.logInAccount))
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        if (navController.currentBackStackEntry?.destination?.route != Screen.LogInCredentials.route) {
                            navController.navigate(Screen.LogInCredentials.route)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .pulsateClick(),
                    content = {
                        Text(stringResource(R.string.continuewithEmail), fontSize = 20.sp)
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    "or",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                GoogleButton(loginWithGoogle = { viewModel.loginWithGoogle(it) })
                Spacer(modifier = Modifier.height(5.dp))
                TextButton(
                    onClick = {
                        if (navController.currentBackStackEntry?.destination?.route != Screen.Register.route) {
                            navController.navigate(Screen.Register.route){
                                launchSingleTop = true
                            }
                        }
                    }
                ) {
                    Text(
                        "Create an Account",
                    )
                }
            }
        }
    }


}