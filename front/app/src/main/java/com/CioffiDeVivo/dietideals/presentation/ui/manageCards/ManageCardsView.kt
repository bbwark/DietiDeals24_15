package com.CioffiDeVivo.dietideals.presentation.ui.manageCards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.FloatingAddButton
import com.CioffiDeVivo.dietideals.presentation.ui.manageCards.components.ManageCardsElement
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.presentation.ui.loading.LoadingView
import com.CioffiDeVivo.dietideals.presentation.ui.retry.RetryView

@Composable
fun ManageCardsView(viewModel: ManageCardsViewModel, navController: NavController) {

    val manageCardsUiState by viewModel.manageCardsUiState.collectAsState()

    LaunchedEffect(Unit){
        if(manageCardsUiState is ManageCardsUiState.Loading){
            viewModel.fetchCreditCards()
        }
    }

    when(manageCardsUiState){
        is ManageCardsUiState.Loading -> LoadingView()
        is ManageCardsUiState.Error -> RetryView(
            onClick = {
                viewModel.fetchCreditCards()
            }
        )
        is ManageCardsUiState.Success -> {
            val creditCards = (manageCardsUiState as ManageCardsUiState.Success).creditCards
            Box {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    content = {
                        itemsIndexed(creditCards) { index, item ->
                            if (index == 0) {
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                            ManageCardsElement(
                                cardNumber = item.creditCardNumber,
                                clickOnDelete = { viewModel.deleteCard(item.creditCardNumber) })
                            HorizontalDivider()
                        }
                    })
                FloatingAddButton(
                    onClick = {
                        if (navController.currentBackStackEntry?.destination?.route != Screen.AddCard.route) {
                            navController.navigate(Screen.AddCard.route)
                        }
                    }
                )
            }
        }
    }
}