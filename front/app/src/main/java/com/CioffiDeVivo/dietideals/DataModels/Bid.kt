package com.CioffiDeVivo.dietideals.DataModels

import java.time.ZonedDateTime
import java.util.UUID

class Bid(
    id: UUID,
    value: Float,
    userId: UUID,
    date: ZonedDateTime
) {
    val id: UUID = id
    val value: Float = value
    val userId: UUID = userId
    val date: ZonedDateTime = date
}