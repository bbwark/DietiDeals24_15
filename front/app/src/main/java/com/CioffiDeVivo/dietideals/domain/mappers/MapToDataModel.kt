package com.CioffiDeVivo.dietideals.domain.mappers

import android.net.Uri
import com.CioffiDeVivo.dietideals.domain.models.AuctionCategory
import com.CioffiDeVivo.dietideals.domain.models.AuctionType
import com.CioffiDeVivo.dietideals.domain.models.Country
import com.CioffiDeVivo.dietideals.domain.requestModels.Auction
import com.CioffiDeVivo.dietideals.domain.requestModels.Bid
import com.CioffiDeVivo.dietideals.domain.requestModels.CreditCard
import com.CioffiDeVivo.dietideals.domain.requestModels.Item
import com.CioffiDeVivo.dietideals.domain.requestModels.User
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun Auction.toDataModel(): com.CioffiDeVivo.dietideals.domain.models.Auction {
    return com.CioffiDeVivo.dietideals.domain.models.Auction(
        id = this.id ?: "",
        ownerId = this.ownerId ?: "",
        item = this.item?.toDataModel() ?: com.CioffiDeVivo.dietideals.domain.models.Item(),
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

fun Bid.toDataModel(): com.CioffiDeVivo.dietideals.domain.models.Bid {
    return com.CioffiDeVivo.dietideals.domain.models.Bid(
        id = this.id ?: "",
        value = this.value ?: 0F,
        userId = this.userId ?: "",
        date = this.date?.let { LocalDateTime.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss:SSS")).atZone(
            ZoneId.systemDefault()) } ?: ZonedDateTime.now()
    )
}

fun CreditCard.toDataModel(): com.CioffiDeVivo.dietideals.domain.models.CreditCard {
    return com.CioffiDeVivo.dietideals.domain.models.CreditCard(
        creditCardNumber = this.creditCardNumber ?: "",
        expirationDate = this.expirationDate?.let { LocalDate.parse(it) } ?: LocalDate.now(),
        cvv = this.cvv?.toString() ?: "",
        iban = this.iban ?: ""
    )
}

fun Item.toDataModel(): com.CioffiDeVivo.dietideals.domain.models.Item {
    return com.CioffiDeVivo.dietideals.domain.models.Item(
        id = this.id ?: "",
        name = this.name ?: "",
        imagesUri = listOf(Uri.parse(this.imageUrl?:""))
    )
}

fun User.toDataModel(): com.CioffiDeVivo.dietideals.domain.models.User {
    return com.CioffiDeVivo.dietideals.domain.models.User(
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
        country = this.country ?: Country.Italy,
        phoneNumber = this.phoneNumber ?: "",
        creditCards = this.creditCards.map { it.toDataModel() }.toTypedArray()
    )
}