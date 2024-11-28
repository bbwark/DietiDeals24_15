package com.CioffiDeVivo.dietideals.presentation.ui.createAuction

import com.CioffiDeVivo.dietideals.data.models.Auction

sealed interface CreateAuctionUiState {
    object Success: CreateAuctionUiState
    object Error: CreateAuctionUiState
    object Loading: CreateAuctionUiState
    data class CreateAuctionParams(
        val itemNameErrorMsg: String? = null,
        val imageUriErrorMsg: String? = null,
        val auctionTypeErrorMsg: String? = null,
        val descriptionErrorMsg: String? = null,
        val endingDateErrorMsg: String? = null,
        val minStepErrorMsg: String? = null,
        val intervalErrorMsg: String? = null,
        val minAcceptedErrorMsg: String? = null,
        val maxBidErrorMsg: String? = null,
        val auction: Auction = Auction()
    ): CreateAuctionUiState
}