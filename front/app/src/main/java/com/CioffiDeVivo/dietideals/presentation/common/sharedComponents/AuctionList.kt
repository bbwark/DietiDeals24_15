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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.domain.models.Auction
import com.CioffiDeVivo.dietideals.domain.models.AuctionType
import com.CioffiDeVivo.dietideals.domain.models.Item
import com.CioffiDeVivo.dietideals.presentation.theme.DietiDealsTheme
import java.time.LocalDate


@Composable
fun AuctionsList(
    auctions: Array<Auction>,
    categoriesToHide: MutableState<MutableSet<String>> = mutableStateOf(mutableSetOf()),
    navController: NavController,) {
    LazyColumn {
        itemsIndexed(auctions) { index, auction ->
            Column {
                if (index == 0) {
                    Spacer(modifier = Modifier.height(10.dp))
                    /*This Spacer was added to create a small offset to start the list a little below
                      the top of the Composable, this way the list will have a small transparent offset
                      that disappears when you scroll down the list*/
                }
                if (!categoriesToHide.value.contains(auction.category.name)) {
                    AuctionsListElement(auction = auction, navController = navController)
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }
}

@Composable
fun AuctionsListFavoured(
    auctions: Array<Auction>,
    categoriesToHide: MutableState<MutableSet<String>> = mutableStateOf(mutableSetOf()),
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
                if (!categoriesToHide.value.contains(auction.category.name)) {
                    AuctionsListElement(auction = auction, navController = navController)
                    Spacer(modifier = Modifier.height(5.dp))
                }
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

@Preview(showBackground = true)
@Composable
fun AuctionListPreview(){

    val testItem = Item(id = "", imagesUri = listOf(), name = "Desktop Computer")

    val testAuctions: Array<Auction> = arrayOf(
        Auction(
            id = "",
            ownerId = "",
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 16),
            expired = false,
            type = AuctionType.English
        ),
        Auction(
            id = "",
            ownerId = "",
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 17),
            expired = false,
            type = AuctionType.English
        ),
        Auction(
            id = "",
            ownerId = "",
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 10),
            expired = false,
            type = AuctionType.Silent
        ),
        Auction(
            id = "",
            ownerId = "",
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 9),
            expired = false,
            type = AuctionType.Silent
        ),
        Auction(
            id = "",
            ownerId = "",
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 8),
            expired = false,
            type = AuctionType.English
        )
    )

    DietiDealsTheme {
        AuctionsList(auctions = testAuctions, navController = rememberNavController())
    }
}