package com.CioffiDeVivo.dietideals.presentation.ui.becomeSeller

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.animations.pulsateClick
import com.CioffiDeVivo.dietideals.data.validations.ValidationState
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.ContactInfo
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.CreditCardComponents
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.presentation.ui.loading.LoadingView
import com.CioffiDeVivo.dietideals.presentation.ui.retry.RetryView

@Composable
fun BecomeSellerView(
    viewModel: BecomeSellerViewModel,
    navController: NavController
){
    val becomeSellerUiState by viewModel.becomeSellerUiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit){
        viewModel.getUserInfo()
    }
    LaunchedEffect(Unit){
        viewModel.validationBecomeSellerEvents.collect { event ->
            when(event){
                is ValidationState.Success -> {
                }
                else -> { Toast.makeText(context, "Invalid Field", Toast.LENGTH_SHORT).show() }
            }
        }
    }

    when(becomeSellerUiState){
        is BecomeSellerUiState.Loading -> { LoadingView() }
        is BecomeSellerUiState.Error -> { RetryView(
            onClick = {
                navController.popBackStack()
                navController.navigate(Screen.BecomeSeller.route)
            }
        )}
        is BecomeSellerUiState.Success -> {
            Toast.makeText(context, "For safety Log In Again", Toast.LENGTH_SHORT).show()
            if (navController.currentBackStackEntry?.destination?.route != Screen.LogInCredentials.route) {
                navController.navigate(Screen.LogInCredentials.route) {
                    popUpTo(0){ inclusive = true }
                }
            }
        }
        is BecomeSellerUiState.BecomeSellerParams -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ){
                ContactInfo(
                    userState = becomeSellerUiState,
                    viewModel = viewModel
                )
                if((becomeSellerUiState as BecomeSellerUiState.BecomeSellerParams).user.creditCards.isEmpty()){
                    CreditCardComponents(
                        userState = becomeSellerUiState,
                        viewModel = viewModel
                    )
                }
                Button(
                    onClick = { viewModel.becomeSellerOnAction(BecomeSellerEvents.Submit) },
                    modifier = Modifier
                        .size(width = 330.dp, height = 50.dp)
                        .pulsateClick(),
                    content = {
                        Text(stringResource(R.string.becomeSeller), fontSize = 20.sp)
                    }
                )
            }
        }
    }
}