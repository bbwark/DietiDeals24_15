package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.Components.AuctionsList
import com.CioffiDeVivo.dietideals.DataModels.Auction
import com.CioffiDeVivo.dietideals.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.DataModels.Item
import com.CioffiDeVivo.dietideals.DataModels.User
import com.CioffiDeVivo.dietideals.ui.theme.DietiDealsTheme
import java.time.LocalDate
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FavouritesView(user: User) {
    var tabIndex: Int by remember { mutableStateOf(0) }

    Column(Modifier.fillMaxSize()){
        FavouriteTabRow(
            selectedTabIndex = tabIndex,
            tabs = listOf<String>("Active", "Finished"),
            onTabChange = {tabIndex = it} //or onTabChange = {selectedTab -> tabIndex = selectedTab}
        )
        Spacer(modifier = Modifier.height(5.dp))
        when (tabIndex) {
            0 -> AuctionsList(auctions = user.favouriteAuctions.filter { it.endingDate.isAfter(LocalDate.now()) }.toTypedArray()) //ActiveAuctions
            1 -> AuctionsList(auctions = user.favouriteAuctions.filter { it.endingDate.isBefore(LocalDate.now().plusDays(1)) }.toTypedArray()) //FinishedAuctions
        }
    }
}

@Composable
fun FavouriteTabRow(selectedTabIndex: Int, tabs: List<String>, onTabChange: (Int) -> Unit) {
    TabRow(selectedTabIndex = selectedTabIndex) {
        tabs.forEachIndexed { index, title ->
            Tab(selected = selectedTabIndex == index, onClick = { onTabChange(index)}) {
                Text(modifier = Modifier.padding(vertical = 10.dp), text = title)
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun FavouritesViewPreview(){

    val testItem = Item(id = UUID.randomUUID(), imagesUrl = arrayOf("url"), name = "Desktop Computer")

    val testAuctions: Array<Auction> = arrayOf(
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 16),
            auctionType = AuctionType.English
            ),
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 17),
            auctionType = AuctionType.English
        ),
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 10),
            auctionType = AuctionType.Silent
        ),
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 9),
            auctionType = AuctionType.Silent
        ),
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 8),
            auctionType = AuctionType.English
        )
    )


    val testUser = User(
        id = UUID.randomUUID(),
        name = "Pippo",
        email = "pippopluto@paperopoli.pap",
        password = "Pluto123",
        favouriteAuctions = testAuctions
    )

    DietiDealsTheme {
        FavouritesView(user = testUser)
    }
}