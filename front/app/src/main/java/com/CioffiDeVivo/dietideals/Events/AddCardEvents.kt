package com.CioffiDeVivo.dietideals.Events

sealed class AddCardEvents {

    data class CreditCardNumberChanged(val creditCardNumber: String) : AddCardEvents()
    data class CreditCardNumberDeleted(val creditCardNumber: String) : AddCardEvents()
    data class ExpirationDateChanged(val expirationDate: String) : AddCardEvents()
    data class ExpirationDateDeleted(val expirationDate: String) : AddCardEvents()
    data class CvvChanged(val cvv: String) : AddCardEvents()
    data class CvvDeleted(val cvv: String) : AddCardEvents()
    data class IBANChanged(val iban: String) : AddCardEvents()
    data class IBANDeleted(val iban: String) : AddCardEvents()
    object Submit: AddCardEvents()

}