package com.CioffiDeVivo.dietideals.presentation.ui.addCard

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.CreditCardComponents
import com.CioffiDeVivo.dietideals.animations.pulsateClick
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.domain.validations.ValidationState
import com.CioffiDeVivo.dietideals.presentation.ui.loading.LoadingView
import com.CioffiDeVivo.dietideals.presentation.ui.retry.RetryView


@Composable
fun AddCardView(viewModel: AddCardViewModel, navController: NavHostController){
    val addCardUiState by viewModel.addCardUiState.collectAsState()
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

    when(addCardUiState){
        is AddCardUiState.Error -> RetryView()
        is AddCardUiState.Loading -> LoadingView()
        is AddCardUiState.Success -> {
            
        }
        is AddCardUiState.AddCardParams -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                CreditCardComponents(
                    userState = addCardUiState,
                    onNumberChange = { viewModel.addCardAction(AddCardEvents.CreditCardNumberChanged(it)) },
                    onDateChange = { viewModel.addCardAction(AddCardEvents.ExpirationDateChanged(it)) },
                    onCvvChange = { viewModel.addCardAction(AddCardEvents.CvvChanged(it)) },
                    onIbanChange = { viewModel.addCardAction(AddCardEvents.IBANChanged(it)) },
                    onDeleteCardNumber = { viewModel.addCardAction(AddCardEvents.CreditCardNumberDeleted(it)) },
                    onDeleteExpirationDate = { viewModel.addCardAction(AddCardEvents.ExpirationDateDeleted(it)) },
                    onDeleteCvv = { viewModel.addCardAction(AddCardEvents.CvvDeleted(it)) },
                    onDeleteIban = { viewModel.addCardAction(AddCardEvents.IBANDeleted(it)) }
                )
                Button(
                    onClick = {
                        viewModel.addCardAction(AddCardEvents.Submit())
                    },
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(bottom = 8.dp)
                        .pulsateClick(),
                ) {
                    Text(text = stringResource(id = R.string.saveCard))
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AddCardPreview(){
    AddCardView(viewModel = AddCardViewModel(Application()), navController = rememberNavController())
}
