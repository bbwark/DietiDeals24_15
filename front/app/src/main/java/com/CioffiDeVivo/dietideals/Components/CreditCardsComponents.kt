package com.CioffiDeVivo.dietideals.Components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.DataModels.CreditCardTest
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.Views.modifierStandard

@Composable
fun CreditCardFields(
    creditCard: CreditCardTest,
    onNumberChange: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onCvvChange: (String) -> Unit,
    onIbanChange: (String) -> Unit,
    onDeleteCardNumber: (String) -> Unit,
    onDeleteIban: (String) -> Unit,
){
    InputTextField(
        value = creditCard.creditCardNumber,
        onValueChanged = { onNumberChange(it) },
        label = stringResource(R.string.creditcard),
        onDelete = { onDeleteCardNumber(it) },
        modifier = modifierStandard
    )
    Row {
        InputTextField(
            value = creditCard.expirationDate,
            onValueChanged = { onDateChange(it) },
            label = stringResource(R.string.expirationdate),
            onDelete = {},
            modifier = Modifier.width(150.dp)
        )
        Spacer(modifier = Modifier.width(30.dp))
        InputTextField(
            value = creditCard.cvv,
            onValueChanged = { onCvvChange(it) },
            label = stringResource(R.string.cvv),
            onDelete = {},
            modifier = Modifier.width(150.dp)
        )
    }
    InputTextField(
        value = creditCard.iban,
        onValueChanged = { onIbanChange(it) },
        label = stringResource(R.string.iban),
        onDelete = { onDeleteIban(it) },
        modifier = modifierStandard
    )
}