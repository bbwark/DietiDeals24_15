package com.CioffiDeVivo.dietideals.presentation.ui.sell

import android.app.Application
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.FloatingAddButton
import com.CioffiDeVivo.dietideals.presentation.ui.sell.components.SellGridElement
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen

@Composable
fun SellView(viewModel: SellViewModel, navController: NavHostController) {
    val userCreatedAuction by viewModel.userAuctionState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAuctions()
    }

    Box {
        if (userCreatedAuction.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                content = {
                    //view model userCreatedAuction
                    itemsIndexed(userCreatedAuction) { index, item ->
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

@Preview(showBackground = true)
@Composable
fun SellViewPreview() {
    SellView(viewModel = SellViewModel(Application()), rememberNavController())
}