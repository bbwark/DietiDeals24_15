package com.CioffiDeVivo.dietideals.presentation.ui.account

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.CioffiDeVivo.dietideals.domain.models.Auction
import com.CioffiDeVivo.dietideals.domain.models.User
import com.CioffiDeVivo.dietideals.utils.EncryptedPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AccountViewModel(application: Application) : AndroidViewModel(application) {

    private val _userState = MutableStateFlow(User())
    val userState: StateFlow<User> = _userState.asStateFlow()
    private val _auctionState = MutableStateFlow<ArrayList<Auction>>(arrayListOf())
    val auctionState = _auctionState.asStateFlow()

    private val sharedPreferences by lazy {
        application.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    }


    fun logOut(){
        val encryptedSharedPreferences = EncryptedPreferencesManager.getEncryptedPreferences()
        encryptedSharedPreferences.edit().clear().apply()
        sharedPreferences.edit().clear().apply()
    }

}