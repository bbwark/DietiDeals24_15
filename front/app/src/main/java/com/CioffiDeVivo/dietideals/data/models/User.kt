package com.CioffiDeVivo.dietideals.data.models

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isSeller: Boolean = false,
    val favouriteAuctions: Array<Auction> = arrayOf(),
    val ownedAuctions: Array<Auction> = arrayOf(),
    val bio: String = "",
    val address: String = "",
    val zipcode: String = "",
    val country: Country? = Country.Italy,
    val phoneNumber: String = "",
    val creditCards: Array<CreditCard> = arrayOf(),
    val deviceTokens: Array<String> = arrayOf()
)

enum class Country{
    Italy,
    Spain,
    Germany,
    France,
    Belgium
}