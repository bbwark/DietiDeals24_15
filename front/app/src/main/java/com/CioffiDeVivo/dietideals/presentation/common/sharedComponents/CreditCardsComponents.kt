package com.CioffiDeVivo.dietideals.presentation.common.sharedComponents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.presentation.ui.addCard.AddCardUiState
import com.CioffiDeVivo.dietideals.presentation.ui.becomeSeller.BecomeSellerUiState
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.modifierStandard
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.RegisterCredentialsUiState
import com.CioffiDeVivo.dietideals.utils.ExpirationDateTrasformation

@Composable
fun CreditCardComponents(
    userState: RegisterCredentialsUiState,
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
        value = (userState as RegisterCredentialsUiState.RegisterParams).creditCard.creditCardNumber,
        onValueChanged = {
            if(it.isEmpty() || it.matches(pattern)){
                onNumberChange(it)
            }
        },
        label = stringResource(R.string.creditcard),
        isError = userState.creditCardNumberErrorMsg != null,
        supportingText = userState.creditCardNumberErrorMsg,
        onTrailingIconClick = { onDeleteCardNumber(it) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifierStandard
    )
    Row(
        modifier = modifierStandard
    ){
        InputTextField(
            value = userState.creditCard.expirationDate,
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
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        InputTextField(
            value = userState.creditCard.cvv,
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
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
        )
    }
    InputTextField(
        value = userState.creditCard.iban,
        onValueChanged = { onIbanChange(it) },
        label = stringResource(R.string.iban),
        isError = userState.ibanErrorMsg != null,
        supportingText = userState.ibanErrorMsg,
        onTrailingIconClick = { onDeleteIban(it) },
        modifier = modifierStandard
    )
}

@Composable
fun CreditCardComponents(
    userState: AddCardUiState,
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
        value = (userState as AddCardUiState.AddCardParams).creditCard.creditCardNumber,
        onValueChanged = {
            if(it.isEmpty() || it.matches(pattern)){
                onNumberChange(it)
            }
        },
        label = stringResource(R.string.creditcard),
        isError = userState.creditCardNumberErrorMsg != null,
        supportingText = userState.creditCardNumberErrorMsg,
        onTrailingIconClick = { onDeleteCardNumber(it) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifierStandard
    )
    Row(
        modifier = modifierStandard
    ){
        InputTextField(
            value = userState.creditCard.expirationDate,
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
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
        )
        InputTextField(
            value = userState.creditCard.cvv,
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
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
        )
    }
    InputTextField(
        value = userState.creditCard.iban,
        onValueChanged = { onIbanChange(it) },
        label = stringResource(R.string.iban),
        isError = userState.ibanErrorMsg != null,
        supportingText = userState.ibanErrorMsg,
        onTrailingIconClick = { onDeleteIban(it) },
        modifier = modifierStandard
    )
}

@Composable
fun CreditCardComponents(
    userState: BecomeSellerUiState,
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
        value = (userState as BecomeSellerUiState.BecomeSellerParams).creditCard.creditCardNumber,
        onValueChanged = {
            if(it.isEmpty() || it.matches(pattern)){
                onNumberChange(it)
            }
        },
        label = stringResource(R.string.creditcard),
        isError = userState.creditCardNumberErrorMsg != null,
        supportingText = userState.creditCardNumberErrorMsg,
        onTrailingIconClick = { onDeleteCardNumber(it) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifierStandard
    )
    Row(
        modifier = modifierStandard
    ){
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
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        InputTextField(
            value = userState.creditCard.cvv,
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
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
        )
    }
    InputTextField(
        value = userState.creditCard.iban,
        onValueChanged = { onIbanChange(it) },
        label = stringResource(R.string.iban),
        isError = userState.ibanErrorMsg != null,
        supportingText = userState.ibanErrorMsg,
        onTrailingIconClick = { onDeleteIban(it) },
        modifier = modifierStandard
    )
}
