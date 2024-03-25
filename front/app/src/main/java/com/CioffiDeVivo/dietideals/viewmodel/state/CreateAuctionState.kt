package com.CioffiDeVivo.dietideals.viewmodel.state

import android.net.Uri
import com.CioffiDeVivo.dietideals.domain.DataModels.AuctionCategory
import com.CioffiDeVivo.dietideals.domain.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.domain.DataModels.Item
import java.time.LocalDate
import java.util.UUID

data class CreateAuctionState(
    val itemName: String = "",
    val itemNameErrorMsg: String? = null,
    val imagesUri: List<Uri?> = emptyList(),
    val imageUriErrorMsg: String? = null,
    val description: String = "",
    val descriptionErrorMsg: String? = null,
    val endingDate: LocalDate? = LocalDate.now(),
    val endingDateErrorMsg: String? = null,
    val minStep: Float = 0.0f,
    val minStepErrorMsg: String? = null,
    val interval: String = "",
    val intervalErrorMsg: String? = null,
    val minAccepted: Float = 0.0f,
    val minAcceptedErrorMsg: String? = null,
    val auctionType: AuctionType = AuctionType.None,
    val auctionCategory: AuctionCategory = AuctionCategory.Other,
    val auctionCategoryErrorMsg: String? = null
)
