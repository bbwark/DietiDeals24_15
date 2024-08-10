package com.CioffiDeVivo.dietideals.domain.Mappers

import android.net.Uri
import com.CioffiDeVivo.dietideals.domain.DataModels.AuctionCategory
import com.CioffiDeVivo.dietideals.domain.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.domain.RequestModels.Auction
import com.CioffiDeVivo.dietideals.domain.RequestModels.Bid
import com.CioffiDeVivo.dietideals.domain.RequestModels.CreditCard
import com.CioffiDeVivo.dietideals.domain.RequestModels.Item
import com.CioffiDeVivo.dietideals.domain.RequestModels.User
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.UUID

fun Auction.toViewModel(): com.CioffiDeVivo.dietideals.domain.DataModels.Auction {
    return com.CioffiDeVivo.dietideals.domain.DataModels.Auction(
        id = this.id?.let { UUID.fromString(it) } ?: UUID.randomUUID(),
        ownerId = this.ownerId?.let { UUID.fromString(it) } ?: UUID.randomUUID(),
        item = this.item?.toViewModel() ?: com.CioffiDeVivo.dietideals.domain.DataModels.Item(),
        description = this.description ?: "",
        bids = this.bids.map { it.toViewModel() }.toTypedArray(),
        endingDate = this.endingDate?.let { LocalDate.parse(it) },
        minStep = this.minStep ?: "",
        interval = this.interval ?: "",
        expired = this.expired ?: false,
        minAccepted = this.startingPrice ?: "",
        auctionType = this.type ?: AuctionType.None,
        auctionCategory = this.category ?: AuctionCategory.Other
    )
}

fun Bid.toViewModel(): com.CioffiDeVivo.dietideals.domain.DataModels.Bid {
    return com.CioffiDeVivo.dietideals.domain.DataModels.Bid(
        id = this.id?.let { UUID.fromString(it) } ?: UUID.randomUUID(),
        value = this.value ?: 0F,
        userId = this.userId?.let { UUID.fromString(it) } ?: UUID.randomUUID(),
        date = this.date?.let { ZonedDateTime.parse(it) } ?: ZonedDateTime.now()
    )
}

fun CreditCard.toViewModel(): com.CioffiDeVivo.dietideals.domain.DataModels.CreditCard {
    return com.CioffiDeVivo.dietideals.domain.DataModels.CreditCard(
        creditCardNumber = this.creditCardNumber ?: "",
        expirationDate = this.expirationDate?.let { LocalDate.parse(it) } ?: LocalDate.now(),
        cvv = this.cvv?.toString() ?: "",
        iban = this.iban ?: ""
    )
}

fun Item.toViewModel(): com.CioffiDeVivo.dietideals.domain.DataModels.Item {
    return com.CioffiDeVivo.dietideals.domain.DataModels.Item(
        id = this.id?.let { UUID.fromString(it) } ?: UUID.randomUUID(),
        name = this.name ?: "",
        imagesUri = listOf(Uri.parse(this.imageUrl))
    )
}

fun User.toViewModel(): com.CioffiDeVivo.dietideals.domain.DataModels.User {
    return com.CioffiDeVivo.dietideals.domain.DataModels.User(
        id = this.id?.let { UUID.fromString(it) } ?: UUID.randomUUID(),
        name = this.name ?: "",
        email = this.email ?: "",
        password = this.password ?: "",
        isSeller = this.isSeller ?: false,
        favouriteAuctions = this.favouriteAuctionEntities.map { it.toViewModel() }.toTypedArray(),
        bio = this.bio ?: "",
        address = this.address ?: "",
        zipCode = this.zipcode ?: "",
        country = this.country ?: "",
        phoneNumber = this.phoneNumber ?: "",
        creditCards = this.creditCards.map { it.toViewModel() }.toTypedArray()
    )
}