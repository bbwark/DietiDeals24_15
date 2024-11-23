package com.CioffiDeVivo.dietideals.data.requestModels

data class CreditCardRequest(
    val creditCardNumber: String,
    val expirationDate: String,
    val cvv: String,
    val iban: String,
    val ownerId: String
)