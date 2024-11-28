package com.CioffiDeVivo.dietideals.presentation.ui.createAuction

import com.CioffiDeVivo.dietideals.data.models.AuctionCategory
import com.CioffiDeVivo.dietideals.data.models.AuctionType

sealed class CreateAuctionEvents {
    data class ItemNameChanged(val itemName: String): CreateAuctionEvents()
    object ItemNameDeleted: CreateAuctionEvents()
    data class ImagesChanged(val image: String): CreateAuctionEvents()
    data class ImagesDeleted(val index: Int): CreateAuctionEvents()
    data class AuctionTypeChanged(val auctionType: AuctionType): CreateAuctionEvents()
    data class AuctionCategoryChanged(val auctionCategory: AuctionCategory): CreateAuctionEvents()
    data class IntervalChanged(val interval: String): CreateAuctionEvents()
    object IntervalDeleted: CreateAuctionEvents()
    data class MinStepChanged(val minStep: String): CreateAuctionEvents()
    data class EndingDateChanged(val endingDate: Long): CreateAuctionEvents()
    data class DescriptionChanged(val description: String): CreateAuctionEvents()
    object DescriptionDeleted: CreateAuctionEvents()
    data class MinAcceptedChanged(val minAccepted: String): CreateAuctionEvents()
    data class MaxBidChanged(val maxBid: String): CreateAuctionEvents()
}