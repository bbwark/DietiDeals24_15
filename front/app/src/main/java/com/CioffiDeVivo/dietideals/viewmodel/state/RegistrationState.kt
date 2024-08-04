package com.CioffiDeVivo.dietideals.viewmodel.state

data class RegistrationState (
    val email:  String  = "",
    val emailErrorMsg: String? = null,
    val name: String = "",
    val nameErrorMsg: String? = null,
    val surname: String = "",
    val surnameErrorMsg: String? = null,
    val password: String = "",
    val passwordErrorMsg: String? = null,
    val newPassword: String = "",
    val newPasswordErrorMsg: String? = null,
    val isSeller: Boolean = false,
    val address: String = "",
    val addressErrorMsg: String? = null,
    val zipCode: String = "",
    val zipCodeErrorMsg: String? = null,
    val country: String = "",
    val countryErrorMsg: String? = null,
    val phoneNumber: String = "",
    val phoneNumberErrorMsg: String? = null,
    val creditCardNumber: String = "",
    val creditCardNumberErrorMsg: String? = null,
    val expirationDate: String = "",
    val expirationDateErrorMsg: String? = null,
    val cvv: String = "",
    val cvvErrorMsg: String? = null,
    val iban: String = "",
    val ibanErrorMsg: String? = null,
)
