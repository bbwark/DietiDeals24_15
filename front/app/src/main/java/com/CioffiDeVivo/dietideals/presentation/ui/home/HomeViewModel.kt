package com.CioffiDeVivo.dietideals.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.data.repositories.AuctionRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val auctionRepository: AuctionRepository
): ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Success(arrayOf(), arrayOf(), arrayOf()))
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    fun fetchHomeAuctions(){
        viewModelScope.launch {
            setLoadingState()
            _homeUiState.value = try {
                val userId = userPreferencesRepository.getUserIdPreference()
                val randomAuctions = async { auctionRepository.getRandomAuctions(userId) }
                val participatedAuctions = async { auctionRepository.getEndingAuctions(userId) }
                val endingAuctions = async { auctionRepository.getParticipatedAuctions(userId) }

                val resultRandomAuctions = randomAuctions.await()
                val resultParticipatedAuctions = participatedAuctions.await()
                val resultEndingAuctions = endingAuctions.await()
                HomeUiState.Success(resultRandomAuctions, resultParticipatedAuctions, resultEndingAuctions)
            } catch (e: Exception){
                Log.e("Error", "Error: ${e.message}")
                HomeUiState.Error
            }
        }
    }

    private fun setLoadingState(){
        _homeUiState.value = HomeUiState.Loading
    }

}