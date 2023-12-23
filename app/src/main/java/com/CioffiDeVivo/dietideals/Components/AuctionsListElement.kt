package com.CioffiDeVivo.dietideals.Components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.DataModels.Auction
import com.CioffiDeVivo.dietideals.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.DataModels.Item
import java.time.LocalDate
import java.util.UUID

@Composable
fun AuctionsListElement(modifier: Modifier = Modifier, auction: Auction) {
    Row(modifier = Modifier.padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically){
        iconPlaceholder()
        Spacer(modifier = Modifier.width(7.dp))
        Text(modifier = Modifier.fillMaxWidth(), text = auction.item.name)
    }
}

//Waiting for image system to implement images
@Composable
fun iconPlaceholder() {
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(Color.Red),
        contentAlignment = Alignment.Center
    ) {

    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AuctionsListElementPreview() {
    
    val testAuction = Auction(
        id = UUID.randomUUID(), 
        ownerId = UUID.randomUUID(),
        item = Item(id = UUID.randomUUID(), imagesUrl = arrayOf("url"), name = "Desktop Computer"),
        endingDate = LocalDate.of(2025, 10, 15), 
        auctionType = AuctionType.Silent)
    

}