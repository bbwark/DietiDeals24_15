package com.CioffiDeVivo.dietideals.data.requestModels

import java.time.ZonedDateTime

data class BidRequest(
    val id: String = "",
    val value: Float = 0F,
    val userId: String = "",
    val date: ZonedDateTime = ZonedDateTime.now(),
    val auctionId: String = ""
)