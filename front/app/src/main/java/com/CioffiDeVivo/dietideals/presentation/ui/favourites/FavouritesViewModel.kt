package com.CioffiDeVivo.dietideals.presentation.ui.favourites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.CioffiDeVivo.dietideals.domain.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavouritesViewModel(application: Application) : AndroidViewModel(application){

    private val _userState = MutableStateFlow(User())
    val userState: StateFlow<User> = _userState.asStateFlow()

    fun setUser(user: User) {
        _userState.value = user
    }

}