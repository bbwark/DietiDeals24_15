package com.CioffiDeVivo.dietideals.domain.RequestModels
import com.google.gson.annotations.SerializedName

data class Item(

    @SerializedName("id"        ) var id        : String? = null,
    @SerializedName("name"      ) var name      : String? = null,
    @SerializedName("imageUrl"  ) var imageUrl  : String? = null,
    @SerializedName("auctionId" ) var auctionId : String? = null
)