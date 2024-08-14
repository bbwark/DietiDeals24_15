package com.CioffiDeVivo.dietideals.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.DataModels.User
import com.CioffiDeVivo.dietideals.utils.ApiService
import com.CioffiDeVivo.dietideals.viewmodel.state.EditProfileState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ManageCardsViewModel(application: Application) : AndroidViewModel(application){

    private val _userCardsState = MutableStateFlow(EditProfileState())
    val userCardsState: StateFlow<EditProfileState> = _userCardsState.asStateFlow()

    fun setUser(user: User) {
        _userCardsState.value = _userCardsState.value.copy(
            user = user
        )
    }

    fun deleteCard(creditCardNumber: String) {
        viewModelScope.launch {
            ApiService.deleteCreditCard(creditCardNumber)
            _userCardsState.value = _userCardsState.value.copy(
                user = _userCardsState.value.user.copy(
                    creditCards = _userCardsState.value.user.creditCards.filterNot { it.creditCardNumber == creditCardNumber }.toTypedArray()
                )
            )
        }
    }
}