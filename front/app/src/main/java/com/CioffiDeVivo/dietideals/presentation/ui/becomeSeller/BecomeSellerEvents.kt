package com.CioffiDeVivo.dietideals.presentation.ui.becomeSeller

import com.CioffiDeVivo.dietideals.data.models.Country

sealed class BecomeSellerEvents {
    data class AddressChanged(val address: String): BecomeSellerEvents()
    data class AddressDeleted(val address: String): BecomeSellerEvents()
    data class CountryChanged(val country: Country): BecomeSellerEvents()
    data class ZipCodeChanged(val zipCode: String): BecomeSellerEvents()
    data class ZipCodeDeleted(val address: String): BecomeSellerEvents()
    data class PhoneNumberChanged(val phoneNumber: String): BecomeSellerEvents()
    data class PhoneNumberDeleted(val address: String): BecomeSellerEvents()
    data class CreditCardNumberChanged(val creditCardNumber: String): BecomeSellerEvents()
    data class CreditCardNumberDeleted(val address: String): BecomeSellerEvents()
    data class ExpirationDateChanged(val expirationDate: String): BecomeSellerEvents()
    data class ExpirationDateDeleted(val address: String): BecomeSellerEvents()
    data class CvvChanged(val cvv: String): BecomeSellerEvents()
    data class CvvDeleted(val address: String): BecomeSellerEvents()
    data class IbanChanged(val iban: String): BecomeSellerEvents()
    data class IbanDeleted(val address: String): BecomeSellerEvents()
    data class Submit(val submitted: Boolean = true) : BecomeSellerEvents()
}