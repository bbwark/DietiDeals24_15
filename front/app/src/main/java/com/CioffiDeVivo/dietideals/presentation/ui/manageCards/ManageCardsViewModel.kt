package com.CioffiDeVivo.dietideals.presentation.ui.manageCards

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.data.models.CreditCard
import com.CioffiDeVivo.dietideals.data.repositories.CreditCardRepository
import com.CioffiDeVivo.dietideals.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ManageCardsViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userRepository: UserRepository,
    private val creditCardRepository: CreditCardRepository
): ViewModel(){

    private val _manageCardsUiState = MutableStateFlow<ManageCardsUiState>(ManageCardsUiState.Loading)
    val manageCardsUiState: StateFlow<ManageCardsUiState> = _manageCardsUiState.asStateFlow()
    private var isFetching = false

    fun fetchCreditCards(){
        _manageCardsUiState.value = ManageCardsUiState.Loading
        viewModelScope.launch {
            _manageCardsUiState.value = try {
                val userId = userPreferencesRepository.getUserIdPreference()
                val user = userRepository.getUser(userId)
                withContext(Dispatchers.Main){
                    ManageCardsUiState.Success(user.creditCards)
                }
            } catch (e: Exception){
                ManageCardsUiState.Error
            } finally {
                isFetching = false
            }
        }
    }

    fun deleteCard(creditCardNumber: String) {
        _manageCardsUiState.value = ManageCardsUiState.Loading
        viewModelScope.launch {
            _manageCardsUiState.value = try {
                creditCardRepository.deleteCreditCard(creditCardNumber)
                val userId = userPreferencesRepository.getUserIdPreference()
                val user = userRepository.getUser(userId)
                ManageCardsUiState.Success(user.creditCards)
            } catch (e: Exception){
                ManageCardsUiState.Error
            }

        }
    }

}