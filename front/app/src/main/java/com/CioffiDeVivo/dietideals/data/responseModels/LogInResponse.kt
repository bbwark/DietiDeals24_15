package com.CioffiDeVivo.dietideals.data.responseModels

import com.CioffiDeVivo.dietideals.data.models.User

data class LogInResponse(
    val user: User,
    val jwt: String
)
