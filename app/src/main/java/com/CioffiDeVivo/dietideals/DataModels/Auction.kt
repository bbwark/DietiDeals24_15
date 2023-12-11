package com.CioffiDeVivo.dietideals.DataModels

import java.time.LocalDate

class Auction(
    bids: Array<Bid> = arrayOf(),
    date: LocalDate,
    auctionType: AuctionType
){
    var bids: Array<Bid> = bids
    var date: LocalDate = date
    val auctionType: AuctionType = auctionType
}