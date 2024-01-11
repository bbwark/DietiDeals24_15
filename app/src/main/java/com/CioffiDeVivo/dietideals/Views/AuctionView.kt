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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CioffiDeVivo.dietideals.DataModels.Auction
import com.CioffiDeVivo.dietideals.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.DataModels.Item
import com.CioffiDeVivo.dietideals.R
import java.time.LocalDate
import java.util.UUID

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AuctionView(auction: Auction) {
    val pagerState = rememberPagerState { 3 } //To substitute it with the number of images in the array of images got from backend
    Column(Modifier.fillMaxSize()){
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
            Row(verticalAlignment = Alignment.CenterVertically){
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null, modifier = Modifier.size(14.dp))
                Text(text = insertionistName, fontSize = 12.sp, modifier = Modifier.clickable { /* open user info modal */ })
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = auctionType.name,
            fontSize = 12.sp
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AuctionViewPreview() {
    AuctionView(auction = Auction(UUID.randomUUID(), UUID.randomUUID(), Item(id = UUID.randomUUID(), name = ""), endingDate = LocalDate.now(), auctionType = AuctionType.English))
}