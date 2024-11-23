package com.CioffiDeVivo.dietideals.data.requestModels

import com.CioffiDeVivo.dietideals.data.models.Country
import com.google.gson.annotations.SerializedName


data class User (

    @SerializedName("id"                       ) var id                       : String?                = null,
    @SerializedName("name"                     ) var name                     : String?                = null,
    @SerializedName("email"                    ) var email                    : String?                = null,
    @SerializedName("password"                 ) var password                 : String?                = null,
    @SerializedName("isSeller"                 ) var isSeller                 : Boolean?               = null,
    @SerializedName("ownedAuctions"            ) var ownedAuctionRequests            : ArrayList<AuctionRequest>     = arrayListOf(),
    @SerializedName("favouriteAuctions"        ) var favouriteAuctionRequests        : ArrayList<AuctionRequest>     = arrayListOf(),
    @SerializedName("bio"                      ) var bio                      : String?                = null,
    @SerializedName("address"                  ) var address                  : String?                = null,
    @SerializedName("zipcode"                  ) var zipcode                  : String?                = null,
    @SerializedName("country"                  ) var country                  : Country?                = Country.Italy,
    @SerializedName("phoneNumber"              ) var phoneNumber              : String?                = null,
    @SerializedName("creditCards"              ) var creditCardRequests              : ArrayList<CreditCardRequest>  = arrayListOf(),
    @SerializedName("deviceTokens"             ) var deviceTokens             : ArrayList<String>      = arrayListOf()

)