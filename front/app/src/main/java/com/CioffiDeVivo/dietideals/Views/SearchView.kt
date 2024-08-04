package com.CioffiDeVivo.dietideals.Views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.Components.SearchAuctionsList
import com.CioffiDeVivo.dietideals.Components.SearchViewBar
import com.CioffiDeVivo.dietideals.domain.DataModels.Auction
import com.CioffiDeVivo.dietideals.domain.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.domain.DataModels.Item
import com.CioffiDeVivo.dietideals.viewmodel.MainViewModel
import com.CioffiDeVivo.dietideals.viewmodel.SearchViewModel
import java.time.LocalDate
import java.util.UUID

@Composable
fun SearchView(viewModel: SearchViewModel, navController: NavHostController) {
    val searchedAuctionState by viewModel.searchedAuctionState.collectAsState()

    Column(Modifier.fillMaxSize()) {
        SearchViewBar(viewModel = viewModel)
        SearchAuctionsList(
            auctions = viewModel.auctionSearchResult,
            navController = navController,
            viewModel = viewModel
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
    val viewModel = MainViewModel()
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

    SearchView(viewModel = SearchViewModel(), navController = rememberNavController())
}