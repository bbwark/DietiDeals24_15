package com.CioffiDeVivo.dietideals.DataModels

import java.time.ZonedDateTime
import java.util.UUID

class Bid(
    value: Int,
    userId: UUID,
    date: ZonedDateTime
) {
    val value: Int = value
    val userId: UUID = userId
    val date: ZonedDateTime = date
}