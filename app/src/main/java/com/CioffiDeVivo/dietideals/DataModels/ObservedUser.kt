package com.CioffiDeVivo.dietideals.DataModels

import java.util.UUID

class ObservedUser(
    id: Int,
    name: String,
    isSeller: Boolean = false,
    bio: String? = null
    ) {
    val id: Int = id
    var name: String = name
    var isSeller: Boolean = isSeller
    var bio: String? = bio
}