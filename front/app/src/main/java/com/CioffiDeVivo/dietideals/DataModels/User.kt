package com.CioffiDeVivo.dietideals.DataModels

import java.util.UUID

data class User(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val password: String = "",
    val newPassword: String = "",
    val isSeller: Boolean = false,
    val favouriteAuctions: Array<Auction> = arrayOf(),
    val bio: String = "",
    val address: String = "",
    val zipCode: String = "",
    val country: String = "",
    val phoneNumber: String = "",
    val creditCards: Array<CreditCard> = arrayOf()
)