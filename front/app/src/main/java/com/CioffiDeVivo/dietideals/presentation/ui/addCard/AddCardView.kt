package com.CioffiDeVivo.dietideals.presentation.ui.addCard

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.CreditCardComponents
import com.CioffiDeVivo.dietideals.animations.pulsateClick
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.data.validations.ValidationState
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.presentation.ui.loading.LoadingView
import com.CioffiDeVivo.dietideals.presentation.ui.retry.RetryView


@Composable
fun AddCardView(viewModel: AddCardViewModel, navController: NavController){
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
        is AddCardUiState.Error -> RetryView(onClick = {})
        is AddCardUiState.Loading -> LoadingView()
        is AddCardUiState.Success -> {
            if (navController.currentBackStackEntry?.destination?.route != Screen.ManageCards.route) {
                navController.popBackStack()
            }
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
                    viewModel = viewModel
                )
                Button(
                    onClick = {
                        viewModel.addCardAction(AddCardEvents.Submit)
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
