package com.CioffiDeVivo.dietideals.DataModels

import java.time.LocalDate

class CreditCard(
    number: String,
    cvv: String,
    expirationDate: LocalDate
) {
    val number: String = number
    val cvv: String = cvv
    val expirationDate: LocalDate = expirationDate
}