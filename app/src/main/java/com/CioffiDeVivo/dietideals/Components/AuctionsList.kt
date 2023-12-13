package com.CioffiDeVivo.dietideals.Components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.DataModels.Auction
import com.CioffiDeVivo.dietideals.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.DataModels.Item
import com.CioffiDeVivo.dietideals.ui.theme.DietiDealsTheme
import java.time.LocalDate
import java.util.UUID


@Composable
fun AuctionsList(auctions: Array<Auction>) {
    LazyColumn {
        items(auctions) {auction ->
            AuctionsListElement(
                auction = auction
            )
            Spacer(modifier = Modifier.height(5.dp))
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

    DietiDealsTheme {
        AuctionsList(auctions = testAuctions)
    }
}