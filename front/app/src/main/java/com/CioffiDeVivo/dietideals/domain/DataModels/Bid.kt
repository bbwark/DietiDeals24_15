package com.CioffiDeVivo.dietideals.domain.DataModels

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZonedDateTime
import java.util.UUID

data class Bid(
    val id: UUID = UUID.randomUUID(),
    val value: Float = 0F,
    val userId: UUID = UUID.randomUUID(),
    val date: ZonedDateTime = ZonedDateTime.now()
)