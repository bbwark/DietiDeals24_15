package com.CioffiDeVivo.dietideals.Events

import com.CioffiDeVivo.dietideals.DataModels.AuctionType

sealed class CreateAuctionEvents {

    data class ItemNameChanged(val itemName: String): CreateAuctionEvents()

    data class AuctionTypeChanged(val auctionType: AuctionType): CreateAuctionEvents()

    data class IntervalChanged(val interval: String): CreateAuctionEvents()

    data class MinStepChanged(val minStep: String): CreateAuctionEvents()

    data class EndingDateChanged(val endingDate: String): CreateAuctionEvents()

    data class DescriptionChanged(val description: String): CreateAuctionEvents()

    data class MinAcceptedChanged(val minAccepted: String): CreateAuctionEvents()

    object Submit: CreateAuctionEvents()

}