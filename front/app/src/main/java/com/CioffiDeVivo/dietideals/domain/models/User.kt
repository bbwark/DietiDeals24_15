package com.CioffiDeVivo.dietideals.domain.models

data class User(
    val id: String = "",
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val password: String = "",
    val isSeller: Boolean = false,
    val favouriteAuctions: Array<Auction> = arrayOf(),
    val ownedAuctions: Array<Auction> = arrayOf(),
    val bio: String = "",
    val address: String = "",
    val zipCode: String = "",
    val country: Country? = Country.Italy,
    val phoneNumber: String = "",
    val creditCards: Array<CreditCard> = arrayOf()
)

enum class Country{
    Italy,
    Spain,
    Germany,
    France,
    Belgium
}