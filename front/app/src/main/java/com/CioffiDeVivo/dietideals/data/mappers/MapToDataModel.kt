package com.CioffiDeVivo.dietideals.data.mappers

import com.CioffiDeVivo.dietideals.data.models.AuctionCategory
import com.CioffiDeVivo.dietideals.data.models.AuctionType
import com.CioffiDeVivo.dietideals.data.models.Country
import com.CioffiDeVivo.dietideals.data.requestModels.AuctionRequest
import com.CioffiDeVivo.dietideals.data.requestModels.BidRequest
import com.CioffiDeVivo.dietideals.data.requestModels.CreditCardRequest
import com.CioffiDeVivo.dietideals.data.requestModels.Item
import com.CioffiDeVivo.dietideals.data.requestModels.User
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/*fun AuctionRequest.toDataModel(): com.CioffiDeVivo.dietideals.data.models.Auction {
    return com.CioffiDeVivo.dietideals.data.models.Auction(
        id = this.id ?: "",
        ownerId = this.ownerId ?: "",
        item = this.item?.toDataModel() ?: com.CioffiDeVivo.dietideals.data.models.Item(),
        description = this.description ?: "",
        bids = this.bidRequests.map { it.toDataModel() }.toTypedArray(),
        endingDate = this.endingDate?.let { LocalDateTime.parse(it) },
        minStep = this.minStep ?: "",
        interval = this.interval ?: "",
        expired = this.expired ?: false,
        minAccepted = this.startingPrice ?: "",
        maxBid = this.maxBid ?: "",
        type = this.type ?: AuctionType.None,
        category = this.category ?: AuctionCategory.Other
    )
}

fun BidRequest.toDataModel(): com.CioffiDeVivo.dietideals.data.models.Bid {
    return com.CioffiDeVivo.dietideals.data.models.Bid(
        id = this.id ?: "",
        value = this.value ?: 0F,
        userId = this.userId ?: "",
        date = this.date?.let {
            LocalDateTime.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss:SSS")).atZone(
                ZoneId.systemDefault()
            )
        } ?: ZonedDateTime.now()
    )
}

fun CreditCardRequest.toDataModel(): com.CioffiDeVivo.dietideals.data.models.CreditCard {
    return com.CioffiDeVivo.dietideals.data.models.CreditCard(
        creditCardNumber = this.creditCardNumber ?: "",
        expirationDate = this.expirationDate?.let { LocalDate.parse(it) } ?: LocalDate.now(),
        cvv = this.cvv?.toString() ?: "",
        iban = this.iban ?: ""
    )
}

fun Item.toDataModel(): com.CioffiDeVivo.dietideals.data.models.Item {
    return com.CioffiDeVivo.dietideals.data.models.Item(
        id = this.id ?: "",
        name = this.name ?: "",
        imagesUri = this.imageUrl?.split(" ") ?: emptyList()
    )
}

fun User.toDataModel(): com.CioffiDeVivo.dietideals.data.models.User {
    return com.CioffiDeVivo.dietideals.data.models.User(
        id = this.id ?: "",
        name = this.name ?: "",
        email = this.email ?: "",
        password = this.password ?: "",
        isSeller = this.isSeller ?: false,
        favouriteAuctions = this.favouriteAuctionRequests.map { it.toDataModel() }.toTypedArray(),
        ownedAuctions = this.ownedAuctionRequests.map { it.toDataModel() }.toTypedArray(),
        bio = this.bio ?: "",
        address = this.address ?: "",
        zipCode = this.zipcode ?: "",
        country = this.country ?: Country.Italy,
        phoneNumber = this.phoneNumber ?: "",
        creditCards = this.creditCardRequests.map { it.toDataModel() }.toTypedArray(),
        deviceTokens = this.deviceTokens.map { it }.toTypedArray()
    )
}*/