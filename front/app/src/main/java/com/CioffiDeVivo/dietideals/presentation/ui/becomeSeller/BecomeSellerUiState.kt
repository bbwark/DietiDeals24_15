package com.CioffiDeVivo.dietideals.presentation.ui.becomeSeller

import com.CioffiDeVivo.dietideals.domain.models.CreditCard
import com.CioffiDeVivo.dietideals.domain.models.User


sealed interface BecomeSellerUiState {
    object Success: BecomeSellerUiState

    object Error: BecomeSellerUiState

    object Loading: BecomeSellerUiState

    data class BecomeSellerParams(
        val user: User = User(),
        val creditCard: CreditCard = CreditCard(),
        val addressErrorMsg: String? = null,
        val zipCodeErrorMsg: String? = null,
        val phoneNumberErrorMsg: String? = null,
        val creditCardNumberErrorMsg: String? = null,
        val expirationDate: String = "",
        val expirationDateErrorMsg: String? = null,
        val cvvErrorMsg: String? = null,
        val ibanErrorMsg: String? = null
    ): BecomeSellerUiState
}