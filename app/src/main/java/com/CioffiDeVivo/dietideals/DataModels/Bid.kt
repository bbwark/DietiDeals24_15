package com.CioffiDeVivo.dietideals.DataModels

import java.time.ZonedDateTime
import java.util.UUID

class Bid(
    id: UUID,
    value: Int,
    userId: UUID,
    date: ZonedDateTime
) {
    val id: UUID = id
    val value: Int = value
    val userId: UUID = userId
    val date: ZonedDateTime = date
}