package com.CioffiDeVivo.dietideals.domain.models

class ObservedUser(
    val id: String,
    var name: String,
    var isSeller: Boolean = false,
    var bio: String? = null
    ) {
}