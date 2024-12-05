package com.CioffiDeVivo.dietideals.data.models

data class Item(
    val id: String = "",
    val name: String = "",
    val imageUrl: List<String> = emptyList(),
    val auctionId: String = ""
)