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

    var auctionCreatedByUser: Array<Auction> = arrayOf(
        Auction(
            "",
            "",
            Item(id = "", name = "First Auction"),
            endingDate = LocalDate.now().plusMonths(2),
            expired = false,
            type = AuctionType.English
        ),
        Auction(
            "",
            "",
            Item(id = "", name = "Second Auction"),
            endingDate = LocalDate.now().plusMonths(2),
            expired = false,
            type = AuctionType.English
        ),
        Auction(
            "",
            "",
            Item(id = "", name = "Third Auction"),
            endingDate = LocalDate.now().plusMonths(2),
            expired = false,
            type = AuctionType.English
        )
    )

}