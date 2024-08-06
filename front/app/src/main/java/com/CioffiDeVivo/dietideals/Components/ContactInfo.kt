package com.CioffiDeVivo.dietideals.Components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.Views.modifierStandard
import com.CioffiDeVivo.dietideals.viewmodel.state.EditContactInfoState
import com.CioffiDeVivo.dietideals.viewmodel.state.RegistrationState

@Composable
fun ContactInfoOnRegisterCredentials(
    userState: RegistrationState,
    onAddressChange: (String) -> Unit,
    onZipCodeChange: (String) -> Unit,
    onCountryChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onDeleteAddress: (String) -> Unit,
    onDeleteZipCode: (String) -> Unit,
    onDeletePhoneNumber: (String) -> Unit,
){
    val countryList = listOf("Italy", "Spain", "Germany", "France", "Belgium")
    InputTextField(
        value = userState.address,
        onValueChanged = { onAddressChange(it) },
        label = stringResource(R.string.address),
        isError = userState.addressErrorMsg != null,
        supportingText = userState.addressErrorMsg,
        onTrailingIconClick = { onDeleteAddress(it) },
        modifier = modifierStandard
    )
    Row {
        InputTextField(
            value = userState.zipCode,
            onValueChanged = { onZipCodeChange(it) },
            label = stringResource(R.string.zipcode),
            isError = userState.zipCodeErrorMsg != null,
            supportingText = userState.zipCodeErrorMsg,
            onTrailingIconClick = { onDeleteZipCode(it) },
            modifier = Modifier.width(145.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Spacer(modifier = Modifier.height(10.dp))
        DropDownMenuField(
            menuList = countryList,
            onChange = { onCountryChange(it) }
        )
    }
    InputTextField(
        value = userState.phoneNumber,
        onValueChanged = { onPhoneNumberChange(it) },
        label = stringResource(R.string.phonenumber),
        isError = userState.phoneNumberErrorMsg != null,
        supportingText = userState.phoneNumberErrorMsg,
        onTrailingIconClick = { onDeletePhoneNumber(it) },
        modifier = modifierStandard
    )
}

@Composable
fun ContactInfoOnEditContactInfo(
    userState: EditContactInfoState,
    onAddressChange: (String) -> Unit,
    onZipCodeChange: (String) -> Unit,
    onCountryChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onDeleteAddress: (String) -> Unit,
    onDeleteZipCode: (String) -> Unit,
    onDeletePhoneNumber: (String) -> Unit,
){
    val countryList = listOf("Italy", "Spain", "Germany", "France", "Belgium")
    InputTextField(
        value = userState.address,
        onValueChanged = { onAddressChange(it) },
        label = stringResource(R.string.address),
        isError = userState.addressErrorMsg != null,
        supportingText = userState.addressErrorMsg,
        onTrailingIconClick = { onDeleteAddress(it) },
        modifier = modifierStandard
    )
    Row {
        InputTextField(
            value = userState.zipCode,
            onValueChanged = { onZipCodeChange(it) },
            label = stringResource(R.string.zipcode),
            isError = userState.zipCodeErrorMsg != null,
            supportingText = userState.zipCodeErrorMsg,
            onTrailingIconClick = { onDeleteZipCode(it) },
            modifier = Modifier.width(145.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Spacer(modifier = Modifier.height(10.dp))
        DropDownMenuField(
            menuList = countryList,
            onChange = { onCountryChange(it) }
        )
    }
    InputTextField(
        value = userState.phoneNumber,
        onValueChanged = { onPhoneNumberChange(it) },
        label = stringResource(R.string.phonenumber),
        isError = userState.phoneNumberErrorMsg != null,
        supportingText = userState.phoneNumberErrorMsg,
        onTrailingIconClick = { onDeletePhoneNumber(it) },
        modifier = modifierStandard
    )
}

