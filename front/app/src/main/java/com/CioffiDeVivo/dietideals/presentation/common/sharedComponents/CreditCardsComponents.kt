package com.CioffiDeVivo.dietideals.presentation.common.sharedComponents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
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
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.modifierStandard
import com.CioffiDeVivo.dietideals.presentation.ui.addCard.AddCardState
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.RegistrationState
import com.CioffiDeVivo.dietideals.utils.ExpirationDateTrasformation

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
    val pattern = remember { Regex("^\\d+\$") }

    InputTextField(
        value = userState.card.creditCardNumber,
        onValueChanged = { onNumberChange(it) },
        label = stringResource(R.string.creditcard),
        isError = userState.creditCardNumberErrorMsg != null,
        supportingText = userState.creditCardNumberErrorMsg,
        onTrailingIconClick = { onDeleteCardNumber(it) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifierStandard
    )
    Row {
        InputTextField(
            value = userState.expirationDate,
            onValueChanged = {
                if(it.isEmpty() || it.matches(pattern)){
                    onDateChange(it)
                }
            },
            label = stringResource(R.string.expirationdate),
            isError = userState.expirationDateErrorMsg != null,
            placeholder = "MM/YY",
            supportingText = userState.expirationDateErrorMsg,
            onTrailingIconClick = { onDeleteExpirationDate(it) },
            visualTransformation =  ExpirationDateTrasformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.width(145.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        InputTextField(
            value = userState.card.cvv,
            onValueChanged = {
                if(it.isEmpty() || it.matches(pattern)){
                    onCvvChange(it)
                }
            },
            label = stringResource(R.string.cvv),
            isError = userState.cvvErrorMsg != null,
            supportingText = userState.cvvErrorMsg,
            onTrailingIconClick = { onDeleteCvv(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
    val pattern = remember { Regex("^\\d+\$") }

    InputTextField(
        value = userState.card.creditCardNumber,
        onValueChanged = { onNumberChange(it) },
        label = stringResource(R.string.creditcard),
        isError = userState.creditCardNumberErrorMsg != null,
        supportingText = userState.creditCardNumberErrorMsg,
        onTrailingIconClick = { onDeleteCardNumber(it) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifierStandard
    )
    Row {
        InputTextField(
            value = userState.expirationDate,
            onValueChanged = {
                if(it.isEmpty() || it.matches(pattern)){
                    onDateChange(it)
                }
            },
            label = stringResource(R.string.expirationdate),
            isError = userState.expirationDateErrorMsg != null,
            placeholder = "MM/YY",
            supportingText = userState.expirationDateErrorMsg,
            onTrailingIconClick = { onDeleteExpirationDate(it) },
            visualTransformation =  ExpirationDateTrasformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.width(145.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        InputTextField(
            value = userState.card.cvv,
            onValueChanged = {
                if(it.isEmpty() || it.matches(pattern)){
                    onCvvChange(it)
                }
            },
            label = stringResource(R.string.cvv),
            isError = userState.cvvErrorMsg != null,
            supportingText = userState.cvvErrorMsg,
            onTrailingIconClick = { onDeleteCvv(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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