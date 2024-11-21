package com.CioffiDeVivo.dietideals.presentation.common.sharedComponents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.data.models.Country
import com.CioffiDeVivo.dietideals.presentation.ui.becomeSeller.BecomeSellerUiState
import com.CioffiDeVivo.dietideals.presentation.ui.editContactInfo.EditContactInfoUiState
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.modifierStandard
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.RegisterCredentialsUiState

@Composable
fun ContactInfo(
    userState: RegisterCredentialsUiState,
    onAddressChange: (String) -> Unit,
    onCountryChange: (Country) -> Unit,
    onZipCodeChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onDeleteAddress: (String) -> Unit,
    onDeleteZipCode: (String) -> Unit,
    onDeletePhoneNumber: (String) -> Unit,
){
    val pattern = remember { Regex("^\\d+\$") }
    var selectedCountry by remember { mutableStateOf(Country.Italy) }
    InputTextField(
        value = (userState as RegisterCredentialsUiState.RegisterParams).user.address,
        onValueChanged = { onAddressChange(it) },
        label = stringResource(R.string.address),
        isError = userState.addressErrorMsg != null,
        supportingText = userState.addressErrorMsg,
        onTrailingIconClick = { onDeleteAddress(it) },
        modifier = modifierStandard
    )
    Row(
        modifier = modifierStandard
    ){
        InputTextField(
            value = userState.user.zipCode,
            onValueChanged = {
                if(it.isEmpty() || it.matches(pattern)){
                    onZipCodeChange(it)
                }
            },
            label = stringResource(R.string.zipcode),
            isError = userState.zipCodeErrorMsg != null,
            supportingText = userState.zipCodeErrorMsg,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onTrailingIconClick = { onDeleteZipCode(it) },
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
        )
        DropDownMenuField(
            selectedValue = selectedCountry,
            menuList = Country.values(),
            label = stringResource(id = R.string.country),
            onValueSelected = {
                    newSelection -> selectedCountry = newSelection
                onCountryChange(selectedCountry)
            },
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
        )
    }
    InputTextField(
        value = userState.user.phoneNumber,
        onValueChanged = {
            if(it.isEmpty() || it.matches(pattern)){
                onPhoneNumberChange(it)
            }
        },
        label = stringResource(R.string.phonenumber),
        isError = userState.phoneNumberErrorMsg != null,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        supportingText = userState.phoneNumberErrorMsg,
        onTrailingIconClick = { onDeletePhoneNumber(it) },
        modifier = modifierStandard
    )
}

@Composable
fun ContactInfo(
    userState: EditContactInfoUiState,
    onAddressChange: (String) -> Unit,
    onCountryChange: (Country) -> Unit,
    onZipCodeChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onDeleteAddress: (String) -> Unit,
    onDeleteZipCode: (String) -> Unit,
    onDeletePhoneNumber: (String) -> Unit,
){
    val pattern = remember { Regex("^\\d+\$") }
    var selectedCountry by remember { mutableStateOf(Country.Italy) }
    InputTextField(
        value = (userState as EditContactInfoUiState.EditContactInfoParams).user.address,
        onValueChanged = { onAddressChange(it) },
        label = stringResource(R.string.address),
        isError = userState.addressErrorMsg != null,
        supportingText = userState.addressErrorMsg,
        onTrailingIconClick = { onDeleteAddress(it) },
        modifier = modifierStandard
    )
    Row(
        modifier = modifierStandard
    ){
        InputTextField(
            value = userState.user.zipCode,
            onValueChanged = {
                if(it.isEmpty() || it.matches(pattern)){
                    onZipCodeChange(it)
                }
            },
            label = stringResource(R.string.zipcode),
            isError = userState.zipCodeErrorMsg != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = userState.zipCodeErrorMsg,
            onTrailingIconClick = { onDeleteZipCode(it) },
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
        )
        DropDownMenuField(
            selectedValue = selectedCountry,
            menuList = Country.values(),
            label = stringResource(id = R.string.country),
            onValueSelected = {
                newSelection -> selectedCountry = newSelection
                onCountryChange(selectedCountry)
            },
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
        )
    }
    InputTextField(
        value = userState.user.phoneNumber,
        onValueChanged = {
            if(it.isEmpty() || it.matches(pattern)){
                onPhoneNumberChange(it)
            }
        },
        label = stringResource(R.string.phonenumber),
        isError = userState.phoneNumberErrorMsg != null,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        supportingText = userState.phoneNumberErrorMsg,
        onTrailingIconClick = { onDeletePhoneNumber(it) },
        modifier = modifierStandard
    )
}

@Composable
fun ContactInfo(
    userState: BecomeSellerUiState,
    onAddressChange: (String) -> Unit,
    onCountryChange: (Country) -> Unit,
    onZipCodeChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onDeleteAddress: (String) -> Unit,
    onDeleteZipCode: (String) -> Unit,
    onDeletePhoneNumber: (String) -> Unit,
){
    val pattern = remember { Regex("^\\d+\$") }
    var selectedCountry by remember { mutableStateOf(Country.Italy) }
    InputTextField(
        value = (userState as BecomeSellerUiState.BecomeSellerParams).user.address,
        onValueChanged = { onAddressChange(it) },
        label = stringResource(R.string.address),
        isError = userState.addressErrorMsg != null,
        supportingText = userState.addressErrorMsg,
        onTrailingIconClick = { onDeleteAddress(it) },
        modifier = modifierStandard
    )
    Row(
        modifier = modifierStandard
    ){
        InputTextField(
            value = userState.user.zipCode,
            onValueChanged = {
                if(it.isEmpty() || it.matches(pattern)){
                    onZipCodeChange(it)
                }
            },
            label = stringResource(R.string.zipcode),
            isError = userState.zipCodeErrorMsg != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = userState.zipCodeErrorMsg,
            onTrailingIconClick = { onDeleteZipCode(it) },
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
        )
        DropDownMenuField(
            selectedValue = selectedCountry,
            menuList = Country.values(),
            label = stringResource(id = R.string.country),
            onValueSelected = {
                    newSelection -> selectedCountry = newSelection
                onCountryChange(selectedCountry)
            },
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
        )
    }
    InputTextField(
        value = userState.user.phoneNumber,
        onValueChanged = {
            if(it.isEmpty() || it.matches(pattern)){
                onPhoneNumberChange(it)
            }
        },
        label = stringResource(R.string.phonenumber),
        isError = userState.phoneNumberErrorMsg != null,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        supportingText = userState.phoneNumberErrorMsg,
        onTrailingIconClick = { onDeletePhoneNumber(it) },
        modifier = modifierStandard
    )
}

