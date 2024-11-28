package com.CioffiDeVivo.dietideals.presentation.common.sharedComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.CioffiDeVivo.dietideals.data.models.Auction

@Composable
fun AuctionsListFavoured(
    auctions: Array<Auction>,
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
                AuctionsListElement(auction = auction, navController = navController)
                Spacer(modifier = Modifier.height(5.dp))

            }
        }
    }
}

@Composable
fun HomeViewAuctionsList(
    auctions: Array<Auction>,
    navController: NavController){
    LazyRow{
        itemsIndexed(auctions){index, item->
            Row {
                if(index == 0){
                    Spacer(modifier = Modifier.width(20.dp))
                }
                HomeViewAuctionListElement(
                    auction = item,
                    navController = navController
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}