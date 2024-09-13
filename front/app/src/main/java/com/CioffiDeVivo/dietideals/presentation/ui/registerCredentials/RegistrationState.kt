package com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials

import com.CioffiDeVivo.dietideals.domain.models.CreditCard
import com.CioffiDeVivo.dietideals.domain.models.User

data class RegistrationState (
    val emailErrorMsg: String? = null,
    val nameErrorMsg: String? = null,
    val surnameErrorMsg: String? = null,
    val passwordErrorMsg: String? = null,
    val retypePassword: String = "",
    val retypePasswordErrorMsg: String? = null,
    val addressErrorMsg: String? = null,
    val zipCodeErrorMsg: String? = null,
    val countryErrorMsg: String? = null,
    val phoneNumberErrorMsg: String? = null,
    val creditCardNumberErrorMsg: String? = null,
    val expirationDateErrorMsg: String? = null,
    val cvvErrorMsg: String? = null,
    val ibanErrorMsg: String? = null,
    val user: User = User(),
    val card: CreditCard = CreditCard()
)
