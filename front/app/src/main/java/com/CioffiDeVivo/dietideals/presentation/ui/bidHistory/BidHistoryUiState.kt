package com.CioffiDeVivo.dietideals.presentation.ui.bidHistory

import com.CioffiDeVivo.dietideals.domain.models.User

sealed interface BidHistoryUiState {
    data class Success(val bidders: List<User>): BidHistoryUiState
    object Error: BidHistoryUiState
    object Loading: BidHistoryUiState
}