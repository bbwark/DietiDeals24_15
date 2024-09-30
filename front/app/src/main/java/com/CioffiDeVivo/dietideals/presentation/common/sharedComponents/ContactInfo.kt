package com.CioffiDeVivo.dietideals.presentation.common.sharedComponents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.domain.models.Country
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.modifierStandard
import com.CioffiDeVivo.dietideals.presentation.ui.editContactInfo.EditContactInfoState
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.RegistrationState

@Composable
fun ContactInfoOnRegisterCredentials(
    userState: RegistrationState,
    onAddressChange: (String) -> Unit,
    onCountryChange: (Country) -> Unit,
    onZipCodeChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onDeleteAddress: (String) -> Unit,
    onDeleteZipCode: (String) -> Unit,
    onDeletePhoneNumber: (String) -> Unit,
){
    var selectedCountry by remember { mutableStateOf(Country.Italy) }
    InputTextField(
        value = userState.user.address,
        onValueChanged = { onAddressChange(it) },
        label = stringResource(R.string.address),
        isError = userState.addressErrorMsg != null,
        supportingText = userState.addressErrorMsg,
        onTrailingIconClick = { onDeleteAddress(it) },
        modifier = modifierStandard
    )
    Row {
        InputTextField(
            value = userState.user.zipCode,
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
            selectedValue = selectedCountry,
            menuList = Country.values(),
            label = stringResource(id = R.string.category),
            onValueSelected = {
                newSelection -> selectedCountry = newSelection
                onCountryChange(selectedCountry)
            }
        )
    }
    InputTextField(
        value = userState.user.phoneNumber,
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
    onCountryChange: (Country) -> Unit,
    onZipCodeChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onDeleteAddress: (String) -> Unit,
    onDeleteZipCode: (String) -> Unit,
    onDeletePhoneNumber: (String) -> Unit,
){
    var selectedCountry by remember { mutableStateOf(Country.Italy) }
    InputTextField(
        value = userState.user.address,
        onValueChanged = { onAddressChange(it) },
        label = stringResource(R.string.address),
        isError = userState.addressErrorMsg != null,
        supportingText = userState.addressErrorMsg,
        onTrailingIconClick = { onDeleteAddress(it) },
        modifier = modifierStandard
    )
    Row {
        InputTextField(
            value = userState.user.zipCode,
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
            selectedValue = selectedCountry,
            menuList = Country.values(),
            label = stringResource(id = R.string.category),
            onValueSelected = {
                newSelection -> selectedCountry = newSelection
                onCountryChange(selectedCountry)
            }
        )
    }
    InputTextField(
        value = userState.user.phoneNumber,
        onValueChanged = { onPhoneNumberChange(it) },
        label = stringResource(R.string.phonenumber),
        isError = userState.phoneNumberErrorMsg != null,
        supportingText = userState.phoneNumberErrorMsg,
        onTrailingIconClick = { onDeletePhoneNumber(it) },
        modifier = modifierStandard
    )
}

