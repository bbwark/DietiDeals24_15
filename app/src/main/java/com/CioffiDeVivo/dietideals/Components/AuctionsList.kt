package com.CioffiDeVivo.dietideals.Components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.DataModels.Auction
import com.CioffiDeVivo.dietideals.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.DataModels.Item
import com.CioffiDeVivo.dietideals.DietiDealsViewModel
import com.CioffiDeVivo.dietideals.Views.Navigation.Screen
import com.CioffiDeVivo.dietideals.ui.theme.DietiDealsTheme
import java.time.LocalDate
import java.util.UUID


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AuctionsList(modifier: Modifier = Modifier, auctions: Array<Auction>, navController: NavHostController, viewModel: DietiDealsViewModel) {
    LazyColumn {
        itemsIndexed(auctions) { index, item ->
            Column {
                if (index == 0) {
                    Spacer(modifier = Modifier.height(10.dp))
                    /*This Spacer was added to create a small offset to start the list a little below
                      the top of the Composable, this way the list will have a small transparent offset
                      that disappears when you scroll down the list*/
                }
                AuctionsListElement(modifier = Modifier.clickable {
                    viewModel.selectedAuction = item
                    //navController.navigate(Screen.Auction.route)
                }, auction = item)
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AuctionListPreview(){

    val testItem = Item(id = UUID.randomUUID(), imagesUrl = arrayOf("url"), name = "Desktop Computer")

    val testAuctions: Array<Auction> = arrayOf(
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 16),
            expired = false,
            auctionType = AuctionType.English
        ),
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 17),
            expired = false,
            auctionType = AuctionType.English
        ),
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 10),
            expired = false,
            auctionType = AuctionType.Silent
        ),
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 9),
            expired = false,
            auctionType = AuctionType.Silent
        ),
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 8),
            expired = false,
            auctionType = AuctionType.English
        )
    )

    DietiDealsTheme {
        AuctionsList(auctions = testAuctions, navController = rememberNavController(), viewModel = DietiDealsViewModel())
    }
}