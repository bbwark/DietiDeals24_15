package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CioffiDeVivo.dietideals.Components.ContactInfo
import com.CioffiDeVivo.dietideals.Components.CreditCardFields
import com.CioffiDeVivo.dietideals.Components.InputTextField
import com.CioffiDeVivo.dietideals.DietiDealsViewModel
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.Components.PasswordsTextfields
import com.CioffiDeVivo.dietideals.Components.ViewTitle
import com.CioffiDeVivo.dietideals.Components.pulsateClick
import com.CioffiDeVivo.dietideals.Events.RegistrationEvent
import com.CioffiDeVivo.dietideals.DataModels.UserTest

val modifierStandard: Modifier = Modifier
    .fillMaxWidth()
    .padding(start = 30.dp, end = 30.dp)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterCredentialsView(viewModel: DietiDealsViewModel,){

    val userRegistrationState by viewModel.userState.collectAsState()
    val userCreditCardState by viewModel.creditCardState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        ViewTitle(title = stringResource(id = R.string.createAccount))
        Spacer(modifier = Modifier.height(30.dp))
        PersonalInformation(
            user = userRegistrationState,
            onEmailChange = { viewModel.registrationAction(RegistrationEvent.EmailChanged(it)) },
            onNameChange = { viewModel.registrationAction(RegistrationEvent.NameChanged(it)) },
            onSurnameChange = { viewModel.registrationAction(RegistrationEvent.SurnameChanged(it)) },
            onPasswordChange = { viewModel.registrationAction(RegistrationEvent.PasswordChanged(it)) },
            onNewPasswordChange = { viewModel.registrationAction(RegistrationEvent.NewPasswordChanged(it)) },
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Switch(
                checked = userRegistrationState.isSeller,
                onCheckedChange = { viewModel.registrationAction(RegistrationEvent.SellerChange(it)) },
                thumbContent = {
                    if (userRegistrationState.isSeller){
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(stringResource(R.string.areYouSeller))
        }
        if(userRegistrationState.isSeller){
            ContactInfo(
                user = userRegistrationState,
                onAddressChange = { viewModel.registrationAction(RegistrationEvent.AddressChanged(it)) },
                onZipCodeChange = { viewModel.registrationAction(RegistrationEvent.ZipCodeChanged(it)) },
                onCountryChange = { viewModel.registrationAction(RegistrationEvent.CountryChanged(it)) },
                onPhoneNumberChange = { viewModel.registrationAction(RegistrationEvent.PhoneNumberChanged(it)) }
            )
            CreditCardFields(
                creditCard = userCreditCardState,
                onNumberChange = { viewModel.registrationAction(RegistrationEvent.CreditCardNumberChanged(it)) },
                onDateChange = { viewModel.registrationAction(RegistrationEvent.ExpirationDateChanged(it)) },
                onCvvChange = { viewModel.registrationAction(RegistrationEvent.CvvChanged(it)) },
                onIbanChange = { viewModel.registrationAction(RegistrationEvent.IbanChanged(it)) },
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {  },
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
            Text(
                "Do you have an Account? ",

                )
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    "Log In",
                )
            }
        }
    }
}
@Composable
fun PersonalInformation(
    user: UserTest,
    onEmailChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onSurnameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,

){
    InputTextField(
        value = user.email,
        onValueChanged = { onEmailChange(it) },
        label = stringResource(R.string.email),
        modifier = modifierStandard
    )
    InputTextField(
        value = user.name,
        onValueChanged = { onNameChange(it) },
        label = stringResource(R.string.name),
        modifier = modifierStandard
    )
    InputTextField(
        value = user.surname,
        onValueChanged = { onSurnameChange(it) },
        label = stringResource(R.string.surname),
        modifier = modifierStandard
    )
    PasswordsTextfields(
        user = user,
        onPasswordChange = onPasswordChange,
        onNewPasswordChange = onNewPasswordChange
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun RegisterCredentialsPreview(){
    RegisterCredentialsView(viewModel = DietiDealsViewModel())
}

