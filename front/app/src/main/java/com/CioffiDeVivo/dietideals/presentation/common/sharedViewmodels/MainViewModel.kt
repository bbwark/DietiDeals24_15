package com.CioffiDeVivo.dietideals.presentation.common.sharedViewmodels

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.CioffiDeVivo.dietideals.domain.models.Auction
import com.CioffiDeVivo.dietideals.domain.models.Bid
import com.CioffiDeVivo.dietideals.domain.models.CreditCard
import com.CioffiDeVivo.dietideals.domain.models.Item
import com.CioffiDeVivo.dietideals.domain.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class MainViewModel : ViewModel() {

    private val _userState = MutableStateFlow(User("1", "Simone", "Cioffi","simo@gmail.com"))
    val userState: StateFlow<User> = _userState.asStateFlow()

    private val _creditCardState = MutableStateFlow(CreditCard())
    val creditCardState: StateFlow<CreditCard> = _creditCardState.asStateFlow()

    private val _auctionState = MutableStateFlow(Auction())
    val auctionState: StateFlow<Auction> = _auctionState.asStateFlow()

    private val _itemState = MutableStateFlow(Item())
    val itemState: StateFlow<Item> = _itemState.asStateFlow()

    private val _bidState = MutableStateFlow(Bid())
    val bidState: StateFlow<Bid> = _bidState.asStateFlow()

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
}
val LocalUserState = compositionLocalOf<MainViewModel> { error("User State Context Not Found!") }