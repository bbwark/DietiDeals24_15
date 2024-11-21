package com.CioffiDeVivo.dietideals.network

import com.CioffiDeVivo.dietideals.domain.models.Country
import com.CioffiDeVivo.dietideals.domain.requestModels.Auction
import com.CioffiDeVivo.dietideals.domain.requestModels.CreditCard
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

data class UserReq(
    @SerialName(value = "id")
    val id: String,

    @SerialName(value = "email")
    val email: String,

    @SerialName(value = "name")
    val name: String,

    @SerialName(value = "isSeller")
    val isSeller: Boolean,

    @SerialName(value = "jwt")
    val jwt: String
)
