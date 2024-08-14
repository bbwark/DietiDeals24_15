package com.CioffiDeVivo.dietideals.Components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.Views.modifierStandard
import com.CioffiDeVivo.dietideals.viewmodel.state.AddCardState
import com.CioffiDeVivo.dietideals.viewmodel.state.RegistrationState

@Composable
fun CreditCardFieldsOnRegisterCredentials(
    userState: RegistrationState,
    onNumberChange: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onCvvChange: (String) -> Unit,
    onIbanChange: (String) -> Unit,
    onDeleteCardNumber: (String) -> Unit,
    onDeleteExpirationDate: (String) -> Unit,
    onDeleteCvv: (String) -> Unit,
    onDeleteIban: (String) -> Unit,
){
    InputTextField(
        value = userState.card.creditCardNumber,
        onValueChanged = { onNumberChange(it) },
        label = stringResource(R.string.creditcard),
        isError = userState.creditCardNumberErrorMsg != null,
        supportingText = userState.creditCardNumberErrorMsg,
        onTrailingIconClick = { onDeleteCardNumber(it) },
        modifier = modifierStandard
    )
    Row {
        InputTextField(
            value = userState.card.expirationDate.toString(),
            onValueChanged = { onDateChange(it) },
            label = stringResource(R.string.expirationdate),
            isError = userState.expirationDateErrorMsg != null,
            placeholder = "MM/YY",
            supportingText = userState.expirationDateErrorMsg,
            onTrailingIconClick = { onDeleteExpirationDate(it) },
            modifier = Modifier.width(145.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        InputTextField(
            value = userState.card.cvv,
            onValueChanged = { onCvvChange(it) },
            label = stringResource(R.string.cvv),
            isError = userState.cvvErrorMsg != null,
            supportingText = userState.cvvErrorMsg,
            onTrailingIconClick = { onDeleteCvv(it) },
            modifier = Modifier.width(145.dp)
        )
    }
    InputTextField(
        value = userState.card.iban,
        onValueChanged = { onIbanChange(it) },
        label = stringResource(R.string.iban),
        isError = userState.ibanErrorMsg != null,
        supportingText = userState.ibanErrorMsg,
        onTrailingIconClick = { onDeleteIban(it) },
        modifier = modifierStandard
    )
}

@Composable
fun CreditCardFieldsOnAddCard(
    userState: AddCardState,
    onNumberChange: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onCvvChange: (String) -> Unit,
    onIbanChange: (String) -> Unit,
    onDeleteCardNumber: (String) -> Unit,
    onDeleteExpirationDate: (String) -> Unit,
    onDeleteCvv: (String) -> Unit,
    onDeleteIban: (String) -> Unit,
){
    InputTextField(
        value = userState.card.creditCardNumber,
        onValueChanged = { onNumberChange(it) },
        label = stringResource(R.string.creditcard),
        isError = userState.creditCardNumberErrorMsg != null,
        supportingText = userState.creditCardNumberErrorMsg,
        onTrailingIconClick = { onDeleteCardNumber(it) },
        modifier = modifierStandard
    )
    Row {
        InputTextField(
            value = userState.card.expirationDate.toString(),
            onValueChanged = { onDateChange(it) },
            label = stringResource(R.string.expirationdate),
            isError = userState.expirationDateErrorMsg != null,
            placeholder = "MM/YY",
            supportingText = userState.expirationDateErrorMsg,
            onTrailingIconClick = { onDeleteExpirationDate(it) },
            modifier = Modifier.width(145.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        InputTextField(
            value = userState.card.cvv,
            onValueChanged = { onCvvChange(it) },
            label = stringResource(R.string.cvv),
            isError = userState.cvvErrorMsg != null,
            supportingText = userState.cvvErrorMsg,
            onTrailingIconClick = { onDeleteCvv(it) },
            modifier = Modifier.width(145.dp)
        )
    }
    InputTextField(
        value = userState.card.iban,
        onValueChanged = { onIbanChange(it) },
        label = stringResource(R.string.iban),
        isError = userState.ibanErrorMsg != null,
        supportingText = userState.ibanErrorMsg,
        onTrailingIconClick = { onDeleteIban(it) },
        modifier = modifierStandard
    )
}