package com.CioffiDeVivo.dietideals.DataModels

class User(
    name: String,
    email: String,
    password: String,
    isSeller: Boolean = false,
    bio: String = "",
    webLinks: String = "",
    address: String = "",
    phoneNumber: String = "",
    creditCards: Array<CreditCard> = arrayOf()
){
    var name = name
    var email = email
    var password = password
    var isSeller = isSeller
    var bio = bio
    var webLinks = webLinks
    var address = address
    var phoneNumber = phoneNumber
    var creditCards = creditCards
}