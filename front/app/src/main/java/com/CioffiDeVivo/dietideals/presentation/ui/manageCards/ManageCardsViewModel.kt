package com.CioffiDeVivo.dietideals.presentation.ui.manageCards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.data.repositories.CreditCardRepository
import com.CioffiDeVivo.dietideals.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ManageCardsViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userRepository: UserRepository,
    private val creditCardRepository: CreditCardRepository
): ViewModel(){

    private val _manageCardsUiState = MutableStateFlow<ManageCardsUiState>(ManageCardsUiState.Loading)
    val manageCardsUiState: StateFlow<ManageCardsUiState> = _manageCardsUiState.asStateFlow()

    fun fetchCreditCards(){
        viewModelScope.launch {
            setLoadingState()
            _manageCardsUiState.value = try {
                val userId = userPreferencesRepository.getUserIdPreference()
                val user = userRepository.getUser(userId)
                ManageCardsUiState.Success(user.creditCards)
            } catch (e: Exception){
                ManageCardsUiState.Error
            }
        }
    }

    fun deleteCard(creditCardNumber: String) {
        viewModelScope.launch {
            setLoadingState()
            try {
                creditCardRepository.deleteCreditCard(creditCardNumber)
                fetchCreditCards()
            } catch (e: Exception){
                _manageCardsUiState.value = ManageCardsUiState.Error
            }

        }
    }

    private fun setLoadingState(){
        _manageCardsUiState.value = ManageCardsUiState.Loading
    }
}