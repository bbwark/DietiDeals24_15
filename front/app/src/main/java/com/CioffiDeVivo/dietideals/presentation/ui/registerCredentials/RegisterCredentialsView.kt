package com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials

import android.app.Application
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.ContactInfoOnRegisterCredentials
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.CreditCardFieldsOnRegisterCredentials
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.PersonalInfoOnRegisterCredentials
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.ViewTitle
import com.CioffiDeVivo.dietideals.animations.pulsateClick
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.domain.validations.ValidationState

val modifierStandard: Modifier = Modifier
    .fillMaxWidth()
    .padding(start = 30.dp, end = 30.dp)
@Composable
fun RegisterCredentialsView(viewModel: RegisterCredentialsViewModel, navController: NavHostController){

    val userRegistrationState by viewModel.userRegistrationState.collectAsState()
    var isSeller by remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(key1 = context){
        viewModel.validationRegistrationEvent.collect { event ->
            when(event){
                is ValidationState.Success -> {
                    Toast.makeText(context, "Correct Registration", Toast.LENGTH_SHORT).show()
                }

                else -> { Toast.makeText(context, "Invalid Field", Toast.LENGTH_SHORT).show() }
            }
        }
    }

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
            userRegistrationState = userRegistrationState,
            onEmailChange = { viewModel.registrationAction(RegistrationEvent.EmailChanged(it)) },
            onNameChange = { viewModel.registrationAction(RegistrationEvent.NameChanged(it)) },
            onSurnameChange = { viewModel.registrationAction(RegistrationEvent.SurnameChanged(it)) },
            onPasswordChange = { viewModel.registrationAction(RegistrationEvent.PasswordChanged(it)) },
            onNewPasswordChange = { viewModel.registrationAction(RegistrationEvent.RetypePasswordChanged(it)) },
            onDeleteEmail = { viewModel.registrationAction(RegistrationEvent.EmailDeleted(it)) },
            onDeleteName = { viewModel.registrationAction(RegistrationEvent.NameDeleted(it)) },
            onDeleteSurname = { viewModel.registrationAction(RegistrationEvent.SurnameDeleted(it)) }
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
                    viewModel.registrationAction(RegistrationEvent.SellerChange(it))
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
            ContactInfoOnRegisterCredentials(
                userState = userRegistrationState,
                onAddressChange = { viewModel.registrationAction(RegistrationEvent.AddressChanged(it)) },
                onZipCodeChange = { viewModel.registrationAction(RegistrationEvent.ZipCodeChanged(it)) },
                onCountryChange = { viewModel.registrationAction(RegistrationEvent.CountryChanged(it)) },
                onPhoneNumberChange = { viewModel.registrationAction(RegistrationEvent.PhoneNumberChanged(it)) },
                onDeleteAddress = { viewModel.registrationAction(RegistrationEvent.AddressDeleted(it)) },
                onDeleteZipCode = { viewModel.registrationAction(RegistrationEvent.AddressDeleted(it)) },
                onDeletePhoneNumber = { viewModel.registrationAction(RegistrationEvent.AddressDeleted(it)) }
            )
            CreditCardFieldsOnRegisterCredentials(
                userState = userRegistrationState,
                onNumberChange = { viewModel.registrationAction(RegistrationEvent.CreditCardNumberChanged(it)) },
                onDateChange = { viewModel.registrationAction(RegistrationEvent.ExpirationDateChanged(it)) },
                onCvvChange = { viewModel.registrationAction(RegistrationEvent.CvvChanged(it)) },
                onIbanChange = { viewModel.registrationAction(RegistrationEvent.IbanChanged(it)) },
                onDeleteCardNumber = { viewModel.registrationAction(RegistrationEvent.AddressDeleted(it)) },
                onDeleteExpirationDate = { viewModel.registrationAction(RegistrationEvent.ExpirationDateDeleted(it)) },
                onDeleteCvv = { viewModel.registrationAction(RegistrationEvent.CvvDeleted(it)) },
                onDeleteIban = { viewModel.registrationAction(RegistrationEvent.AddressDeleted(it)) }
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { viewModel.registrationAction(RegistrationEvent.Submit()) },
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

@Preview(showBackground = true)
@Composable
fun RegisterCredentialsPreview(){
    RegisterCredentialsView(viewModel = RegisterCredentialsViewModel(application = Application()), navController = rememberNavController())
}

