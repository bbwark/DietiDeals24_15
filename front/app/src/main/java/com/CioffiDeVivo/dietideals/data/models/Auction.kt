package com.CioffiDeVivo.dietideals.data.models

import java.time.LocalDateTime

data class Auction (
    val id: String = "",
    val ownerId: String = "",
    val item: Item = Item(),
    val description: String = "",
    val bids: Array<Bid> = arrayOf(),
    val endingDate: LocalDateTime = LocalDateTime.now(),
    val minStep: String = "",
    val interval: String = "",
    val expired: Boolean = false,
    val startingPrice: String = "",
    val buyoutPrice: String = "",
    val type: AuctionType = AuctionType.None,
    val category: AuctionCategory = AuctionCategory.Other,
)