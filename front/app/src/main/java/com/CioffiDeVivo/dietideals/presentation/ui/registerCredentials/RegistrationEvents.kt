package com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials

import com.CioffiDeVivo.dietideals.data.models.Country

sealed class RegistrationEvents {
    data class EmailChanged(val email: String): RegistrationEvents()
    object EmailDeleted: RegistrationEvents()
    data class NameChanged(val name: String): RegistrationEvents()
    object NameDeleted: RegistrationEvents()
    data class PasswordChanged(val password: String): RegistrationEvents()
    data class RetypePasswordChanged(val newPassword: String): RegistrationEvents()
    data class AddressChanged(val address: String): RegistrationEvents()
    object AddressDeleted: RegistrationEvents()
    data class CountryChanged(val country: Country): RegistrationEvents()
    data class ZipCodeChanged(val zipCode: String): RegistrationEvents()
    object ZipCodeDeleted: RegistrationEvents()
    data class PhoneNumberChanged(val phoneNumber: String): RegistrationEvents()
    object PhoneNumberDeleted: RegistrationEvents()
    data class CreditCardNumberChanged(val creditCardNumber: String): RegistrationEvents()
    object CreditCardNumberDeleted: RegistrationEvents()
    data class ExpirationDateChanged(val expirationDate: String): RegistrationEvents()
    object ExpirationDateDeleted: RegistrationEvents()
    data class CvvChanged(val cvv: String): RegistrationEvents()
    object CvvDeleted: RegistrationEvents()
    data class IbanChanged(val iban: String): RegistrationEvents()
    object IbanDeleted: RegistrationEvents()
    data class SellerChange(val isSeller: Boolean): RegistrationEvents()
    object Submit: RegistrationEvents()
}