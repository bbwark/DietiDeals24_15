package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CioffiDeVivo.dietideals.domain.DataModels.Auction
import com.CioffiDeVivo.dietideals.domain.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.domain.DataModels.Bid
import com.CioffiDeVivo.dietideals.domain.DataModels.Item
import com.CioffiDeVivo.dietideals.R
import java.time.LocalDate
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AuctionView(auction: Auction, isOwner: Boolean) {
    val pagerState =
        rememberPagerState { 3 } //To substitute it with the number of images in the array of images got from backend
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(state = pagerState) {
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp)
            )
        }

        AuctionHeader(
            itemName = auction.item.name,
            insertionistName = "Temporary Insertionist", //server request to get the name from the auction ownerId
            auctionType = auction.auctionType
        )

        Row(
            Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            when (auction.auctionType) {
                AuctionType.English -> EnglishAuctionBody(lastBid = auction.bids.lastOrNull())
                AuctionType.Silent -> auction.endingDate?.let { SilentAuctionBody(endingDate = it) }
                else ->{

                }
            }
            if (isOwner) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "View bid history",
                    Modifier.clickable { /* navigate to Bid History view */ },
                    Color.Blue,
                    fontSize = 12.sp
                )
            }
        }

        if (!isOwner) {
            Spacer(modifier = Modifier.size(12.dp))
            Button(onClick = { /* Navigates to Make A Bid View */ }) {
                Text(text = "Make a Bid", fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.size(12.dp))
        }

        auction.description.let { DescriptionAuctionItem(description = it) }
    }
}

@Composable
fun AuctionHeader(modifier: Modifier = Modifier, itemName: String, insertionistName: String, auctionType: AuctionType) {
    Row(
        Modifier.padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = itemName,
                fontSize = 24.sp,
                fontWeight = FontWeight(500)
            )
            Spacer(modifier = Modifier.size(3.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { /* open user info modal */ }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
                Text(text = insertionistName, fontSize = 12.sp)
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = auctionType.name,
            fontSize = 12.sp
        )
    }
}

@Composable
fun EnglishAuctionBody(lastBid: Bid?) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        if (lastBid != null) {
            Text(
                text = "${lastBid.value} EUR",
                fontSize = 32.sp,
                fontWeight = FontWeight(700)
            )
        } else {
            Text(
                text = "0 EUR",
                fontSize = 32.sp,
                fontWeight = FontWeight(700)
            )

        }
        Row(verticalAlignment = Alignment.Bottom) {
            Text(text = "Remaining Time: ", fontSize = 12.sp)
            Text(
                text = "50m",
                color = Color.Red,
                fontSize = 16.sp,
                fontWeight = FontWeight(500)
            ) //a function that calculates how much time has passed since the last bid, checks the auction interval, and put the remaining time
        }
    }
}

@Composable
fun SilentAuctionBody(endingDate: LocalDate) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Row(verticalAlignment = Alignment.Bottom){
            Text(text = "Ending Date: ", fontSize = 12.sp)
            Text(text = endingDate.toString(), fontWeight = FontWeight(500))
        }
        Row(verticalAlignment = Alignment.Bottom) {
            Text(text = "Remaining Time: ", fontSize = 12.sp)
            Text(
                text = "50m",
                color = Color.Red,
                fontSize = 16.sp,
                fontWeight = FontWeight(500)
            ) //a function that calculates how much time has passed since the last bid, checks the auction interval, and put the remaining time
        }
    }
}

@Composable
fun DescriptionAuctionItem(description: String) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(horizontal = 12.dp)) {
        Text(text = "About this item", fontSize = 16.sp, fontWeight = FontWeight(600))
        Spacer(modifier = Modifier.size(3.dp))
        Text(text = description, fontSize = 12.sp)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AuctionViewPreview() {
    AuctionView(
        auction = Auction(
            UUID.randomUUID(),
            UUID.randomUUID(),
            Item(id = UUID.randomUUID(), name = "Temporary Item"),
            endingDate = LocalDate.now().plusMonths(1),
            expired = false,
            auctionType = AuctionType.English
        ),
        isOwner = false
    )
}