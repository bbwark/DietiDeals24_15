package com.CioffiDeVivo.dietideals.domain.requestModels
import com.CioffiDeVivo.dietideals.domain.models.AuctionCategory
import com.CioffiDeVivo.dietideals.domain.models.AuctionType
import com.google.gson.annotations.SerializedName

data class Auction(
    @SerializedName("id"                ) var id:               String?             = null,
    @SerializedName("ownerId"           ) var ownerId:          String?             = null,
    @SerializedName("item"              ) var item:             Item?               = null,
    @SerializedName("description"       ) var description:      String?             = null,
    @SerializedName("bids"              ) var bids:             ArrayList<Bid>      = arrayListOf(),
    @SerializedName("endingDate"       ) var endingDate:       String?             = null,
    @SerializedName("minStep"          ) var minStep:          String?             = null,
    @SerializedName("interval"          ) var interval:         String?             = null,
    @SerializedName("expired"           ) var expired:          Boolean?            = false,
    @SerializedName("startingPrice"     ) var startingPrice:    String?             = null,
    @SerializedName("type"              ) var type:             AuctionType?        = null,
    @SerializedName("category"          ) var category:         AuctionCategory?    = AuctionCategory.Other,
    @SerializedName("buyoutPrice"       ) var buyoutPrice:      String?             = null,
)