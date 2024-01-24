package com.CioffiDeVivo.dietideals.Events

sealed class RegistrationEvent {
    data class EmailChanged(val email: String): RegistrationEvent()
    data class NameChanged(val name: String): RegistrationEvent()
    data class SurnameChanged(val surname: String): RegistrationEvent()
    data class PasswordChanged(val password: String): RegistrationEvent()
    data class NewPasswordChanged(val newPassword: String): RegistrationEvent()
    data class AddressChanged(val address: String): RegistrationEvent()
    data class CountryChanged(val country: String): RegistrationEvent()
    data class ZipCodeChanged(val zipCode: String): RegistrationEvent()
    data class PhoneNumberChanged(val phoneNumber: String): RegistrationEvent()
    data class CreditCardNumberChanged(val creditCardNumber: String): RegistrationEvent()
    data class ExpirationDateChanged(val expirationDate: String): RegistrationEvent()
    data class CvvChanged(val cvv: String): RegistrationEvent()
    data class IbanChanged(val iban: String): RegistrationEvent()
    data class SellerChange(val isSeller: Boolean): RegistrationEvent()
    object Submit: RegistrationEvent()
}