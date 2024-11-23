package com.CioffiDeVivo.dietideals.presentation.ui.createAuction

import android.content.Context
import android.net.Uri
import com.CioffiDeVivo.dietideals.data.models.AuctionCategory
import com.CioffiDeVivo.dietideals.data.models.AuctionType

sealed class CreateAuctionEvents {
    data class ItemNameChanged(val itemName: String): CreateAuctionEvents()
    data class ItemNameDeleted(val itemName: String): CreateAuctionEvents()
    data class ImagesChanged(val image: String): CreateAuctionEvents()
    data class ImagesDeleted(val index: Int): CreateAuctionEvents()
    data class AuctionTypeChanged(val auctionType: AuctionType): CreateAuctionEvents()
    data class AuctionCategoryChanged(val auctionCategory: AuctionCategory): CreateAuctionEvents()
    data class IntervalChanged(val interval: String): CreateAuctionEvents()
    data class IntervalDeleted(val interval: String): CreateAuctionEvents()
    data class MinStepChanged(val minStep: String): CreateAuctionEvents()
    data class EndingDateChanged(val endingDate: Long): CreateAuctionEvents()
    data class DescriptionChanged(val description: String): CreateAuctionEvents()
    data class DescriptionDeleted(val description: String): CreateAuctionEvents()
    data class MinAcceptedChanged(val minAccepted: String): CreateAuctionEvents()
    data class MaxBidChanged(val maxBid: String): CreateAuctionEvents()
}