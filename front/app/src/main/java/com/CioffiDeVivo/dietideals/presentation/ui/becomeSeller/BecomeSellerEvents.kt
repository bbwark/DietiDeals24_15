package com.CioffiDeVivo.dietideals.presentation.ui.becomeSeller

import com.CioffiDeVivo.dietideals.data.models.Country

sealed class BecomeSellerEvents {
    data class AddressChanged(val address: String): BecomeSellerEvents()
    object AddressDeleted: BecomeSellerEvents()
    data class CountryChanged(val country: Country): BecomeSellerEvents()
    data class ZipCodeChanged(val zipCode: String): BecomeSellerEvents()
    object ZipCodeDeleted: BecomeSellerEvents()
    data class PhoneNumberChanged(val phoneNumber: String): BecomeSellerEvents()
    object PhoneNumberDeleted: BecomeSellerEvents()
    data class CreditCardNumberChanged(val creditCardNumber: String): BecomeSellerEvents()
    object CreditCardNumberDeleted: BecomeSellerEvents()
    data class ExpirationDateChanged(val expirationDate: String): BecomeSellerEvents()
    object ExpirationDateDeleted: BecomeSellerEvents()
    data class CvvChanged(val cvv: String): BecomeSellerEvents()
    object CvvDeleted: BecomeSellerEvents()
    data class IbanChanged(val iban: String): BecomeSellerEvents()
    object IbanDeleted: BecomeSellerEvents()
    object Submit: BecomeSellerEvents()
}