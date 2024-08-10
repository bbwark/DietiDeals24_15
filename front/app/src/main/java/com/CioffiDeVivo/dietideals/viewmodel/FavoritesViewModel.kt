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
class FavoritesViewModel : ViewModel(){

    private val _userState = MutableStateFlow(User())
    val userState: StateFlow<User> = _userState.asStateFlow()
    private val _favouredAuctionState = MutableStateFlow<ArrayList<Auction>>(arrayListOf())
    val favouredAuctionState : StateFlow<ArrayList<Auction>> = _favouredAuctionState.asStateFlow()


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

}