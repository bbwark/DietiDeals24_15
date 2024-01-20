package com.CioffiDeVivo.dietideals.DataModels

import java.util.UUID

data class UserTest(
    val id: UUID = UUID.randomUUID(),
    var name: String = "",
    var surname: String = "",
    var email: String = "",
    var password: String = "",
    var isSeller: Boolean = false,
    var favouriteAuctions: Array<Auction> = arrayOf(),
    var bio: String? = "",
    var address: String? = "",
    var phoneNumber: String? = "",
    var creditCards: Array<CreditCard> = arrayOf()
)
