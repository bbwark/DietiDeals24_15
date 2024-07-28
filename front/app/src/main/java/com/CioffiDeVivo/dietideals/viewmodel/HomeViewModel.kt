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
import com.CioffiDeVivo.dietideals.domain.DataModels.CreditCard
import com.CioffiDeVivo.dietideals.domain.DataModels.Item
import com.CioffiDeVivo.dietideals.domain.DataModels.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel : ViewModel() {

    private val _userState = MutableStateFlow(User())
    val userState: StateFlow<User> = _userState.asStateFlow()
    private val _auctionState = MutableStateFlow<ArrayList<Auction>>(arrayListOf())
    val auctionsState = _auctionState.asStateFlow()

    var user by mutableStateOf(
        User(
            UUID.randomUUID(),
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
            UUID.randomUUID(),
            UUID.randomUUID(),
            Item(id = UUID.randomUUID(), name = ""),
            bids = arrayOf(
                Bid(
                    UUID.randomUUID(),
                    11f,
                    UUID.randomUUID(),
                    ZonedDateTime.now().minusDays(5)
                )
            ),
            endingDate = LocalDate.now(),
            expired = false,
            auctionType = AuctionType.English
        )
    )

    //Rest fo getting the correct Auctions for each category(latest, ending, partecipated...)

    val testItem = Item(id = UUID.randomUUID(), imagesUri = listOf(), name = "Desktop Computer")
    val testLatestAuctions: Array<Auction> = arrayOf(
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 16),
            expired = false,
            auctionType = AuctionType.English
        ),
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 17),
            expired = false,
            auctionType = AuctionType.English
        ),
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 10),
            expired = false,
            auctionType = AuctionType.Silent
        ),
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 9),
            expired = false,
            auctionType = AuctionType.Silent
        ),
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 8),
            expired = false,
            auctionType = AuctionType.English
        )
    )

    val testEndingAuctions: Array<Auction> = arrayOf(
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 16),
            expired = false,
            auctionType = AuctionType.English
        ),
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 17),
            expired = false,
            auctionType = AuctionType.English
        ),
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 10),
            expired = false,
            auctionType = AuctionType.Silent
        ),
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 9),
            expired = false,
            auctionType = AuctionType.Silent
        ),
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 8),
            expired = false,
            auctionType = AuctionType.English
        )
    )

    val testPartecipatedAuctions: Array<Auction> = arrayOf(
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 16),
            expired = false,
            auctionType = AuctionType.English
        ),
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 17),
            expired = false,
            auctionType = AuctionType.English
        ),
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 10),
            expired = false,
            auctionType = AuctionType.Silent
        ),
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 9),
            expired = false,
            auctionType = AuctionType.Silent
        ),
        Auction(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            item = testItem,
            bids = arrayOf(),
            endingDate = LocalDate.of(2023, 12, 8),
            expired = false,
            auctionType = AuctionType.English
        )
    )
}