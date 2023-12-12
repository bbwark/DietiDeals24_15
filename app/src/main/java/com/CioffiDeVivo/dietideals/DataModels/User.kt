package com.CioffiDeVivo.dietideals.DataModels

import java.util.UUID

class User(
    id: UUID,
    name: String,
    email: String,
    password: String,
    isSeller: Boolean = false,
    favouriteAuctions: Array<Auction> = arrayOf(),
    bio: String? = null,
    webLinks: String? = null,
    address: String? = null,
    phoneNumber: String? = null,
    creditCards: Array<CreditCard> = arrayOf()
){
    val id: UUID = id
    var name: String = name
    var email: String = email
    var password: String = password
    var isSeller: Boolean = isSeller
    var favouriteAuctions: Array<Auction> = favouriteAuctions
    var bio: String? = bio
    var webLinks: String? = webLinks
    var address: String? = address
    var phoneNumber: String? = phoneNumber
    var creditCards: Array<CreditCard> = creditCards
}