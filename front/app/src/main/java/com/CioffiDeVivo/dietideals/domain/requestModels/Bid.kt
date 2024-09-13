package com.CioffiDeVivo.dietideals.domain.requestModels

import com.google.gson.annotations.SerializedName
import java.time.Instant
import java.time.format.DateTimeFormatter

data class Bid(

    @SerializedName("id"        )   var id          : String? = null,
    @SerializedName("value"     )   var value       : Float?  = null,
    @SerializedName("userId"   )    var userId      : String? = null,
    @SerializedName("date"      )   var date        : String? = DateTimeFormatter.ISO_INSTANT.format(Instant.now()),
    @SerializedName("auctionId" )   var auctionId   : String? = null
)