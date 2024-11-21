package com.CioffiDeVivo.dietideals.data.requestModels
import com.google.gson.annotations.SerializedName

data class CreditCard(

    @SerializedName("creditCardNumber"  ) var creditCardNumber  : String?   = null,
    @SerializedName("expirationDate"    ) var expirationDate    : String?   = null,
    @SerializedName("cvv"               ) var cvv               : Int?      = null,
    @SerializedName("iban"              ) var iban              : String?   = null,
    @SerializedName("ownerId"           ) var ownerId           : String?     = null,
)