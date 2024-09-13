package com.CioffiDeVivo.dietideals.presentation.ui.createAuction

import com.CioffiDeVivo.dietideals.domain.models.Auction

data class CreateAuctionState(
    val itemNameErrorMsg: String? = "",
    val imageUriErrorMsg: String? = "",
    val descriptionErrorMsg: String? = "",
    val endingDateErrorMsg: String? = "",
    val minStepErrorMsg: String? = "",
    val intervalErrorMsg: String? = "",
    val minAcceptedErrorMsg: String? = "",
    val auctionCategoryErrorMsg: String? = "",
    val auction: Auction = Auction()
)
