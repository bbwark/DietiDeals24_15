package com.CioffiDeVivo.dietideals.viewmodel.state

import android.net.Uri
import com.CioffiDeVivo.dietideals.domain.DataModels.Auction
import com.CioffiDeVivo.dietideals.domain.DataModels.AuctionCategory
import com.CioffiDeVivo.dietideals.domain.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.domain.DataModels.Item
import java.time.LocalDate
import java.util.UUID

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
