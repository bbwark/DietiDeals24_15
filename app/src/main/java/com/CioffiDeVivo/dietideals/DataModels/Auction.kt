package com.CioffiDeVivo.dietideals.DataModels

import java.time.LocalDate
import java.util.UUID

class Auction(
    id: UUID,
    bids: Array<Bid> = arrayOf(),
    endingDate: LocalDate,
    auctionType: AuctionType
){
    val id: UUID = id
    var bids: Array<Bid> = bids
    var endingDate: LocalDate = endingDate
    val auctionType: AuctionType = auctionType
}