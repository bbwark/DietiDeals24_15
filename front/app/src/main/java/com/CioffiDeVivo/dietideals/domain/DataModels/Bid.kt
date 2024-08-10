package com.CioffiDeVivo.dietideals.domain.DataModels

import java.time.ZonedDateTime

data class Bid(
    val id: String = "",
    val value: Float = 0F,
    val userId: String = "",
    val date: ZonedDateTime = ZonedDateTime.now()
)