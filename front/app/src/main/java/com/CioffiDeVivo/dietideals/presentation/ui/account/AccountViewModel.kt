package com.CioffiDeVivo.dietideals.presentation.ui.account

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.CioffiDeVivo.dietideals.domain.models.Auction
import com.CioffiDeVivo.dietideals.domain.models.User
import com.CioffiDeVivo.dietideals.utils.EncryptedPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AccountViewModel : ViewModel() {

    private val _userState = MutableStateFlow(User())
    val userState: StateFlow<User> = _userState.asStateFlow()
    private val _auctionState = MutableStateFlow<ArrayList<Auction>>(arrayListOf())
    val auctionState = _auctionState.asStateFlow()
    private val encryptedSharedPreferences = EncryptedPreferencesManager.getEncryptedPreferences()

    fun setUser() {
        setEmail()
        setName()
    }

    private fun setEmail(){
        val email = encryptedSharedPreferences.getString("email", null)
        if(email != null){
            _userState.value = _userState.value.copy(
                email = email
            )
        }
    }

    private fun setName(){
        val name = encryptedSharedPreferences.getString("name", null)
        if(name != null){
            _userState.value = _userState.value.copy(
                name = name
            )
        }
    }

    var selectedNavBarItem: MutableState<Int> = mutableStateOf(0)
}