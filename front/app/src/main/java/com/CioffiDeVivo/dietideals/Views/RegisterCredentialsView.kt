package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CioffiDeVivo.dietideals.Components.ContactInfo
import com.CioffiDeVivo.dietideals.Components.CreditCardFields
import com.CioffiDeVivo.dietideals.Components.InputTextField
import com.CioffiDeVivo.dietideals.viewmodel.MainViewModel
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.Components.ViewTitle
import com.CioffiDeVivo.dietideals.Components.pulsateClick
import com.CioffiDeVivo.dietideals.Events.RegistrationEvent
import com.CioffiDeVivo.dietideals.viewmodel.RegistrationViewModel
import java.time.LocalDate

val modifierStandard: Modifier = Modifier
    .fillMaxWidth()
    .padding(start = 30.dp, end = 30.dp)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterCredentialsView(viewModel: MainViewModel, viewModel2: RegistrationViewModel){

    val userRegistrationState by viewModel.userState.collectAsState()
    val userCreditCardState by viewModel.creditCardState.collectAsState()
    val userRegistrationState2 by viewModel2.registrationState.collectAsState()
    var showSellerComposable by rememberSaveable { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        ViewTitle(title = stringResource(id = R.string.createAccount))
        Spacer(modifier = Modifier.height(30.dp))
        PersonalInformation(viewModel2)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Switch(
                checked = userRegistrationState2.isSeller,
                onCheckedChange = {
                    viewModel.updateIsSeller()
                    showSellerComposable = !showSellerComposable
                                  },
                thumbContent = {
                    if (showSellerComposable){
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
        if(showSellerComposable){
            ContactInfo(
                user = userRegistrationState,
                onAddressChange = { viewModel.updateAddress(it) },
                onZipCodeChange = { viewModel.updateZipCode(it) },
                onCountryChange = { viewModel.updateCountry(it) },
                onPhoneNumberChange = { viewModel.updatePhoneNumber(it) },
                onDeleteAddress = { viewModel.deleteAddress() },
                onDeleteZipCode = { viewModel.deleteZipCode() },
                onDeletePhoneNumber = { viewModel.deletePhoneNumber() }
            )
            CreditCardFields(
                creditCard = userCreditCardState,
                onNumberChange = { viewModel.updateCreditCardNumber(it) },
                onDateChange = { viewModel.updateExpirationDate(LocalDate.parse(it)) },
                onCvvChange = { viewModel.updateCvv(it) },
                onIbanChange = { viewModel.updateIban(it) },
                onDeleteCardNumber = { viewModel.deleteCreditCardNumber() },
                onDeleteIban = { viewModel.deleteIban() }
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { viewModel2.registrationAction(RegistrationEvent.Submit) },
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
    viewModel: RegistrationViewModel

    ){
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var newPasswordVisible by rememberSaveable { mutableStateOf(false) }
    val userRegistrationState by viewModel.registrationState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = context){
        viewModel.validationEvent.collect { event ->
            when(event){
                is RegistrationViewModel.ValidationState.Success -> {
                    Toast.makeText(context, "Correct Registration", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    InputTextField(
        value = userRegistrationState.email,
        onValueChanged = { viewModel.registrationAction(RegistrationEvent.EmailChanged(it)) },
        label = stringResource(R.string.email),
        placeholder = stringResource(R.string.emailExample),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        isError = userRegistrationState.emailErrorMsg != null,
        onTrailingIconClick = { viewModel.deleteEmail() },
        supportingText = userRegistrationState.emailErrorMsg,
        modifier = modifierStandard
    )
    InputTextField(
        value = userRegistrationState.name,
        onValueChanged = { viewModel.registrationAction(RegistrationEvent.NameChanged(it)) },
        label = stringResource(R.string.name),
        isError = userRegistrationState.nameErrorMsg != null,
        onTrailingIconClick = { viewModel.deleteName() },
        supportingText = userRegistrationState.nameErrorMsg,
        modifier = modifierStandard
    )
    InputTextField(
        value = userRegistrationState.surname,
        onValueChanged = { viewModel.registrationAction(RegistrationEvent.SurnameChanged(it)) },
        label = stringResource(R.string.surname),
        isError = userRegistrationState.surnameErrorMsg != null,
        onTrailingIconClick = { viewModel.deleteSurname() },
        supportingText = userRegistrationState.surnameErrorMsg,
        modifier = modifierStandard
    )
    InputTextField(
        value = userRegistrationState.password,
        onValueChanged = { viewModel.registrationAction(RegistrationEvent.PasswordChanged(it)) },
        label = stringResource(R.string.password),
        isError = userRegistrationState.passwordErrorMsg != null,
        onTrailingIconClick = { passwordVisible = !passwordVisible },
        supportingText = userRegistrationState.passwordErrorMsg,
        visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = if(passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
        modifier = modifierStandard
    )
    InputTextField(
        value = userRegistrationState.newPassword,
        onValueChanged = { viewModel.registrationAction(RegistrationEvent.NewPasswordChanged(it)) },
        label = stringResource(R.string.rewritepassword),
        isError = userRegistrationState.newPasswordErrorMsg != null,
        onTrailingIconClick = { newPasswordVisible = !newPasswordVisible },
        supportingText = userRegistrationState.newPasswordErrorMsg,
        visualTransformation = if(newPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = if(newPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
        modifier = modifierStandard
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun RegisterCredentialsPreview(){
    RegisterCredentialsView(viewModel = MainViewModel(), viewModel2 = RegistrationViewModel())
}

