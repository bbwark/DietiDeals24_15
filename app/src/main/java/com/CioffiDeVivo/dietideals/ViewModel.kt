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
import com.CioffiDeVivo.dietideals.DataModels.CreditCard
import com.CioffiDeVivo.dietideals.DataModels.Item
import com.CioffiDeVivo.dietideals.DataModels.User
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
class DietiDealsViewModel : ViewModel() {
    var user by mutableStateOf(User(UUID.randomUUID(), "usertest","emailtest","passwordtest"))
    var creditCard by mutableStateOf(CreditCard("","",""))
    var selectedNavBarItem: MutableState<Int> = mutableStateOf(0)
    var selectedAuction by mutableStateOf(Auction(UUID.randomUUID(), UUID.randomUUID(), Item(id = UUID.randomUUID(), name = ""), endingDate = LocalDate.now(), auctionType = AuctionType.English))
    var auctionSearchResult: Array<Auction> = arrayOf()
    var sellerShowComposables by mutableStateOf(false)
}


