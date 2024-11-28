package com.CioffiDeVivo.dietideals.presentation.ui.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.AuctionsListElement
import com.CioffiDeVivo.dietideals.data.models.Auction


@Composable
fun SearchAuctionsList(
    auctions: ArrayList<Auction>,
    categoriesToHide: Set<String>,
    navController: NavController) {

    LazyColumn {
        itemsIndexed(auctions) { index, auction ->
            Column {
                if (index == 0) {
                    Spacer(modifier = Modifier.height(10.dp))
                    /*This Spacer was added to create a small offset to start the list a little below
                      the top of the Composable, this way the list will have a small transparent offset
                      that disappears when you scroll down the list*/
                }
                if (!categoriesToHide.contains(auction.category.name)) {
                    AuctionsListElement(auction = auction, navController = navController)
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }
}