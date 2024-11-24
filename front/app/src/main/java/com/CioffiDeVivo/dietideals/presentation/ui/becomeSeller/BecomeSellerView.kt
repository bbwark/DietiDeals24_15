package com.CioffiDeVivo.dietideals.presentation.ui.becomeSeller

import android.app.Application
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.animations.pulsateClick
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

    LaunchedEffect(Unit){
        viewModel.getUserInfo()
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
            if (navController.currentBackStackEntry?.destination?.route != Screen.Sell.route) {
                navController.popBackStack()
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
                    onAddressChange = { viewModel.becomeSellerOnAction(BecomeSellerEvents.AddressChanged(it)) },
                    onCountryChange = { viewModel.becomeSellerOnAction(BecomeSellerEvents.CountryChanged(it)) },
                    onZipCodeChange = { viewModel.becomeSellerOnAction(BecomeSellerEvents.ZipCodeChanged(it)) },
                    onPhoneNumberChange = { viewModel.becomeSellerOnAction(BecomeSellerEvents.PhoneNumberChanged(it)) },
                    onDeleteAddress = { viewModel.becomeSellerOnAction(BecomeSellerEvents.AddressDeleted(it)) },
                    onDeleteZipCode = { viewModel.becomeSellerOnAction(BecomeSellerEvents.ZipCodeDeleted(it)) },
                    onDeletePhoneNumber = { viewModel.becomeSellerOnAction(BecomeSellerEvents.PhoneNumberDeleted(it)) }
                )
                CreditCardComponents(
                    userState = becomeSellerUiState,
                    onNumberChange = { viewModel.becomeSellerOnAction(BecomeSellerEvents.CreditCardNumberChanged(it)) },
                    onDateChange = { viewModel.becomeSellerOnAction(BecomeSellerEvents.ExpirationDateChanged(it)) },
                    onCvvChange = { viewModel.becomeSellerOnAction(BecomeSellerEvents.CvvChanged(it)) },
                    onIbanChange = { viewModel.becomeSellerOnAction(BecomeSellerEvents.IbanChanged(it)) },
                    onDeleteCardNumber = { viewModel.becomeSellerOnAction(BecomeSellerEvents.CreditCardNumberDeleted(it)) },
                    onDeleteExpirationDate = { viewModel.becomeSellerOnAction(BecomeSellerEvents.ExpirationDateDeleted(it)) },
                    onDeleteCvv = { viewModel.becomeSellerOnAction(BecomeSellerEvents.CvvDeleted(it)) },
                    onDeleteIban = { viewModel.becomeSellerOnAction(BecomeSellerEvents.IbanDeleted(it)) }
                )
                Button(
                    onClick = { viewModel.becomeSellerOnAction(BecomeSellerEvents.Submit()) },
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