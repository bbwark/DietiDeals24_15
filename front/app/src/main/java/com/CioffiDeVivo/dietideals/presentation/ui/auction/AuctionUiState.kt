package com.CioffiDeVivo.dietideals.presentation.ui.auction

import com.CioffiDeVivo.dietideals.domain.models.Auction
import com.CioffiDeVivo.dietideals.domain.models.User

sealed interface AuctionUiState {
    data class Success(val auction: Auction, val owner: User, val isOwner: Boolean): AuctionUiState
    object Error: AuctionUiState
    object Loading: AuctionUiState
}