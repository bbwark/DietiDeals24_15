package com.CioffiDeVivo.dietideals.domain.requestModels

import com.CioffiDeVivo.dietideals.domain.models.Country
import com.google.gson.annotations.SerializedName


data class User (

    @SerializedName("id"                       ) var id                       : String?                = null,
    @SerializedName("name"                     ) var name                     : String?                = null,
    @SerializedName("email"                    ) var email                    : String?                = null,
    @SerializedName("password"                 ) var password                 : String?                = null,
    @SerializedName("isSeller"                 ) var isSeller                 : Boolean?               = null,
    @SerializedName("ownedAuctions"            ) var ownedAuctions            : ArrayList<Auction>     = arrayListOf(),
    @SerializedName("favouriteAuctions"        ) var favouriteAuctions        : ArrayList<Auction>     = arrayListOf(),
    @SerializedName("bio"                      ) var bio                      : String?                = null,
    @SerializedName("address"                  ) var address                  : String?                = null,
    @SerializedName("zipcode"                  ) var zipcode                  : String?                = null,
    @SerializedName("country"                  ) var country                  : Country?                = Country.Italy,
    @SerializedName("phoneNumber"              ) var phoneNumber              : String?                = null,
    @SerializedName("creditCards"              ) var creditCards              : ArrayList<CreditCard>  = arrayListOf(),
    @SerializedName("authorities"              ) var authorities              : ArrayList<Authority> = arrayListOf()

)