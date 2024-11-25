package com.CioffiDeVivo.dietideals.presentation.ui.auction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.data.repositories.AuctionRepository
import com.CioffiDeVivo.dietideals.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuctionViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val auctionRepository: AuctionRepository,
    private val userRepository: UserRepository
): ViewModel(){

    private val _auctionUiState = MutableStateFlow<AuctionUiState>(AuctionUiState.Loading)
    val auctionUiState: StateFlow<AuctionUiState> = _auctionUiState.asStateFlow()

    fun fetchAuctionUiState(auctionId: String){
        _auctionUiState.value = AuctionUiState.Loading
        viewModelScope.launch {
            _auctionUiState.value = try{
                val userId = userPreferencesRepository.getUserIdPreference()
                val user = userRepository.getUser(userId)
                val auction = auctionRepository.getAuction(auctionId)
                val owner = userRepository.getUserInfo(auction.ownerId)
                val isFavoured = user.favouriteAuctions.any { it.id == auctionId }
                if(owner.id == userId){
                    AuctionUiState.Success(auction, owner, true, isFavoured)
                } else{
                    AuctionUiState.Success(auction, owner, false, isFavoured)
                }
            } catch (e: Exception){
                AuctionUiState.Error
            }
        }
    }

    fun addOnFavourites(){
        val currentState = _auctionUiState.value
        if(currentState is AuctionUiState.Success){
            viewModelScope.launch {
                try {
                    val userId = userPreferencesRepository.getUserIdPreference()
                    userRepository.addFavourite(userId, currentState.auction)
                    _auctionUiState.value = currentState.copy(isFavoured = true)
                } catch (e: Exception){
                    _auctionUiState.value = AuctionUiState.Error
                }
            }
        }
    }

    fun removeFromFavourites(){
        val currentState = _auctionUiState.value
        if(currentState is AuctionUiState.Success){
            viewModelScope.launch {
                try {
                    val userId = userPreferencesRepository.getUserIdPreference()
                    userRepository.removeFavourite(userId, currentState.auction)
                    _auctionUiState.value = currentState.copy(isFavoured = false)
                } catch (e: Exception){
                    _auctionUiState.value = AuctionUiState.Error
                }
            }
        }
    }
}