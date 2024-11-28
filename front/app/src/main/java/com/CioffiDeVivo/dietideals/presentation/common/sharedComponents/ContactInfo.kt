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
import com.CioffiDeVivo.dietideals.presentation.ui.becomeSeller.BecomeSellerEvents
import com.CioffiDeVivo.dietideals.presentation.ui.becomeSeller.BecomeSellerUiState
import com.CioffiDeVivo.dietideals.presentation.ui.becomeSeller.BecomeSellerViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.editContactInfo.EditContactInfoEvents
import com.CioffiDeVivo.dietideals.presentation.ui.editContactInfo.EditContactInfoUiState
import com.CioffiDeVivo.dietideals.presentation.ui.editContactInfo.EditContactInfoViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.modifierStandard
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.RegisterCredentialsUiState
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.RegisterCredentialsViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.RegistrationEvents

@Composable
fun ContactInfo(
    userState: RegisterCredentialsUiState,
    viewModel: RegisterCredentialsViewModel
){
    val pattern = remember { Regex("^\\d+\$") }
    var selectedCountry by remember { mutableStateOf(Country.Italy) }
    InputTextField(
        value = (userState as RegisterCredentialsUiState.RegisterParams).user.address,
        onValueChanged = { viewModel.registrationAction(RegistrationEvents.AddressChanged(it)) },
        label = stringResource(R.string.address),
        isError = userState.addressErrorMsg != null,
        supportingText = userState.addressErrorMsg,
        onTrailingIconClick = { viewModel.registrationAction(RegistrationEvents.AddressDeleted) },
        modifier = modifierStandard
    )
    Row(
        modifier = modifierStandard
    ){
        InputTextField(
            value = userState.user.zipcode,
            onValueChanged = {
                if(it.isEmpty() || it.matches(pattern)){
                    viewModel.registrationAction(RegistrationEvents.ZipCodeChanged(it))
                }
            },
            label = stringResource(R.string.zipcode),
            isError = userState.zipCodeErrorMsg != null,
            supportingText = userState.zipCodeErrorMsg,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onTrailingIconClick = { viewModel.registrationAction(RegistrationEvents.ZipCodeDeleted) },
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
                viewModel.registrationAction(RegistrationEvents.CountryChanged(selectedCountry))
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
                viewModel.registrationAction(RegistrationEvents.PhoneNumberChanged(it))
            }
        },
        label = stringResource(R.string.phonenumber),
        isError = userState.phoneNumberErrorMsg != null,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        supportingText = userState.phoneNumberErrorMsg,
        onTrailingIconClick = { viewModel.registrationAction(RegistrationEvents.PhoneNumberDeleted) },
        modifier = modifierStandard
    )
}

@Composable
fun ContactInfo(
    userState: EditContactInfoUiState,
    viewModel: EditContactInfoViewModel
){
    val pattern = remember { Regex("^\\d+\$") }
    var selectedCountry by remember { mutableStateOf(Country.Italy) }
    InputTextField(
        value = (userState as EditContactInfoUiState.EditContactInfoParams).user.address,
        onValueChanged = { viewModel.editContactInfoAction(EditContactInfoEvents.AddressChanged(it)) },
        label = stringResource(R.string.address),
        isError = userState.addressErrorMsg != null,
        supportingText = userState.addressErrorMsg,
        onTrailingIconClick = { viewModel.editContactInfoAction(EditContactInfoEvents.AddressDeleted) },
        modifier = modifierStandard
    )
    Row(
        modifier = modifierStandard
    ){
        InputTextField(
            value = userState.user.zipcode,
            onValueChanged = {
                if(it.isEmpty() || it.matches(pattern)){
                    viewModel.editContactInfoAction(EditContactInfoEvents.ZipCodeChanged(it))
                }
            },
            label = stringResource(R.string.zipcode),
            isError = userState.zipCodeErrorMsg != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = userState.zipCodeErrorMsg,
            onTrailingIconClick = { viewModel.editContactInfoAction(EditContactInfoEvents.ZipCodeDeleted) },
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
                viewModel.editContactInfoAction(EditContactInfoEvents.CountryChanged(selectedCountry))
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
                viewModel.editContactInfoAction(EditContactInfoEvents.PhoneNumberChanged(it))
            }
        },
        label = stringResource(R.string.phonenumber),
        isError = userState.phoneNumberErrorMsg != null,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        supportingText = userState.phoneNumberErrorMsg,
        onTrailingIconClick = { viewModel.editContactInfoAction(EditContactInfoEvents.PhoneNumberDeleted) },
        modifier = modifierStandard
    )
}

@Composable
fun ContactInfo(
    userState: BecomeSellerUiState,
    viewModel: BecomeSellerViewModel
){
    val pattern = remember { Regex("^\\d+\$") }
    var selectedCountry by remember { mutableStateOf(Country.Italy) }
    InputTextField(
        value = (userState as BecomeSellerUiState.BecomeSellerParams).user.address,
        onValueChanged = { viewModel.becomeSellerOnAction(BecomeSellerEvents.AddressChanged(it)) },
        label = stringResource(R.string.address),
        isError = userState.addressErrorMsg != null,
        supportingText = userState.addressErrorMsg,
        onTrailingIconClick = { viewModel.becomeSellerOnAction(BecomeSellerEvents.AddressDeleted) },
        modifier = modifierStandard
    )
    Row(
        modifier = modifierStandard
    ){
        InputTextField(
            value = userState.user.zipcode,
            onValueChanged = {
                if(it.isEmpty() || it.matches(pattern)){
                    viewModel.becomeSellerOnAction(BecomeSellerEvents.ZipCodeChanged(it))
                }
            },
            label = stringResource(R.string.zipcode),
            isError = userState.zipCodeErrorMsg != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = userState.zipCodeErrorMsg,
            onTrailingIconClick = { viewModel.becomeSellerOnAction(BecomeSellerEvents.ZipCodeDeleted) },
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
                viewModel.becomeSellerOnAction(BecomeSellerEvents.CountryChanged(selectedCountry))
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
                viewModel.becomeSellerOnAction(BecomeSellerEvents.PhoneNumberChanged(it))
            }
        },
        label = stringResource(R.string.phonenumber),
        isError = userState.phoneNumberErrorMsg != null,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        supportingText = userState.phoneNumberErrorMsg,
        onTrailingIconClick = { viewModel.becomeSellerOnAction(BecomeSellerEvents.PhoneNumberDeleted) },
        modifier = modifierStandard
    )
}

