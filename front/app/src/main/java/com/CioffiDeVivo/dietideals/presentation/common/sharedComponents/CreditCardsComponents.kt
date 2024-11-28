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
import com.CioffiDeVivo.dietideals.presentation.ui.addCard.AddCardEvents
import com.CioffiDeVivo.dietideals.presentation.ui.addCard.AddCardUiState
import com.CioffiDeVivo.dietideals.presentation.ui.addCard.AddCardViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.becomeSeller.BecomeSellerEvents
import com.CioffiDeVivo.dietideals.presentation.ui.becomeSeller.BecomeSellerUiState
import com.CioffiDeVivo.dietideals.presentation.ui.becomeSeller.BecomeSellerViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.modifierStandard
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.RegisterCredentialsUiState
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.RegisterCredentialsViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.RegistrationEvents
import com.CioffiDeVivo.dietideals.utils.ExpirationDateTransformation

@Composable
fun CreditCardComponents(
    userState: RegisterCredentialsUiState,
    viewModel: RegisterCredentialsViewModel
){
    val pattern = remember { Regex("^\\d+\$") }

    InputTextField(
        value = (userState as RegisterCredentialsUiState.RegisterParams).creditCard.creditCardNumber,
        onValueChanged = {
            if(it.isEmpty() || it.matches(pattern)){
                viewModel.registrationAction(RegistrationEvents.CreditCardNumberChanged(it))
            }
        },
        label = stringResource(R.string.creditcard),
        isError = userState.creditCardNumberErrorMsg != null,
        supportingText = userState.creditCardNumberErrorMsg,
        onTrailingIconClick = { viewModel.registrationAction(RegistrationEvents.CreditCardNumberDeleted) },
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
                    viewModel.registrationAction(RegistrationEvents.ExpirationDateChanged(it))
                }
            },
            label = stringResource(R.string.expirationdate),
            isError = userState.expirationDateErrorMsg != null,
            placeholder = "MM/YY",
            supportingText = userState.expirationDateErrorMsg,
            onTrailingIconClick = { viewModel.registrationAction(RegistrationEvents.ExpirationDateDeleted) },
            visualTransformation =  ExpirationDateTransformation(),
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
                    viewModel.registrationAction(RegistrationEvents.CvvChanged(it))
                }
            },
            label = stringResource(R.string.cvv),
            isError = userState.cvvErrorMsg != null,
            supportingText = userState.cvvErrorMsg,
            onTrailingIconClick = { viewModel.registrationAction(RegistrationEvents.CvvDeleted) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
        )
    }
    InputTextField(
        value = userState.creditCard.iban,
        onValueChanged = { viewModel.registrationAction(RegistrationEvents.IbanChanged(it)) },
        label = stringResource(R.string.iban),
        isError = userState.ibanErrorMsg != null,
        supportingText = userState.ibanErrorMsg,
        onTrailingIconClick = { viewModel.registrationAction(RegistrationEvents.IbanDeleted) },
        modifier = modifierStandard
    )
}

@Composable
fun CreditCardComponents(
    userState: AddCardUiState,
    viewModel: AddCardViewModel
){
    val pattern = remember { Regex("^\\d+\$") }

    InputTextField(
        value = (userState as AddCardUiState.AddCardParams).creditCard.creditCardNumber,
        onValueChanged = {
            if(it.isEmpty() || it.matches(pattern)){
                viewModel.addCardAction(AddCardEvents.CreditCardNumberChanged(it))
            }
        },
        label = stringResource(R.string.creditcard),
        isError = userState.creditCardNumberErrorMsg != null,
        supportingText = userState.creditCardNumberErrorMsg,
        onTrailingIconClick = { viewModel.addCardAction(AddCardEvents.CreditCardNumberDeleted) },
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
                    viewModel.addCardAction(AddCardEvents.ExpirationDateChanged(it))
                }
            },
            label = stringResource(R.string.expirationdate),
            isError = userState.expirationDateErrorMsg != null,
            placeholder = "MM/YY",
            supportingText = userState.expirationDateErrorMsg,
            onTrailingIconClick = { viewModel.addCardAction(AddCardEvents.ExpirationDateDeleted) },
            visualTransformation =  ExpirationDateTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
        )
        InputTextField(
            value = userState.creditCard.cvv,
            onValueChanged = {
                if(it.isEmpty() || it.matches(pattern)){
                    viewModel.addCardAction(AddCardEvents.CvvChanged(it))
                }
            },
            label = stringResource(R.string.cvv),
            isError = userState.cvvErrorMsg != null,
            supportingText = userState.cvvErrorMsg,
            onTrailingIconClick = { viewModel.addCardAction(AddCardEvents.CvvDeleted) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
        )
    }
    InputTextField(
        value = userState.creditCard.iban,
        onValueChanged = { viewModel.addCardAction(AddCardEvents.IBANChanged(it)) },
        label = stringResource(R.string.iban),
        isError = userState.ibanErrorMsg != null,
        supportingText = userState.ibanErrorMsg,
        onTrailingIconClick = { viewModel.addCardAction(AddCardEvents.IBANDeleted) },
        modifier = modifierStandard
    )
}

@Composable
fun CreditCardComponents(
    userState: BecomeSellerUiState,
    viewModel: BecomeSellerViewModel
){
    val pattern = remember { Regex("^\\d+\$") }

    InputTextField(
        value = (userState as BecomeSellerUiState.BecomeSellerParams).creditCard.creditCardNumber,
        onValueChanged = {
            if(it.isEmpty() || it.matches(pattern)){
                viewModel.becomeSellerOnAction(BecomeSellerEvents.CreditCardNumberChanged(it))
            }
        },
        label = stringResource(R.string.creditcard),
        isError = userState.creditCardNumberErrorMsg != null,
        supportingText = userState.creditCardNumberErrorMsg,
        onTrailingIconClick = { viewModel.becomeSellerOnAction(BecomeSellerEvents.CreditCardNumberDeleted) },
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
                    viewModel.becomeSellerOnAction(BecomeSellerEvents.ExpirationDateChanged(it))
                }
            },
            label = stringResource(R.string.expirationdate),
            isError = userState.expirationDateErrorMsg != null,
            placeholder = "MM/YY",
            supportingText = userState.expirationDateErrorMsg,
            onTrailingIconClick = { viewModel.becomeSellerOnAction(BecomeSellerEvents.ExpirationDateDeleted) },
            visualTransformation =  ExpirationDateTransformation(),
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
                    viewModel.becomeSellerOnAction(BecomeSellerEvents.CvvChanged(it))
                }
            },
            label = stringResource(R.string.cvv),
            isError = userState.cvvErrorMsg != null,
            supportingText = userState.cvvErrorMsg,
            onTrailingIconClick = { viewModel.becomeSellerOnAction(BecomeSellerEvents.CvvDeleted) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
        )
    }
    InputTextField(
        value = userState.creditCard.iban,
        onValueChanged = { viewModel.becomeSellerOnAction(BecomeSellerEvents.IbanChanged(it)) },
        label = stringResource(R.string.iban),
        isError = userState.ibanErrorMsg != null,
        supportingText = userState.ibanErrorMsg,
        onTrailingIconClick = { viewModel.becomeSellerOnAction(BecomeSellerEvents.IbanDeleted) },
        modifier = modifierStandard
    )
}
