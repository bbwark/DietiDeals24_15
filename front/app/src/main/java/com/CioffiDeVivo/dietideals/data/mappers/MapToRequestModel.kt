package com.CioffiDeVivo.dietideals.data.mappers

import com.CioffiDeVivo.dietideals.data.models.Auction
import com.CioffiDeVivo.dietideals.data.models.Bid
import com.CioffiDeVivo.dietideals.data.models.CreditCard
import com.CioffiDeVivo.dietideals.data.models.Item
import com.CioffiDeVivo.dietideals.data.models.User

fun Auction.toRequestModel(): com.CioffiDeVivo.dietideals.data.requestModels.Auction {
    return com.CioffiDeVivo.dietideals.data.requestModels.Auction(
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
        maxBid = this.maxBid,
        type = this.type,
        category = this.category
    )
}

fun Bid.toRequestModel(): com.CioffiDeVivo.dietideals.data.requestModels.Bid {
    return com.CioffiDeVivo.dietideals.data.requestModels.Bid(
        id = this.id,
        value = this.value,
        userId = this.userId,
        date = this.date.toString(),
        auctionId = null // To set from the context
    )
}

fun CreditCard.toRequestModel(): com.CioffiDeVivo.dietideals.data.requestModels.CreditCard {
    return com.CioffiDeVivo.dietideals.data.requestModels.CreditCard(
        creditCardNumber = this.creditCardNumber,
        expirationDate = this.expirationDate.toString(),
        cvv = this.cvv.toIntOrNull(),
        iban = this.iban,
        ownerId = null // To set from the context
    )
}

fun Item.toRequestModel(): com.CioffiDeVivo.dietideals.data.requestModels.Item {
    return com.CioffiDeVivo.dietideals.data.requestModels.Item(
        id = this.id,
        name = this.name,
        imageUrl = this.imagesUri.joinToString(" "),
        auctionId = null // Da impostare dal contesto
    )
}

fun User.toRequestModel(): com.CioffiDeVivo.dietideals.data.requestModels.User {
    return com.CioffiDeVivo.dietideals.data.requestModels.User(
        id = this.id,
        name = "${this.name} ${this.surname}",
        email = this.email,
        password = this.password,
        isSeller = this.isSeller,
        favouriteAuctions = this.favouriteAuctions.map { it.toRequestModel() }
            .toCollection(ArrayList()),
        ownedAuctions = this.ownedAuctions.map { it.toRequestModel() }.toCollection(ArrayList()),
        bio = this.bio,
        address = this.address,
        zipcode = this.zipCode,
        country = this.country,
        phoneNumber = this.phoneNumber,
        creditCards = this.creditCards.map { it.toRequestModel() }.toCollection(ArrayList()),
        deviceTokens = this.deviceTokens.map { it } as ArrayList<String>
    )
}