package com.CioffiDeVivo.dietideals.Views

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.Components.CreditCardFields
import com.CioffiDeVivo.dietideals.Components.pulsateClick
import com.CioffiDeVivo.dietideals.Events.AddCardEvents
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.domain.use_case.ValidationState
import com.CioffiDeVivo.dietideals.viewmodel.AddCardViewModel


@Composable
fun AddCardView(viewModel: AddCardViewModel){
    val userCard by viewModel.userCardState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = context){
        viewModel.validationAddCardEvent.collect { event ->
            when(event){
                is ValidationState.Success -> {
                    Toast.makeText(context, "Correct Registration", Toast.LENGTH_SHORT).show()
                }

                else -> { Toast.makeText(context, "Invalid Field", Toast.LENGTH_SHORT).show() }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        CreditCardFields(
            userState = userCard,
            onNumberChange = { viewModel.addCardAction(AddCardEvents.CreditCardNumberChanged(it)) },
            onDateChange = { viewModel.addCardAction(AddCardEvents.ExpirationDateChanged(it)) },
            onCvvChange = { viewModel.addCardAction(AddCardEvents.CvvChanged(it)) },
            onIbanChange = { viewModel.addCardAction(AddCardEvents.IBANChanged(it)) },
            onDeleteCardNumber = { viewModel.deleteCreditCardNumber() },
            onDeleteIban = { viewModel.deleteIban() }
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.pulsateClick()
        ) {
            Text(text = stringResource(id = R.string.saveCard))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddCardPreview(){
    AddCardView(viewModel = AddCardViewModel())
}
