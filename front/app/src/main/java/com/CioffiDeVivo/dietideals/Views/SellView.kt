package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.CioffiDeVivo.dietideals.Components.FloatingAddButton
import com.CioffiDeVivo.dietideals.Components.SellGridElement
import com.CioffiDeVivo.dietideals.viewmodel.MainViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SellView(viewModel: MainViewModel, navController: NavHostController) {
    Box {
        if (viewModel.auctionCreatedByUser.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                content = {
                    itemsIndexed(viewModel.auctionCreatedByUser) { index, item ->
                        SellGridElement(
                            auctionItemName = item.item.name,
                            viewModel = viewModel,
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
                    textAlign = TextAlign.Center
                )
            }
        }
        FloatingAddButton(viewModel = viewModel) {
            //create a new Auction Navigation
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun SellViewPreview() {
    SellView(viewModel = MainViewModel(), rememberNavController())
}