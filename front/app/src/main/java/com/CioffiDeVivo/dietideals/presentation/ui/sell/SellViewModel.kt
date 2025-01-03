package com.CioffiDeVivo.dietideals.presentation.ui.sell

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.data.repositories.AuctionRepository
import com.CioffiDeVivo.dietideals.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SellViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userRepository: UserRepository,
    private val auctionRepository: AuctionRepository
): ViewModel() {

    private val _sellUiState = MutableStateFlow<SellUiState>(SellUiState.Loading)
    val sellUiState: StateFlow<SellUiState> = _sellUiState.asStateFlow()

    fun fetchAuctions() {
        _sellUiState.value = SellUiState.Loading
        viewModelScope.launch {
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

    fun deleteAuction(auctionId: String){
        _sellUiState.value = SellUiState.Loading
        viewModelScope.launch {
            _sellUiState.value = try {
                auctionRepository.deleteAuction(auctionId)
                val userId = userPreferencesRepository.getUserIdPreference()
                val isSeller = userPreferencesRepository.getIsSellerPreference()
                val user = userRepository.getUser(userId)
                SellUiState.Success(user.ownedAuctions.toCollection(ArrayList()), isSeller)
            } catch (e: Exception){
                SellUiState.Error
            }
        }
    }

}