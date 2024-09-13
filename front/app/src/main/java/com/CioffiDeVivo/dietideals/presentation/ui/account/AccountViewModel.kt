package com.CioffiDeVivo.dietideals.presentation.ui.account

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.CioffiDeVivo.dietideals.domain.models.Auction
import com.CioffiDeVivo.dietideals.domain.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AccountViewModel : ViewModel() {

    private val _userState = MutableStateFlow(User())
    val userState: StateFlow<User> = _userState.asStateFlow()
    private val _auctionState = MutableStateFlow<ArrayList<Auction>>(arrayListOf())
    val auctionState = _auctionState.asStateFlow()

    fun setUser(user: User) {
        _userState.value = user
    }

    var selectedNavBarItem: MutableState<Int> = mutableStateOf(0)
}