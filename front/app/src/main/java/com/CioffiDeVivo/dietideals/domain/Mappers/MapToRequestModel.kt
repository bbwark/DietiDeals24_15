package com.CioffiDeVivo.dietideals.domain.Mappers

import com.CioffiDeVivo.dietideals.domain.DataModels.Auction
import com.CioffiDeVivo.dietideals.domain.DataModels.Bid
import com.CioffiDeVivo.dietideals.domain.DataModels.CreditCard
import com.CioffiDeVivo.dietideals.domain.DataModels.Item
import com.CioffiDeVivo.dietideals.domain.DataModels.User

fun Auction.toRequestModel(): com.CioffiDeVivo.dietideals.domain.RequestModels.Auction {
    return com.CioffiDeVivo.dietideals.domain.RequestModels.Auction(
        id = this.id,
        ownerId = this.ownerId,
        item = this.item.toRequestModel(),
        description = this.description,
        bids = this.bids.map { it.toRequestModel() }.toCollection(ArrayList()),
        endingDate = this.endingDate?.toString(),
        minStep = this.minStep,
        interval = this.interval,
        expired = this.expired,
        startingPrice = this.minAccepted,
        type = this.type,
        category = this.category
    )
}

fun Bid.toRequestModel(): com.CioffiDeVivo.dietideals.domain.RequestModels.Bid {
    return com.CioffiDeVivo.dietideals.domain.RequestModels.Bid(
        id = this.id,
        value = this.value,
        userId = this.userId,
        date = this.date.toString(),
        auctionId = null // To set from the context
    )
}

fun CreditCard.toRequestModel(): com.CioffiDeVivo.dietideals.domain.RequestModels.CreditCard {
    return com.CioffiDeVivo.dietideals.domain.RequestModels.CreditCard(
        creditCardNumber = this.creditCardNumber,
        expirationDate = this.expirationDate.toString(),
        cvv = this.cvv.toIntOrNull(),
        iban = this.iban,
        ownerId = null // To set from the context
    )
}

fun Item.toRequestModel(): com.CioffiDeVivo.dietideals.domain.RequestModels.Item {
    return com.CioffiDeVivo.dietideals.domain.RequestModels.Item(
        id = this.id.toString(),
        name = this.name,
        imageUrl = this.imagesUri.firstOrNull()?.toString(),
        auctionId = null // To set from the context
    )
}

fun User.toRequestModel(): com.CioffiDeVivo.dietideals.domain.RequestModels.User {
    return com.CioffiDeVivo.dietideals.domain.RequestModels.User(
        id = this.id,
        name = "${this.name} ${this.surname}",
        email = this.email,
        password = this.password,
        isSeller = this.isSeller,
        favouriteAuctions = this.favouriteAuctions.map { it.toRequestModel() }.toCollection(ArrayList()),
        ownedAuctions = this.ownedAuctions.map { it.toRequestModel() }.toCollection(ArrayList()),
        bio = this.bio,
        address = this.address,
        zipcode = this.zipCode,
        country = this.country,
        phoneNumber = this.phoneNumber,
        creditCards = this.creditCards.map { it.toRequestModel() }.toCollection(ArrayList()),
    )
}