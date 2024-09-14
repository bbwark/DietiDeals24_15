package com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials

open class RegistrationEvent {
    data class EmailChanged(val email: String): RegistrationEvent()
    data class EmailDeleted(val email: String): RegistrationEvent()
    data class NameChanged(val name: String): RegistrationEvent()
    data class NameDeleted(val email: String): RegistrationEvent()
    data class SurnameChanged(val surname: String): RegistrationEvent()
    data class SurnameDeleted(val email: String): RegistrationEvent()
    data class PasswordChanged(val password: String): RegistrationEvent()
    data class RetypePasswordChanged(val newPassword: String): RegistrationEvent()
    data class AddressChanged(val address: String): RegistrationEvent()
    data class AddressDeleted(val address: String): RegistrationEvent()
    data class CountryChanged(val country: String): RegistrationEvent()
    data class ZipCodeChanged(val zipCode: String): RegistrationEvent()
    data class ZipCodeDeleted(val address: String): RegistrationEvent()
    data class PhoneNumberChanged(val phoneNumber: String): RegistrationEvent()
    data class PhoneNumberDeleted(val address: String): RegistrationEvent()
    data class CreditCardNumberChanged(val creditCardNumber: String): RegistrationEvent()
    data class CreditCardNumberDeleted(val address: String): RegistrationEvent()
    data class ExpirationDateChanged(val expirationDate: String): RegistrationEvent()
    data class ExpirationDateDeleted(val address: String): RegistrationEvent()
    data class CvvChanged(val cvv: String): RegistrationEvent()
    data class CvvDeleted(val address: String): RegistrationEvent()
    data class IbanChanged(val iban: String): RegistrationEvent()
    data class IbanDeleted(val address: String): RegistrationEvent()
    data class SellerChange(val isSeller: Boolean): RegistrationEvent()
    data class Submit(val submitted: Boolean = true) : RegistrationEvent()
}