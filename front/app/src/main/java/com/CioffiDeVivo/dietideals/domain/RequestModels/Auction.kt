package com.CioffiDeVivo.dietideals.domain.RequestModels
import com.CioffiDeVivo.dietideals.domain.DataModels.AuctionCategory
import com.CioffiDeVivo.dietideals.domain.DataModels.AuctionType
import com.google.gson.annotations.SerializedName

data class Auction(
    @SerializedName("id"                ) var id:               String?             = null,
    @SerializedName("ownerId"           ) var ownerId:          String?             = null,
    @SerializedName("item"              ) var item:             Item?               = null,
    @SerializedName("description"       ) var description:      String?             = null,
    @SerializedName("bids"              ) var bids:             ArrayList<Bid>      = arrayListOf(),
    @SerializedName("ending_date"       ) var endingDate:       String?             = null,
    @SerializedName("min_step"          ) var minStep:          String?             = null,
    @SerializedName("interval"          ) var interval:         String?             = null,
    @SerializedName("expired"           ) var expired:          Boolean?            = false,
    @SerializedName("startingPrice"     ) var startingPrice:    String?             = null,
    @SerializedName("type"              ) var type:      AuctionType?        = null,
    @SerializedName("category"          ) var category:  AuctionCategory?    = AuctionCategory.Other
)