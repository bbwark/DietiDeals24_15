package com.CioffiDeVivo.dietideals.presentation.ui.sell

import android.app.Application
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.domain.models.Auction
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.FloatingAddButton
import com.CioffiDeVivo.dietideals.presentation.ui.sell.components.SellGridElement
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.presentation.ui.loading.LoadingView
import com.CioffiDeVivo.dietideals.presentation.ui.retry.RetryView
import com.CioffiDeVivo.dietideals.utils.EncryptedPreferencesManager

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
            navController = navController
        )
        is SellUiState.Error -> RetryView(onClick = {})
    }

}

@Composable
fun SellGridView(
    auctions: ArrayList<Auction>,
    navController: NavController
){
    val encryptedSharedPreferences = EncryptedPreferencesManager.getEncryptedPreferences()
    val isSeller = encryptedSharedPreferences.getBoolean("isSeller", false)

    if(!isSeller){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Not a Seller Account!",
                color = Color.Gray,
                fontSize = 20.sp,
                fontWeight = FontWeight(600),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 25.dp, end = 25.dp)
            )
        }
    } else{
        Box {
            if (auctions.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    content = {
                        //view model userCreatedAuction
                        itemsIndexed(auctions) { index, item ->
                            SellGridElement(
                                auction = item,
                                navController = navController
                            )
                        }
                    })
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
                navController.navigate(Screen.CreateAuction.route)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SellViewPreview() {
    SellView(viewModel = SellViewModel(Application()), rememberNavController())
}