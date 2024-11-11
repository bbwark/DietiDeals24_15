package com.CioffiDeVivo.dietideals.presentation.ui.manageCards

import android.app.Application
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.FloatingAddButton
import com.CioffiDeVivo.dietideals.presentation.ui.manageCards.components.ManageCardsElement
import com.CioffiDeVivo.dietideals.domain.models.CreditCard
import com.CioffiDeVivo.dietideals.domain.models.User
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.presentation.ui.loading.LoadingView
import com.CioffiDeVivo.dietideals.presentation.ui.retry.RetryView
import java.time.LocalDate

@Composable
fun ManageCardsView(viewModel: ManageCardsViewModel, navController: NavController) {

    val manageCardsUiState by viewModel.manageCardsUiState.collectAsState()

    LaunchedEffect(Unit){
        viewModel.fetchCreditCards()
    }

    when(manageCardsUiState){
        is ManageCardsUiState.Loading -> LoadingView()
        is ManageCardsUiState.Error -> RetryView(
            onClick = {
                navController.popBackStack()
                navController.navigate(Screen.ManageCards.route)
            }
        )
        is ManageCardsUiState.Success -> {
            Box {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    content = {
                        itemsIndexed((manageCardsUiState as ManageCardsUiState.Success).creditCards) { index, item ->
                            if (index == 0) {
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                            ManageCardsElement(
                                cardNumber = item.creditCardNumber,
                                clickOnDelete = { viewModel.deleteCard(item.creditCardNumber) })
                            HorizontalDivider()
                        }
                    })
                FloatingAddButton(onClick = {
                    navController.navigate(Screen.AddCard.route)
                })
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun ManageCardsViewPreview() {
    val viewModel = ManageCardsViewModel(Application())
    val user = User(
        "",
        "Nametest Surnametest",
        "",
        "emailtest",
        creditCards = arrayOf(
            CreditCard("556666666666", LocalDate.now().plusYears(1), "222"),
            CreditCard("456666666666", LocalDate.now().plusYears(2), "222"),
            CreditCard("356666666666", LocalDate.now().plusYears(2), "222")
        )
    )
    ManageCardsView(
        viewModel = viewModel,
        navController = rememberNavController()
    )
}