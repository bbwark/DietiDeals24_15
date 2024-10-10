package com.CioffiDeVivo.dietideals.presentation.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.models.Auction
import com.CioffiDeVivo.dietideals.domain.mappers.toDataModel
import com.CioffiDeVivo.dietideals.utils.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    fun fetchHomeAuctions(){
        viewModelScope.launch {
            setLoadingState()
            _homeUiState.value = try {
                val sharedPreferences = getApplication<Application>().getSharedPreferences(
                    "AppPreferences",
                    Context.MODE_PRIVATE
                )
                val userId = sharedPreferences.getString("userId", null)
                if (userId != null) {
                    HomeUiState.Success(getLatestAuctions(userId), getEndingAuctions(), getParticipatedAuctions())
                } else{
                    HomeUiState.Error
                }
            } catch (e: Exception){
                HomeUiState.Error
            }
        }
    }

    private fun getLatestAuctions(userId: String): Array<Auction> {
        var randomAuctionsData: Array<Auction> = arrayOf()
        viewModelScope.launch {
            val randomAuctionsRequest = ApiService.getRandomAuctions(userId)
            randomAuctionsData = randomAuctionsRequest.map { it.toDataModel() }.toTypedArray()
        }
        return randomAuctionsData
    }

    private fun getEndingAuctions(): Array<Auction> {
        return arrayOf()
    }

    private fun getParticipatedAuctions(): Array<Auction> {
        return arrayOf()
    }

    private fun setLoadingState(){
        _homeUiState.value = HomeUiState.Loading
    }

    //Rest fo getting the correct Auctions for each category(latest, ending, participated...)
}