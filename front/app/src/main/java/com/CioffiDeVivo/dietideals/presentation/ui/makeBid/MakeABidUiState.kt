package com.CioffiDeVivo.dietideals.presentation.ui.makeBid

import com.CioffiDeVivo.dietideals.domain.models.Auction
import com.CioffiDeVivo.dietideals.domain.models.Bid

sealed interface MakeABidUiState {
    object Success: MakeABidUiState
    object Error: MakeABidUiState
    object Loading: MakeABidUiState

    data class MakeABidParams(
        val auction: Auction = Auction(),
        val bid: Bid = Bid()
    ): MakeABidUiState
}