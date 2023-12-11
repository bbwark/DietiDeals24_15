package com.CioffiDeVivo.dietideals.DataModels

class User(
    name: String,
    isSeller: Boolean = false,
    bio: String = "",
    webLinks: String = "",
    address: String = "",
    phoneNumber: String = "",
    creditCard: Array<CreditCard> = arrayOf()
){

}