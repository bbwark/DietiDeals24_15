package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CioffiDeVivo.dietideals.Components.ContactInfo
import com.CioffiDeVivo.dietideals.Components.CreditCardFields
import com.CioffiDeVivo.dietideals.DietiDealsViewModel
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.Components.PasswordsTextfields
import com.CioffiDeVivo.dietideals.Components.ViewTitle
import com.CioffiDeVivo.dietideals.Components.pulsateClick
import com.CioffiDeVivo.dietideals.DataModels.RegistrationEvent

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterCredentialsView(viewModel: DietiDealsViewModel){

    val userRegistrationState by viewModel.userState.collectAsState()
    val userCreditCardState by viewModel.creditCardState.collectAsState()
    val isEnabled by remember { mutableStateOf(true) }
    var isSellerButton by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        ViewTitle(title = stringResource(id = R.string.createAccount))
        Spacer(modifier = Modifier.height(30.dp))
        InputTextField(
            value = userRegistrationState.email,
            onValueChanged = { viewModel.registrationAction(RegistrationEvent.EmailChanged(it)) },
            label = stringResource(R.string.email)
        )
        InputTextField(
            value = userRegistrationState.name,
            onValueChanged = { viewModel.registrationAction(RegistrationEvent.NameChanged(it)) },
            label = stringResource(R.string.name)
        )
        InputTextField(
            value = userRegistrationState.surname,
            onValueChanged = { viewModel.registrationAction(RegistrationEvent.SurnameChanged(it)) },
            label = stringResource(R.string.surname)
        )
        PasswordsTextfields(
            password = userRegistrationState.password,
            passwordOnChange = { viewModel.registrationAction(RegistrationEvent.PasswordChanged(it)) },
            isToChangePassword = false
        )
        if(isSellerButton){
            ContactInfo(
                user = userRegistrationState,
                addressOnChange = { viewModel.registrationAction(RegistrationEvent.AddressChanged(it)) },
                zipCodeOnChange = { viewModel.registrationAction(RegistrationEvent.ZipCodeChanged(it)) },
                countryOnChange = { viewModel.registrationAction(RegistrationEvent.CountryChanged(it)) },
                phoneNumberOnChange = { viewModel.registrationAction(RegistrationEvent.PhoneNumberChanged(it)) }
            )
            CreditCardFields(
                creditCard = userRegistrationState.creditCards,
                numberOnChange = {},
                dateOnChange = {},
                cvvOnChange = {},
                ibanOnChange = {}
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
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            //If clicked isSeller on the user data class is true
            IconButton(onClick = {
                isSellerButton = !isSellerButton
            }) {
                val checkedBox = if(isSellerButton){
                    Icons.Filled.CheckBox
                }else{
                    Icons.Filled.CheckBoxOutlineBlank
                }
                Icon(imageVector  = checkedBox, null)
            }
            Text("Do you want to create a Seller Account?")
        }
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
fun InputTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
){
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        label = { Text(label) },
        singleLine = true,
        trailingIcon = {
                       Icon(
                           Icons.Rounded.Clear,
                           contentDescription = null,
                           modifier = Modifier.clickable {  }
                       )
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
    )
    Spacer(modifier = Modifier.height(10.dp))
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun RegisterCredentialsPreview(){
    RegisterCredentialsView(viewModel = DietiDealsViewModel())
}

