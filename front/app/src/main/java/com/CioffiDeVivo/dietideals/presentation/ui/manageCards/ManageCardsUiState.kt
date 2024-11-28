package com.CioffiDeVivo.dietideals.presentation.ui.manageCards

import com.CioffiDeVivo.dietideals.data.models.CreditCard

sealed interface ManageCardsUiState {
    data class Success(val creditCards: Array<CreditCard>): ManageCardsUiState {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Success

            return creditCards.contentEquals(other.creditCards)
        }

        override fun hashCode(): Int {
            return creditCards.contentHashCode()
        }
    }

    object Error: ManageCardsUiState
    object Loading: ManageCardsUiState
}