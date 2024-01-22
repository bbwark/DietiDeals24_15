package com.CioffiDeVivo.dietideals.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.DataModels.CreditCardTest
import com.CioffiDeVivo.dietideals.R

@Composable
fun CreditCardFields(
    creditCard: Array<CreditCardTest>,
    numberOnChange: (String) -> Unit,
    dateOnChange: (String) -> Unit,
    cvvOnChange: (String) -> Unit,
    ibanOnChange: (String) -> Unit
){
    Spacer(modifier = Modifier.height(10.dp))
    OutlinedTextField(
        value = creditCard[0].creditCardNumber,
        onValueChange = { numberOnChange(it) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        trailingIcon = {
            Icon(
                Icons.Rounded.Clear,
                contentDescription = null,
                modifier = Modifier.clickable{  }
            )
        },
        label = { Text(stringResource(R.string.creditcard)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp),
    )
    Spacer(modifier = Modifier.height(10.dp))
    Row {
        OutlinedTextField(
            value = creditCard[0].expirationDate,
            onValueChange = { dateOnChange(it) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            trailingIcon = {
                Icon(
                    Icons.Rounded.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable{  }
                )
            },
            modifier = Modifier.width(150.dp),
            label = { Text(stringResource(R.string.expirationdate)) },
        )
        Spacer(modifier = Modifier.width(30.dp))
        OutlinedTextField(
            value = creditCard[0].cvv,
            onValueChange = { cvvOnChange(it) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            trailingIcon = {
                Icon(
                    Icons.Rounded.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable{  }
                )
            },
            modifier = Modifier.width(150.dp),
            label = { Text(stringResource(R.string.cvv)) },
        )
        OutlinedTextField(
            value = creditCard[0].iban,
            onValueChange = { ibanOnChange(it) },
            singleLine = true,
            trailingIcon = {
                Icon(
                    Icons.Rounded.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable{  }
                )
            },
            modifier = Modifier.width(150.dp),
            label = { Text(stringResource(R.string.cvv)) },
        )
    }
}