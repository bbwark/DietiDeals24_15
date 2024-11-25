package com.CioffiDeVivo.dietideals.data.models

data class CreditCard(
    val creditCardNumber: String = "",
    val expirationDate: String = "",
    val cvv: String = "",
    val iban: String = "",
    val ownerId: String = ""
)