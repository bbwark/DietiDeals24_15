package com.CioffiDeVivo.dietideals.DataModels

import java.util.UUID

data class AuctionTest(
    val id: UUID = UUID.randomUUID(),
    val ownerId: UUID = UUID.randomUUID(),
    val item: ItemTest = ItemTest(UUID.randomUUID(),""),
    val description: String = "",
    val bids: Array<Bid> = arrayOf(),
    val endingDate: String = "",
    val minStep: String = "",
    val interval: String = "",
    val expired: Boolean = false,
    val minAccepted: String = "",
    val auctionType: AuctionType = AuctionType.None
)
