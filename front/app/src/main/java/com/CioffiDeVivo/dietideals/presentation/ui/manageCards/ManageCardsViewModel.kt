package com.CioffiDeVivo.dietideals.presentation.ui.manageCards

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.models.User
import com.CioffiDeVivo.dietideals.services.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ManageCardsViewModel(application: Application) : AndroidViewModel(application){

    private val _userState = MutableStateFlow(User())
    val userState: StateFlow<User> = _userState.asStateFlow()

    private val _manageCardsUiState = MutableStateFlow<ManageCardsUiState>(ManageCardsUiState.Loading)
    val manageCardsUiState: StateFlow<ManageCardsUiState> = _manageCardsUiState.asStateFlow()

    fun deleteCard(creditCardNumber: String) {
        viewModelScope.launch {
            _manageCardsUiState.value = try {
                ApiService.deleteCreditCard(creditCardNumber)
                _userState.value = _userState.value.copy(
                    creditCards = _userState.value.creditCards.filterNot { it.creditCardNumber == creditCardNumber }.toTypedArray()
                )
                ManageCardsUiState.Success
            } catch (e: Exception){
                ManageCardsUiState.Error
            }
        }
    }
}