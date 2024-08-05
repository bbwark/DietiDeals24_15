package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.Components.FloatingAddButton
import com.CioffiDeVivo.dietideals.Components.ManageCardsElement
import com.CioffiDeVivo.dietideals.Views.Navigation.Screen
import com.CioffiDeVivo.dietideals.viewmodel.MainViewModel
import com.CioffiDeVivo.dietideals.viewmodel.ManageCardsViewModel

@Composable
fun ManageCardsView(viewModel: ManageCardsViewModel, navController: NavController) {
    val creditCardsState by viewModel.userCardsState.collectAsState()
    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            content = {
                //viewModel userCardsState with rest API
                itemsIndexed(viewModel.user.creditCards) { index, item ->
                    if (index == 0) {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    ManageCardsElement(
                        cardNumber = item.creditCardNumber,
                        clickOnDelete = {/* delete the card from user's cards */ })
                    HorizontalDivider()
                }
            })
        FloatingAddButton(onClick = {
            navController.navigate(Screen.AddCard.route)
        })
    }
}

@Preview (showBackground = true)
@Composable
fun ManageCardsViewPreview() {
    ManageCardsView(viewModel = ManageCardsViewModel(), navController = rememberNavController())
}