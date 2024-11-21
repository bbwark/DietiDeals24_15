package com.CioffiDeVivo.dietideals.data.models

import java.time.LocalDate

data class CreditCard constructor(
    val creditCardNumber: String = "",
    val expirationDate: LocalDate = LocalDate.now(),
    val cvv: String = "",
    val iban: String = "",
)