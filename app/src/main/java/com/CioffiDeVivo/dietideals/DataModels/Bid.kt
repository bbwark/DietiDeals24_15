package com.CioffiDeVivo.dietideals.DataModels

import java.time.ZonedDateTime
import java.util.UUID

class Bid(
    id: UUID,
    value: Float,
    userId: Int,
    date: ZonedDateTime
) {
    val id: UUID = id
    val value: Float = value
    val userId: Int = userId
    val date: ZonedDateTime = date
}