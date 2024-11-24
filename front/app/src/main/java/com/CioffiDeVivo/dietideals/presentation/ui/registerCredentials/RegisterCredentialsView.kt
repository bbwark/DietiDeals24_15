package com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.ContactInfo
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.CreditCardComponents
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.PersonalInfoOnRegisterCredentials
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.ViewTitle
import com.CioffiDeVivo.dietideals.animations.pulsateClick
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.data.validations.ValidationState
import com.CioffiDeVivo.dietideals.presentation.ui.loading.LoadingView
import com.CioffiDeVivo.dietideals.presentation.ui.retry.RetryView

val modifierStandard: Modifier = Modifier
    .fillMaxWidth()
    .padding(start = 25.dp, end = 25.dp, bottom = 8.dp)
@Composable
fun RegisterCredentialsView(viewModel: RegisterCredentialsViewModel, navController: NavHostController){

    val registerCredentialsUiState by viewModel.registerCredentialsUiState.collectAsState()
    var isSeller by remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(key1 = context){
        viewModel.validationRegistrationEvent.collect { event ->
            when(event){
                is ValidationState.Success -> {
                }
                else -> { Toast.makeText(context, "Invalid Field", Toast.LENGTH_SHORT).show() }
            }
        }
    }

    when(registerCredentialsUiState){
        is RegisterCredentialsUiState.Error -> RetryView(onClick = {
            navController.popBackStack()
            navController.navigate(Screen.RegisterCredentials.route)
        })
        is RegisterCredentialsUiState.Loading -> LoadingView()
        is RegisterCredentialsUiState.Success -> { navController.navigate(Screen.Home.route) }
        is RegisterCredentialsUiState.RegisterParams -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                ViewTitle(title = stringResource(id = R.string.createAccount))
                Spacer(modifier = Modifier.height(30.dp))
                PersonalInfoOnRegisterCredentials(
                    userState = registerCredentialsUiState,
                    onEmailChange = { viewModel.registrationAction(RegistrationEvents.EmailChanged(it)) },
                    onNameChange = { viewModel.registrationAction(RegistrationEvents.NameChanged(it)) },
                    onPasswordChange = { viewModel.registrationAction(RegistrationEvents.PasswordChanged(it)) },
                    onNewPasswordChange = { viewModel.registrationAction(RegistrationEvents.RetypePasswordChanged(it)) },
                    onDeleteEmail = { viewModel.registrationAction(RegistrationEvents.EmailDeleted(it)) },
                    onDeleteName = { viewModel.registrationAction(RegistrationEvents.NameDeleted(it)) }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Switch(
                        checked = isSeller,
                        onCheckedChange = {
                            isSeller = it
                            viewModel.registrationAction(RegistrationEvents.SellerChange(it))
                        },
                        thumbContent = {
                            if (isSeller){
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(SwitchDefaults.IconSize)
                                        .pulsateClick(),
                                )
                            }
                        }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(stringResource(R.string.areYouSeller))
                }
                if(isSeller){
                    ContactInfo(
                        userState = registerCredentialsUiState,
                        onAddressChange = { viewModel.registrationAction(RegistrationEvents.AddressChanged(it)) },
                        onCountryChange = { viewModel.registrationAction(RegistrationEvents.CountryChanged(it)) },
                        onZipCodeChange = { viewModel.registrationAction(RegistrationEvents.ZipCodeChanged(it)) },
                        onPhoneNumberChange = { viewModel.registrationAction(RegistrationEvents.PhoneNumberChanged(it)) },
                        onDeleteAddress = { viewModel.registrationAction(RegistrationEvents.AddressDeleted(it)) },
                        onDeleteZipCode = { viewModel.registrationAction(RegistrationEvents.AddressDeleted(it)) },
                        onDeletePhoneNumber = { viewModel.registrationAction(RegistrationEvents.AddressDeleted(it)) }
                    )
                    CreditCardComponents(
                        userState = registerCredentialsUiState,
                        onNumberChange = { viewModel.registrationAction(RegistrationEvents.CreditCardNumberChanged(it)) },
                        onDateChange = { viewModel.registrationAction(RegistrationEvents.ExpirationDateChanged(it)) },
                        onCvvChange = { viewModel.registrationAction(RegistrationEvents.CvvChanged(it)) },
                        onIbanChange = { viewModel.registrationAction(RegistrationEvents.IbanChanged(it)) },
                        onDeleteCardNumber = { viewModel.registrationAction(RegistrationEvents.AddressDeleted(it)) },
                        onDeleteExpirationDate = { viewModel.registrationAction(RegistrationEvents.ExpirationDateDeleted(it)) },
                        onDeleteCvv = { viewModel.registrationAction(RegistrationEvents.CvvDeleted(it)) },
                        onDeleteIban = { viewModel.registrationAction(RegistrationEvents.AddressDeleted(it)) }
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    onClick = { viewModel.registrationAction(RegistrationEvents.Submit()) },
                    modifier = Modifier
                        .size(width = 330.dp, height = 50.dp)
                        .pulsateClick(),
                    content = {
                        Text(stringResource(R.string.createAccount), fontSize = 20.sp)
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text("Do you have an Account? ")
                    TextButton(onClick = { navController.navigate(Screen.Login.route) }) {
                        Text("Log In")
                    }
                }
            }
        }
    }
}

