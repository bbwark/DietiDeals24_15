package com.CioffiDeVivo.dietideals.presentation.ui.bidHistory

import com.CioffiDeVivo.dietideals.data.models.Auction
import com.CioffiDeVivo.dietideals.data.models.User

sealed interface BidHistoryUiState {
    data class Success(val auction: Auction, val bidders: Array<User>): BidHistoryUiState
    object SuccessOnWinningBid: BidHistoryUiState
    object Error: BidHistoryUiState
    object Loading: BidHistoryUiState
}