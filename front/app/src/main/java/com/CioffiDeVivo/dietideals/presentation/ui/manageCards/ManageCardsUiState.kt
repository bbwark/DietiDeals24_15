package com.CioffiDeVivo.dietideals.presentation.ui.manageCards

import com.CioffiDeVivo.dietideals.data.models.CreditCard

sealed interface ManageCardsUiState {
    data class Success(val creditCards: Array<CreditCard>): ManageCardsUiState
    object Error: ManageCardsUiState
    object Loading: ManageCardsUiState
}