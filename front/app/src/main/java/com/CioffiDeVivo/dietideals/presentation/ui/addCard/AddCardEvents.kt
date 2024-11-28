package com.CioffiDeVivo.dietideals.presentation.ui.addCard

sealed class AddCardEvents {

    data class CreditCardNumberChanged(val creditCardNumber: String) : AddCardEvents()
    object CreditCardNumberDeleted: AddCardEvents()
    data class ExpirationDateChanged(val expirationDate: String) : AddCardEvents()
    object ExpirationDateDeleted: AddCardEvents()
    data class CvvChanged(val cvv: String) : AddCardEvents()
    object CvvDeleted: AddCardEvents()
    data class IBANChanged(val iban: String) : AddCardEvents()
    object IBANDeleted: AddCardEvents()
    object Submit: AddCardEvents()

}