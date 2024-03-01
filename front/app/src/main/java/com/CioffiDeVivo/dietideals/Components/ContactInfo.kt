package com.CioffiDeVivo.dietideals.Components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.DataModels.User
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.Views.modifierStandard

@Composable
fun ContactInfo(
    user: User,
    onAddressChange: (String) -> Unit,
    onZipCodeChange: (String) -> Unit,
    onCountryChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onDeleteAddress: (String) -> Unit,
    onDeleteZipCode: (String) -> Unit,
    onDeletePhoneNumber: (String) -> Unit
){
    InputTextField(
        value = user.address,
        onValueChanged = { onAddressChange(it) },
        label = stringResource(R.string.address),
        onDelete = { onDeleteAddress(it) },
        modifier = modifierStandard
    )
    Row {
        InputTextField(
            value = user.zipCode,
            onValueChanged = { onZipCodeChange(it) },
            label = stringResource(R.string.zipcode),
            onDelete = { onDeleteZipCode(it) },
            modifier = Modifier.width(150.dp)
        )
        Spacer(modifier = Modifier.width(30.dp))
        InputTextField(
            value = user.country,
            onValueChanged = { onCountryChange(it) },
            label = stringResource(R.string.country),
            onDelete = {},
            modifier = Modifier.width(150.dp)
        )
    }
    InputTextField(
        value = user.phoneNumber,
        onValueChanged = { onPhoneNumberChange(it) },
        label = stringResource(R.string.phonenumber),
        onDelete = { onDeletePhoneNumber(it) },
        modifier = modifierStandard
    )
}
