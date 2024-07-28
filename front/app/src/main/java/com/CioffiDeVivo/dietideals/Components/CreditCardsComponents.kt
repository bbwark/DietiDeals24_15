package com.CioffiDeVivo.dietideals.Components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.domain.DataModels.CreditCard
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.Views.modifierStandard
import com.CioffiDeVivo.dietideals.viewmodel.state.RegistrationState

@Composable
fun CreditCardFields(
    userState: RegistrationState,
    onNumberChange: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onCvvChange: (String) -> Unit,
    onIbanChange: (String) -> Unit,
    onDeleteCardNumber: (String) -> Unit,
    onDeleteIban: (String) -> Unit,
){
    InputTextField(
        value = userState.creditCardNumber,
        onValueChanged = { onNumberChange(it) },
        label = stringResource(R.string.creditcard),
        isError = userState.creditCardNumberErrorMsg != null,
        supportingText = userState.creditCardNumberErrorMsg,
        onTrailingIconClick = { onDeleteCardNumber(it) },
        modifier = modifierStandard
    )
    Row {
        InputTextField(
            value = userState.expirationDate,
            onValueChanged = { onDateChange(it) },
            label = stringResource(R.string.expirationdate),
            isError = userState.expirationDateErrorMsg != null,
            placeholder = "MM/YY",
            supportingText = userState.expirationDateErrorMsg,
            onTrailingIconClick = {},
            modifier = Modifier.width(150.dp)
        )
        Spacer(modifier = Modifier.width(30.dp))
        InputTextField(
            value = userState.cvv,
            onValueChanged = { onCvvChange(it) },
            label = stringResource(R.string.cvv),
            isError = userState.cvvErrorMsg != null,
            supportingText = userState.cvvErrorMsg,
            onTrailingIconClick = {},
            modifier = Modifier.width(150.dp)
        )
    }
    InputTextField(
        value = userState.iban,
        onValueChanged = { onIbanChange(it) },
        label = stringResource(R.string.iban),
        isError = userState.ibanErrorMsg != null,
        supportingText = userState.ibanErrorMsg,
        onTrailingIconClick = { onDeleteIban(it) },
        modifier = modifierStandard
    )
}