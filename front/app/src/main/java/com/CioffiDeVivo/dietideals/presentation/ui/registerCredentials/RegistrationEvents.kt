package com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials

import com.CioffiDeVivo.dietideals.data.models.Country

open class RegistrationEvents {
    data class EmailChanged(val email: String): RegistrationEvents()
    data class EmailDeleted(val email: String): RegistrationEvents()
    data class NameChanged(val name: String): RegistrationEvents()
    data class NameDeleted(val email: String): RegistrationEvents()
    data class PasswordChanged(val password: String): RegistrationEvents()
    data class RetypePasswordChanged(val newPassword: String): RegistrationEvents()
    data class AddressChanged(val address: String): RegistrationEvents()
    data class AddressDeleted(val address: String): RegistrationEvents()
    data class CountryChanged(val country: Country): RegistrationEvents()
    data class ZipCodeChanged(val zipCode: String): RegistrationEvents()
    data class ZipCodeDeleted(val address: String): RegistrationEvents()
    data class PhoneNumberChanged(val phoneNumber: String): RegistrationEvents()
    data class PhoneNumberDeleted(val address: String): RegistrationEvents()
    data class CreditCardNumberChanged(val creditCardNumber: String): RegistrationEvents()
    data class CreditCardNumberDeleted(val address: String): RegistrationEvents()
    data class ExpirationDateChanged(val expirationDate: String): RegistrationEvents()
    data class ExpirationDateDeleted(val expirationDate: String): RegistrationEvents()
    data class CvvChanged(val cvv: String): RegistrationEvents()
    data class CvvDeleted(val address: String): RegistrationEvents()
    data class IbanChanged(val iban: String): RegistrationEvents()
    data class IbanDeleted(val address: String): RegistrationEvents()
    data class SellerChange(val isSeller: Boolean): RegistrationEvents()
    data class Submit(val submitted: Boolean = true) : RegistrationEvents()
}