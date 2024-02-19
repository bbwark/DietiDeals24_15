package com.CioffiDeVivo.dietideals.DataModels

import java.time.LocalDate
import java.util.UUID

class Auction(
    id: UUID,
    ownerId: UUID,
    item: Item,
    bids: Array<Bid> = arrayOf(),
    endingDate: LocalDate?,
    expired: Boolean,
    auctionType: AuctionType
){
    val id: UUID = id
    val ownerId: UUID = ownerId
    val item: Item = item
    var bids: Array<Bid> = bids
    var endingDate: LocalDate? = endingDate
    var expired: Boolean = expired
    val auctionType: AuctionType = auctionType
}