package com.CioffiDeVivo.dietideals.presentation.ui.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.AuctionsListElement
import com.CioffiDeVivo.dietideals.data.models.Auction


@Composable
fun SearchAuctionsList(
    auctions: ArrayList<Auction>,
    categoriesToHide: Set<String>,
    navController: NavController,
    onSearchMore: () -> Unit
) {
    val listState = rememberLazyListState()

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(auctions) { auction ->
            AuctionsListElement(auction = auction, navController = navController)
        }
    }
    LaunchedEffect(listState){
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                if(lastVisibleItemIndex == auctions.size - 1){
                    onSearchMore()
                }
            }
    }
}