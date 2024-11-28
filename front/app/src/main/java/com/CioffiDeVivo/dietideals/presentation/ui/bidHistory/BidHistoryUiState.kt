package com.CioffiDeVivo.dietideals.presentation.ui.bidHistory

import com.CioffiDeVivo.dietideals.data.models.Auction
import com.CioffiDeVivo.dietideals.data.models.User

sealed interface BidHistoryUiState {
    data class Success(val auction: Auction, val bidders: Array<User>): BidHistoryUiState {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Success

            return bidders.contentEquals(other.bidders)
        }

        override fun hashCode(): Int {
            return bidders.contentHashCode()
        }
    }

    object SuccessOnWinningBid: BidHistoryUiState
    object Error: BidHistoryUiState
    object Loading: BidHistoryUiState
}