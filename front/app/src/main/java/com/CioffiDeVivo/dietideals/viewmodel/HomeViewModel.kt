package com.CioffiDeVivo.dietideals.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.CioffiDeVivo.dietideals.domain.DataModels.Auction
import com.CioffiDeVivo.dietideals.domain.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.domain.DataModels.Bid
import com.CioffiDeVivo.dietideals.domain.DataModels.CreditCard
import com.CioffiDeVivo.dietideals.domain.DataModels.Item
import com.CioffiDeVivo.dietideals.domain.DataModels.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.ZonedDateTime

class HomeViewModel : ViewModel() {

    private val _userState = MutableStateFlow(User())
    val userState: StateFlow<User> = _userState.asStateFlow()
    private val _auctionState = MutableStateFlow<ArrayList<Auction>>(arrayListOf())
    val auctionsState = _auctionState.asStateFlow()

    var user by mutableStateOf(
        User(
            "",
            "Nametest Surnametest",
            "",
            "passwordtest",
            creditCards = arrayOf(
                CreditCard("556666666666", LocalDate.now().plusYears(1),"222"),
                CreditCard("456666666666", LocalDate.now().plusYears(2), "222"),
                CreditCard("356666666666", LocalDate.now().plusYears(2), "222")
            )
        )
    )

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

    //Rest fo getting the correct Auctions for each category(latest, ending, partecipated...)

    val testItem = Item(id = "", imagesUri = listOf(), name = "Desktop Computer")
    val testLatestAuctions: Array<Auction> = arrayOf(
        Auction(
            id = "",
            ownerId = "",
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 16),
            expired = false,
            type = AuctionType.English
        ),
        Auction(
            id = "",
            ownerId = "",
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 17),
            expired = false,
            type = AuctionType.English
        ),
        Auction(
            id = "",
            ownerId = "",
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 10),
            expired = false,
            type = AuctionType.Silent
        ),
        Auction(
            id = "",
            ownerId = "",
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 9),
            expired = false,
            type = AuctionType.Silent
        ),
        Auction(
            id = "",
            ownerId = "",
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 8),
            expired = false,
            type = AuctionType.English
        )
    )

    val testEndingAuctions: Array<Auction> = arrayOf(
        Auction(
            id = "",
            ownerId = "",
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 16),
            expired = false,
            type = AuctionType.English
        ),
        Auction(
            id = "",
            ownerId = "",
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 17),
            expired = false,
            type = AuctionType.English
        ),
        Auction(
            id = "",
            ownerId = "",
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 10),
            expired = false,
            type = AuctionType.Silent
        ),
        Auction(
            id = "",
            ownerId = "",
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 9),
            expired = false,
            type = AuctionType.Silent
        ),
        Auction(
            id = "",
            ownerId = "",
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 8),
            expired = false,
            type = AuctionType.English
        )
    )

    val testPartecipatedAuctions: Array<Auction> = arrayOf(
        Auction(
            id = "",
            ownerId = "",
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 16),
            expired = false,
            type = AuctionType.English
        ),
        Auction(
            id = "",
            ownerId = "",
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 17),
            expired = false,
            type = AuctionType.English
        ),
        Auction(
            id = "",
            ownerId = "",
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 10),
            expired = false,
            type = AuctionType.Silent
        ),
        Auction(
            id = "",
            ownerId = "",
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 9),
            expired = false,
            type = AuctionType.Silent
        ),
        Auction(
            id = "",
            ownerId = "",
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 8),
            expired = false,
            type = AuctionType.English
        )
    )
}