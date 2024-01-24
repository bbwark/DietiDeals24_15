package com.CioffiDeVivo.dietideals.DataModels

import java.time.ZonedDateTime
import java.util.UUID

data class BidTest(
    val id: UUID = UUID.randomUUID(),
    val value: Float = 0F,
    val userId: UUID = UUID.randomUUID(),
    val date: String = ""
)
