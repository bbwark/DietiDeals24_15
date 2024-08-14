package com.CioffiDeVivo.dietideals.Views

import android.app.Application
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.Components.CreditCardFieldsOnAddCard
import com.CioffiDeVivo.dietideals.Components.CreditCardFieldsOnRegisterCredentials
import com.CioffiDeVivo.dietideals.Components.pulsateClick
import com.CioffiDeVivo.dietideals.Events.AddCardEvents
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.domain.use_case.ValidationState
import com.CioffiDeVivo.dietideals.viewmodel.AddCardViewModel


@Composable
fun AddCardView(viewModel: AddCardViewModel, navController: NavHostController){
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
        CreditCardFieldsOnAddCard(
            userState = userCard,
            onNumberChange = { viewModel.addCardAction(AddCardEvents.CreditCardNumberChanged(it)) },
            onDateChange = { viewModel.addCardAction(AddCardEvents.ExpirationDateChanged(it)) },
            onCvvChange = { viewModel.addCardAction(AddCardEvents.CvvChanged(it)) },
            onIbanChange = { viewModel.addCardAction(AddCardEvents.IBANChanged(it)) },
            onDeleteCardNumber = { viewModel.addCardAction(AddCardEvents.CreditCardNumberDeleted(it)) },
            onDeleteExpirationDate = { viewModel.addCardAction(AddCardEvents.ExpirationDateDeleted(it)) },
            onDeleteCvv = { viewModel.addCardAction(AddCardEvents.CvvDeleted(it)) },
            onDeleteIban = { viewModel.addCardAction(AddCardEvents.IBANDeleted(it)) }
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                      viewModel.addCardAction(AddCardEvents.Submit)
            },
            modifier = Modifier.pulsateClick()
        ) {
            Text(text = stringResource(id = R.string.saveCard))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddCardPreview(){
    AddCardView(viewModel = AddCardViewModel(Application()), navController = rememberNavController())
}
