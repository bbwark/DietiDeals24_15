package com.CioffiDeVivo.dietideals

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.CioffiDeVivo.dietideals.DataModels.Auction
import com.CioffiDeVivo.dietideals.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.DataModels.CreditCard
import com.CioffiDeVivo.dietideals.DataModels.Item
import com.CioffiDeVivo.dietideals.DataModels.User
import com.CioffiDeVivo.dietideals.DataModels.UserTest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
class DietiDealsViewModel : ViewModel() {

    var creditCard by mutableStateOf(CreditCard("", "", LocalDate.now()))

    var emailtest by mutableStateOf("")
        private set

    private val _user1 = MutableStateFlow(UserTest())
    val user1: StateFlow<UserTest> = _user1.asStateFlow()

    var user by mutableStateOf(
        User(
            UUID.randomUUID(),
            "Nametest Surnametest",
            "",
            "passwordtest",
            creditCards = arrayOf(
                CreditCard("556666666666", "222", LocalDate.now().plusYears(1)),
                CreditCard("456666666666", "222", LocalDate.now().plusYears(2)),
                CreditCard("356666666666", "222", LocalDate.now().plusYears(2))
            )
        )
    )
        private set

    var selectedNavBarItem: MutableState<Int> = mutableStateOf(0)

    var selectedAuction by mutableStateOf(
        Auction(
            UUID.randomUUID(),
            UUID.randomUUID(),
            Item(id = UUID.randomUUID(), name = ""),
            endingDate = LocalDate.now(),
            expired = false,
            auctionType = AuctionType.English
        )
    )
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
    var auctionOpenByOwner by mutableStateOf(false)

    fun updateField(stringChanged: String){
         _user1.update {currentState ->
             currentState.copy(
                 email = stringChanged
             )
         }
    }

    fun cancelField(){
        user.email = ""
    }
}