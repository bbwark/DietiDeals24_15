package com.CioffiDeVivo.dietideals.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.CioffiDeVivo.dietideals.domain.DataModels.Auction
import com.CioffiDeVivo.dietideals.domain.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.domain.DataModels.Bid
import com.CioffiDeVivo.dietideals.domain.DataModels.Item
import com.CioffiDeVivo.dietideals.domain.DataModels.ObservedUser
import com.CioffiDeVivo.dietideals.viewmodel.state.RegistrationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
class BidHistoryViewModel : ViewModel() {

    private val _auctionState = MutableStateFlow(Auction())
    val auctionState: StateFlow<Auction> = _auctionState.asStateFlow()
    private val _bidState = MutableStateFlow(Bid())
    val bidState: StateFlow<Bid> = _bidState.asStateFlow()

    var selectedAuction by mutableStateOf(
        Auction(
            "",
            "",
            Item(id = "", name = ""),
            bids = arrayOf(
                Bid(
                    "",
                    11f,
                    "",
                    ZonedDateTime.now().minusDays(5)
                )
            ),
            endingDate = LocalDate.now(),
            expired = false,
            type = AuctionType.English
        )
    )
    var selectedAuctionBidders: List<ObservedUser> = listOf() //need to write a function that takes all the users ID available in selectedAuction.bids and then make requests to the server based on those IDs to fill this list
}