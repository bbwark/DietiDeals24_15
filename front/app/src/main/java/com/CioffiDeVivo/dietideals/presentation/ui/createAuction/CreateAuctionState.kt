package com.CioffiDeVivo.dietideals.presentation.ui.createAuction

import com.CioffiDeVivo.dietideals.domain.models.Auction

data class CreateAuctionState(
    val itemNameErrorMsg: String? = null,
    val imageUriErrorMsg: String? = null,
    val auctionTypeErrorMsg: String? = null,
    val descriptionErrorMsg: String? = null,
    val endingDateErrorMsg: String? = null,
    val minStepErrorMsg: String? = null,
    val intervalErrorMsg: String? = null,
    val minAcceptedErrorMsg: String? = null,
    val auction: Auction = Auction()
)
