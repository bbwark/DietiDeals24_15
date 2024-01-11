package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.CioffiDeVivo.dietideals.DataModels.Auction
import com.CioffiDeVivo.dietideals.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.DataModels.Item
import java.time.LocalDate
import java.util.UUID

@Composable
fun AuctionView(auction: Auction) {

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun AuctionViewPreview() {
    AuctionView(auction = Auction(UUID.randomUUID(), UUID.randomUUID(), Item(id = UUID.randomUUID(), name = ""), endingDate = LocalDate.now(), auctionType = AuctionType.English))
}