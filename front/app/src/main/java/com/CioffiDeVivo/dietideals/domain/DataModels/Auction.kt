package com.CioffiDeVivo.dietideals.domain.DataModels

import java.time.LocalDate

data class Auction (
    val id: String = "",
    val ownerId: String = "",
    val item: Item = Item(name = ""),
    val description: String = "",
    val bids: Array<Bid> = arrayOf(),
    val endingDate: LocalDate? = LocalDate.now(),
    val minStep: String = "",
    val interval: String = "",
    val expired: Boolean = false,
    val minAccepted: String = "",
    val type: AuctionType = AuctionType.None,
    val category: AuctionCategory = AuctionCategory.Other
)