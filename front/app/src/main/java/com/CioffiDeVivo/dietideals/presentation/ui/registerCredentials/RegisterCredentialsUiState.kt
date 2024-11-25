package com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials

import com.CioffiDeVivo.dietideals.data.models.CreditCard
import com.CioffiDeVivo.dietideals.data.models.User

sealed interface RegisterCredentialsUiState {
    object Success: RegisterCredentialsUiState
    object Error: RegisterCredentialsUiState
    object Loading: RegisterCredentialsUiState

    data class RegisterParams (
        val user: User = User(),
        val creditCard: CreditCard = CreditCard(),
        val emailErrorMsg: String? = null,
        val nameErrorMsg: String? = null,
        val surnameErrorMsg: String? = null,
        val passwordErrorMsg: String? = null,
        val retypePassword: String = "",
        val retypePasswordErrorMsg: String? = null,
        val addressErrorMsg: String? = null,
        val zipCodeErrorMsg: String? = null,
        val phoneNumberErrorMsg: String? = null,
        val creditCardNumberErrorMsg: String? = null,
        val expirationDateErrorMsg: String? = null,
        val cvvErrorMsg: String? = null,
        val ibanErrorMsg: String? = null
    ): RegisterCredentialsUiState
}