package com.CioffiDeVivo.dietideals.Components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.DataModels.UserTest
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.Views.modifierStandard

@Composable
fun ContactInfo(
    user: UserTest,
    onAddressChange: (String) -> Unit,
    onZipCodeChange: (String) -> Unit,
    onCountryChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
){
    InputTextField(
        value = user.address,
        onValueChanged = { onAddressChange(it) },
        label = stringResource(R.string.address),
        modifier = modifierStandard
    )
    Row {
        InputTextField(
            value = user.zipCode,
            onValueChanged = { onZipCodeChange(it) },
            label = stringResource(R.string.zipcode),
            modifier = Modifier.width(150.dp)
        )
        Spacer(modifier = Modifier.width(30.dp))
        InputTextField(
            value = user.country,
            onValueChanged = { onCountryChange(it) },
            label = stringResource(R.string.country),
            modifier = Modifier.width(150.dp)
        )
    }
    InputTextField(
        value = user.phoneNumber,
        onValueChanged = { onPhoneNumberChange(it) },
        label = stringResource(R.string.phonenumber),
        modifier = modifierStandard
    )
}
