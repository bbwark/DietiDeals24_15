package com.CioffiDeVivo.dietideals

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.CioffiDeVivo.dietideals.DataModels.Auction
import com.CioffiDeVivo.dietideals.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.DataModels.Bid
import com.CioffiDeVivo.dietideals.DataModels.CreditCard
import com.CioffiDeVivo.dietideals.DataModels.Item
import com.CioffiDeVivo.dietideals.DataModels.ObservedUser
import com.CioffiDeVivo.dietideals.DataModels.User
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
class DietiDealsViewModel : ViewModel() {
    var creditCard by mutableStateOf(CreditCard("", "", LocalDate.now()))
    var user by mutableStateOf(
        User(
            UUID.randomUUID(),
            "Nametest Surnametest",
            "emailtest@test.com",
            "passwordtest",
            creditCards = arrayOf(
                CreditCard("556666666666", "222", LocalDate.now().plusYears(1)),
                CreditCard("456666666666", "222", LocalDate.now().plusYears(2)),
                CreditCard("356666666666", "222", LocalDate.now().plusYears(2))
            )
        )
    )
    var selectedNavBarItem: MutableState<Int> = mutableStateOf(0)

    var selectedAuction by mutableStateOf(
        Auction(
            UUID.randomUUID(),
            UUID.randomUUID(),
            Item(id = UUID.randomUUID(), name = ""),
            bids = arrayOf(
                Bid(
                    UUID.randomUUID(),
                    11f,
                    1,
                    ZonedDateTime.now().minusDays(5)
                ),
                Bid(
                    UUID.randomUUID(),
                    12f,
                    2,
                    ZonedDateTime.now().minusDays(4)
                ),
                Bid(
                    UUID.randomUUID(),
                    13f,
                    1,
                    ZonedDateTime.now().minusDays(3)
                ),
                Bid(
                    UUID.randomUUID(),
                    14f,
                    2,
                    ZonedDateTime.now().minusDays(2)
                ),
                Bid(
                    UUID.randomUUID(),
                    15f,
                    1,
                    ZonedDateTime.now().minusDays(1)
                ),
                Bid(
                    UUID.randomUUID(),
                    16f,
                    3,
                    ZonedDateTime.now().minusDays(1)
                ),
            ),
            endingDate = LocalDate.now(),
            expired = false,
            auctionType = AuctionType.English
        )
    )
    var selectedAuctionBidders: List<ObservedUser> = listOf(
        ObservedUser(1,"Pippo", true, "bio test 1"),
        ObservedUser(2,"Paperino", bio = "bio test 2"),
        ObservedUser(3,"Pluto", bio = "bio test 3"),

    ) //need to write a function that takes all the users ID available in selectedAuction.bids and then make requests to the server based on those IDs to fill this list



    var auctionSearchResult: Array<Auction> = arrayOf()
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
    var sellerShowComposables by mutableStateOf(false)
    var auctionOpenByOwner by mutableStateOf(false)
}