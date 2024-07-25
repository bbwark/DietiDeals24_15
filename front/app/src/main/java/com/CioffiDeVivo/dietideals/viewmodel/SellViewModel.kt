package com.CioffiDeVivo.dietideals.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.CioffiDeVivo.dietideals.domain.DataModels.Auction
import com.CioffiDeVivo.dietideals.domain.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.domain.DataModels.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.util.UUID

class SellViewModel: ViewModel() {

    private val _userAuctionState = MutableStateFlow<ArrayList<Auction>>(arrayListOf())
    val userAuctionState = _userAuctionState.asStateFlow()
    //RESTAPI per prendere auction che corrispondono all' owner id dell utente loggato (Forse con SharedViewModel cosi si ha il dato del utente loggato)

    @RequiresApi(Build.VERSION_CODES.O)
    var auctionCreatedByUser: Array<Auction> = arrayOf(
        Auction(
            UUID.randomUUID(),
            UUID.randomUUID(),
            Item(id = UUID.randomUUID(), name = "First Auction"),
            endingDate = LocalDate.now().plusMonths(2),
            expired = false,
            auctionType = AuctionType.English
        ),
        Auction(
            UUID.randomUUID(),
            UUID.randomUUID(),
            Item(id = UUID.randomUUID(), name = "Second Auction"),
            endingDate = LocalDate.now().plusMonths(2),
            expired = false,
            auctionType = AuctionType.English
        ),
        Auction(
            UUID.randomUUID(),
            UUID.randomUUID(),
            Item(id = UUID.randomUUID(), name = "Third Auction"),
            endingDate = LocalDate.now().plusMonths(2),
            expired = false,
            auctionType = AuctionType.English
        )
    )

}