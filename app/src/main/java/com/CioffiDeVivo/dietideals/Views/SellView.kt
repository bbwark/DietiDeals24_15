package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.Components.FloatingAddButton
import com.CioffiDeVivo.dietideals.Components.SellGridElement
import com.CioffiDeVivo.dietideals.DataModels.Auction
import com.CioffiDeVivo.dietideals.DietiDealsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SellView(viewModel: DietiDealsViewModel) {
    Box {
        FloatingAddButton(viewModel = viewModel) {
            //create a new Auction
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            content = {
                itemsIndexed(DietiDealsViewModel().auctionCreatedByUser) { index, item ->
                    SellGridElement(
                        auctionItemName = item.item.name,
                        viewModel = DietiDealsViewModel(),
                        navController = rememberNavController()
                    )
                }
            })
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun SellViewPreview() {
    SellView(viewModel = DietiDealsViewModel())
}