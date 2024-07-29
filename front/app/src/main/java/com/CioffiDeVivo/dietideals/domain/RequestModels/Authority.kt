package com.CioffiDeVivo.dietideals.domain.RequestModels

import com.google.gson.annotations.SerializedName


data class Authority (

    @SerializedName("roleId"    ) var roleId    : String? = null,
    @SerializedName("authority" ) var authority : String? = null

)