package com.CioffiDeVivo.dietideals.presentation.ui.addCard

import com.CioffiDeVivo.dietideals.data.models.CreditCard

sealed interface AddCardUiState {
    object Success: AddCardUiState
    object Error: AddCardUiState
    object Loading: AddCardUiState
    data class AddCardParams(
        val creditCardNumberErrorMsg: String? = null,
        val expirationDate: String = "",
        val expirationDateErrorMsg: String? = null,
        val cvvErrorMsg: String? = null,
        val ibanErrorMsg: String? = null,
        val creditCard: CreditCard = CreditCard()
    ): AddCardUiState
}