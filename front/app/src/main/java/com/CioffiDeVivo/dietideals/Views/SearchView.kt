package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.Components.AuctionsList
import com.CioffiDeVivo.dietideals.Components.SearchViewBar
import com.CioffiDeVivo.dietideals.DataModels.Auction
import com.CioffiDeVivo.dietideals.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.DataModels.Item
import com.CioffiDeVivo.dietideals.DietiDealsViewModel
import java.time.LocalDate
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchView(viewModel: DietiDealsViewModel, navController: NavHostController) {
    var categoriesToHide = remember { mutableStateOf(mutableSetOf<String>()) }
    Column(Modifier.fillMaxSize()) {
        SearchViewBar(categoriesToHide = categoriesToHide, viewModel = viewModel)
        AuctionsList(auctions = viewModel.auctionSearchResult, categoriesToHide = categoriesToHide, navController = navController, viewModel = viewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
    val viewModel = DietiDealsViewModel()
    viewModel.auctionSearchResult = arrayOf(
        Auction(
            UUID.randomUUID(), UUID.randomUUID(), item = Item(
                UUID.randomUUID(), name = "first"
            ), endingDate = LocalDate.now(), expired = false, auctionType = AuctionType.English
        ),
        Auction(
            UUID.randomUUID(), UUID.randomUUID(), item = Item(
                UUID.randomUUID(), name = "second"
            ), endingDate = LocalDate.now().plusMonths(1), expired = false, auctionType = AuctionType.English
        ),
        Auction(
            UUID.randomUUID(), UUID.randomUUID(), item = Item(
                UUID.randomUUID(), name = "third"
            ), endingDate = LocalDate.now().plusMonths(2), expired = false, auctionType = AuctionType.English
        ),
        Auction(
            UUID.randomUUID(), UUID.randomUUID(), item = Item(
                UUID.randomUUID(), name = "fourth"
            ), endingDate = LocalDate.now().plusMonths(3), expired = false, auctionType = AuctionType.English
        )
    )

    SearchView(viewModel = viewModel, navController = rememberNavController())
}