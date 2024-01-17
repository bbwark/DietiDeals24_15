package com.CioffiDeVivo.dietideals.DataModels

import java.time.LocalDate

class CreditCard(
    number: String,
    cvv: String,
    expirationDate: LocalDate
) {
    var number: String = number
    var cvv: String = cvv
    var expirationDate: LocalDate = expirationDate
}