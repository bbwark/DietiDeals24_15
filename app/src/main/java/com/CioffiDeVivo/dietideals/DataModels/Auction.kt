package com.CioffiDeVivo.dietideals.DataModels

import java.time.LocalDate
import java.util.UUID

class Auction(
    id: UUID,
    bids: Array<Bid> = arrayOf(),
    date: LocalDate,
    auctionType: AuctionType
){
    val id: UUID = id
    var bids: Array<Bid> = bids
    var date: LocalDate = date
    val auctionType: AuctionType = auctionType
}