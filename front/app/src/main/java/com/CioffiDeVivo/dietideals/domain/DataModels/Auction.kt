package com.CioffiDeVivo.dietideals.domain.DataModels

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.UUID

data class Auction (
    val id: UUID = UUID.randomUUID(),
    val ownerId: UUID = UUID.randomUUID(),
    val item: Item = Item(UUID.randomUUID(),""),
    val description: String = "",
    val bids: Array<Bid> = arrayOf(),
    val endingDate: LocalDate? = LocalDate.now(),
    val minStep: String = "",
    val interval: String = "",
    val expired: Boolean = false,
    val minAccepted: String = "",
    val auctionType: AuctionType = AuctionType.None,
    val auctionCategory: AuctionCategory = AuctionCategory.Other
)