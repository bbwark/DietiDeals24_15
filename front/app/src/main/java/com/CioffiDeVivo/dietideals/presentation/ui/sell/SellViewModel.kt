package com.CioffiDeVivo.dietideals.presentation.ui.sell

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SellViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private val _sellUiState = MutableStateFlow<SellUiState>(SellUiState.Loading)
    val sellUiState: StateFlow<SellUiState> = _sellUiState.asStateFlow()

    fun fetchAuctions() {
        viewModelScope.launch {
            setLoadingState()
            _sellUiState.value = try {
                val userId = userPreferencesRepository.getUserIdPreference()
                val isSeller = userPreferencesRepository.getIsSellerPreference()
                val user = userRepository.getUser(userId)
                SellUiState.Success(user.ownedAuctions.toCollection(ArrayList()), isSeller)
            } catch(e: Exception){
                SellUiState.Error
            }
        }
    }

    private fun setLoadingState(){
        _sellUiState.value = SellUiState.Loading
    }

}