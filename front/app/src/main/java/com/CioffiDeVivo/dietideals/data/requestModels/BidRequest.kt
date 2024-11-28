package com.CioffiDeVivo.dietideals.data.requestModels

data class BidRequest (
    val id: String = "",
    val value: Float = 0F,
    val userId: String = "",
    val date: String = "",
    val auctionId: String = ""
)