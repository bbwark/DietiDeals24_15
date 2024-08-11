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

fun Auction.toDataModel(): com.CioffiDeVivo.dietideals.domain.DataModels.Auction {
    return com.CioffiDeVivo.dietideals.domain.DataModels.Auction(
        id = this.id ?: "",
        ownerId = this.ownerId ?: "",
        item = this.item?.toDataModel() ?: com.CioffiDeVivo.dietideals.domain.DataModels.Item(),
        description = this.description ?: "",
        bids = this.bids.map { it.toDataModel() }.toTypedArray(),
        endingDate = this.endingDate?.let { LocalDate.parse(it) },
        minStep = this.minStep ?: "",
        interval = this.interval ?: "",
        expired = this.expired ?: false,
        minAccepted = this.startingPrice ?: "",
        type = this.type ?: AuctionType.None,
        category = this.category ?: AuctionCategory.Other
    )
}

fun Bid.toDataModel(): com.CioffiDeVivo.dietideals.domain.DataModels.Bid {
    return com.CioffiDeVivo.dietideals.domain.DataModels.Bid(
        id = this.id ?: "",
        value = this.value ?: 0F,
        userId = this.userId ?: "",
        date = this.date?.let { ZonedDateTime.parse(it) } ?: ZonedDateTime.now()
    )
}

fun CreditCard.toDataModel(): com.CioffiDeVivo.dietideals.domain.DataModels.CreditCard {
    return com.CioffiDeVivo.dietideals.domain.DataModels.CreditCard(
        creditCardNumber = this.creditCardNumber ?: "",
        expirationDate = this.expirationDate?.let { LocalDate.parse(it) } ?: LocalDate.now(),
        cvv = this.cvv?.toString() ?: "",
        iban = this.iban ?: ""
    )
}

fun Item.toDataModel(): com.CioffiDeVivo.dietideals.domain.DataModels.Item {
    return com.CioffiDeVivo.dietideals.domain.DataModels.Item(
        id = this.id ?: "",
        name = this.name ?: "",
        imagesUri = listOf(Uri.parse(this.imageUrl))
    )
}

fun User.toDataModel(): com.CioffiDeVivo.dietideals.domain.DataModels.User {
    return com.CioffiDeVivo.dietideals.domain.DataModels.User(
        id = this.id ?: "",
        name = this.name ?: "",
        email = this.email ?: "",
        password = this.password ?: "",
        isSeller = this.isSeller ?: false,
        favouriteAuctions = this.favouriteAuctions.map { it.toDataModel() }.toTypedArray(),
        ownedAuctions = this.ownedAuctions.map { it.toDataModel() }.toTypedArray(),
        bio = this.bio ?: "",
        address = this.address ?: "",
        zipCode = this.zipcode ?: "",
        country = this.country ?: "",
        phoneNumber = this.phoneNumber ?: "",
        creditCards = this.creditCards.map { it.toDataModel() }.toTypedArray()
    )
}