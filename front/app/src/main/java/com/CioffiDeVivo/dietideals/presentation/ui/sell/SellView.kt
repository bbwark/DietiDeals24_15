package com.CioffiDeVivo.dietideals.presentation.ui.sell

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.CioffiDeVivo.dietideals.data.models.Auction
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.FloatingAddButton
import com.CioffiDeVivo.dietideals.presentation.ui.sell.components.SellGridElement
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.presentation.ui.loading.LoadingView
import com.CioffiDeVivo.dietideals.presentation.ui.retry.RetryView

@Composable
fun SellView(viewModel: SellViewModel, navController: NavController) {

    val sellUiState by viewModel.sellUiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAuctions()
    }

    when(sellUiState){
        is SellUiState.Loading -> LoadingView()
        is SellUiState.Success -> SellGridView(
            auctions = (sellUiState as SellUiState.Success).auctions,
            navController = navController,
            isSeller = (sellUiState as SellUiState.Success).isSeller,
            onDelete = { viewModel.deleteAuction(it) }
        )
        is SellUiState.Error -> RetryView(onClick = {})
    }

}

@Composable
fun SellGridView(
    auctions: ArrayList<Auction>,
    navController: NavController,
    isSeller: Boolean,
    onDelete: (String) -> Unit
){
    var showDialog by remember { mutableStateOf(false) }
    if(!isSeller){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Not a Seller Account!",
                color = Color.Gray,
                fontSize = 20.sp,
                fontWeight = FontWeight(600),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 25.dp, end = 25.dp, bottom = 10.dp)
            )
            Button(
                onClick = {
                    if (navController.currentBackStackEntry?.destination?.route != Screen.BecomeSeller.route) {
                        navController.navigate(Screen.BecomeSeller.route){
                            launchSingleTop = true
                        }
                    }
                }
            ) {
                Text(text = "Become a Seller")
            }
        }
    } else{
        Box {
            if (auctions.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    content = {
                        itemsIndexed(auctions) { index, item ->
                            SellGridElement(
                                auction = item,
                                navController = navController,
                                onDelete = { onDelete(it) }
                            )
                        }
                    }
                )
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "You haven't created any auctions yet!",
                        color = Color.Gray,
                        fontSize = 20.sp,
                        fontWeight = FontWeight(600),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 25.dp, end = 25.dp)
                    )
                }
            }
            FloatingAddButton{
                if (navController.currentBackStackEntry?.destination?.route != Screen.CreateAuction.route) {
                    navController.navigate(Screen.CreateAuction.route){
                        launchSingleTop = true
                    }
                }
            }
        }
    }
}