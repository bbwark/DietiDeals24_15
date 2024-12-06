 package com.CioffiDeVivo.dietideals.presentation.ui.register

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
fun RegisterView(
     viewModel: RegisterViewModel,
     navController: NavController
) {
    val registerUiState by viewModel.registerUiState.collectAsState()

     when(registerUiState){
         is RegisterUiState.Loading -> LoadingView()
         is RegisterUiState.Error -> RetryView {
             navController.popBackStack()
             navController.navigate(Screen.Register.route)
         }
         is RegisterUiState.SuccessWithGoogle -> {
             if(navController.currentBackStackEntry?.destination?.route != Screen.Home.route){
                 navController.navigate(Screen.Home.route){
                     popUpTo(navController.graph.startDestinationId){ inclusive = true }
                 }
             }
         }
         is RegisterUiState.Success -> {
             Column(
                 horizontalAlignment = Alignment.CenterHorizontally,
                 modifier = Modifier
                     .fillMaxSize()
                     .padding(25.dp)
             ) {
                 ViewTitle(title = stringResource(id = R.string.createAccount))
                 Spacer(modifier = Modifier.weight(1f))
                 Button(
                     onClick = {
                         if (navController.currentBackStackEntry?.destination?.route != Screen.RegisterCredentials.route) {
                             navController.navigate(Screen.RegisterCredentials.route)
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
                 Text("or",
                     fontFamily = FontFamily.SansSerif,
                     fontWeight = FontWeight.Medium
                 )
                 Spacer(modifier = Modifier.height(5.dp))
                 GoogleButton(loginWithGoogle = { viewModel.loginWithGoogle(it) })
                 Spacer(modifier = Modifier.height(5.dp))
                 TextButton(
                     onClick = {
                         if (navController.currentBackStackEntry?.destination?.route != Screen.Login.route) {
                             navController.navigate(Screen.Login.route) {
                                 launchSingleTop = true
                             }
                         }
                     }
                 ) {
                     Text(
                         "Log In",
                     )
                 }
             }
         }
     }

}