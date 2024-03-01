package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CioffiDeVivo.dietideals.Components.HomeViewAuctionsList
import com.CioffiDeVivo.dietideals.Components.ViewTitle
import com.CioffiDeVivo.dietideals.DataModels.Auction
import com.CioffiDeVivo.dietideals.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.DataModels.Item
import java.time.LocalDate
import java.util.UUID
import com.CioffiDeVivo.dietideals.Components.pulsateClick
import com.CioffiDeVivo.dietideals.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeView(){
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(18.dp))
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.pulsateClick()
            ) {
            Icon(Icons.Rounded.Favorite, contentDescription = null)
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Spacer(modifier = Modifier.height(40.dp))
        ViewTitle(title = stringResource(id = R.string.dietideals))
        Spacer(modifier = Modifier.height(15.dp))
        ElevatedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.size(width = 330.dp, height = 50.dp),
            content = {

                Icon(Icons.Rounded.Search, contentDescription = null)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    "Search on DietiDeals",
                    fontSize = 15.sp,
                    color = Color.Gray,
                )
                Spacer(modifier = Modifier.width(110.dp))
            }
        )
        Spacer(modifier = Modifier.height(50.dp))
        LatestAuctions()
        Spacer(modifier = Modifier.height(35.dp))
        EndingAuctions()
        Spacer(modifier = Modifier.height(35.dp))
        PartecipatedAuctions()
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LatestAuctions(){
    Text(
        "Latest Auctions",
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
    )
    Spacer(modifier = Modifier.height(10.dp))
    HomeViewAuctionsList(auctions = testAuctions)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EndingAuctions() {
    Text(
        "Ending Auctions",
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
    )
    Spacer(modifier = Modifier.height(10.dp))
    HomeViewAuctionsList(auctions = testAuctions)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PartecipatedAuctions(){
    Text(
        "Participated Auctions",
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
    )
    Spacer(modifier = Modifier.height(10.dp))
    HomeViewAuctionsList(auctions = testAuctions)
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HomeViewPreview(){
    HomeView()
}




//Test Auctions
val testItem = Item(id = UUID.randomUUID(), imagesUrl = arrayOf("url"), name = "Test")
@RequiresApi(Build.VERSION_CODES.O)
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
    ),
    Auction(
        id = UUID.randomUUID(),
        ownerId = UUID.randomUUID(),
        item = testItem,
        bids = arrayOf(),
        endingDate = LocalDate.of(2023, 12, 8),
        expired = false,
        auctionType = AuctionType.English
    ),
    Auction(
        id = UUID.randomUUID(),
        ownerId = UUID.randomUUID(),
        item = testItem,
        bids = arrayOf(),
        endingDate = LocalDate.of(2023, 12, 8),
        expired = false,
        auctionType = AuctionType.English
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