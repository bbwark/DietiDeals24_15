package com.CioffiDeVivo.dietideals.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.CioffiDeVivo.dietideals.DietiDealsApplication
import com.CioffiDeVivo.dietideals.data.models.Auction
import com.CioffiDeVivo.dietideals.data.mappers.toDataModel
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.data.models.User
import com.CioffiDeVivo.dietideals.data.repositories.AuctionRepository
import com.CioffiDeVivo.dietideals.data.repositories.UserRepository
import com.CioffiDeVivo.dietideals.services.ApiService
import com.google.gson.Gson
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Success(arrayOf(), arrayOf(), arrayOf()))
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()
    private val _userState = MutableStateFlow<User?>(null)
    val userState: StateFlow<User?> = _userState.asStateFlow()

    fun loadUser(){
        viewModelScope.launch {
            try {
                val user = userRepository.getUser(userPreferencesRepository.userId.toString())
                _userState.value = user
            } catch (e: Exception){
                Log.e("Error", "Error: ${e.message}")
            }
        }
    }

    /*fun fetchHomeAuctions(){
        viewModelScope.launch {
            setLoadingState()
            _homeUiState.value = try {
                val sharedPreferences = getApplication<Application>().getSharedPreferences(
                    "AppPreferences",
                    Context.MODE_PRIVATE
                )
                val userId = sharedPreferences.getString("userId", null)
                if (userId != null) {
                    getLoggedUserData(userId)
                    HomeUiState.Success(getLatestAuctions(), getEndingAuctions(), getParticipatedAuctions())
                } else{
                    Log.e("Error", "Error: UserId NULL")
                    HomeUiState.Error
                }
            } catch (e: Exception){
                Log.e("Error", "Error: ${e.message}")
                HomeUiState.Error
            }
        }
    }*/

    private fun getLatestAuctions(): Array<Auction> {
        /*var randomAuctionsData: Array<Auction> = arrayOf()
        viewModelScope.launch {
            val randomAuctionsRequest = ApiService.getRandomAuctions(userId)
            randomAuctionsData = randomAuctionsRequest.map { it.toDataModel() }.toTypedArray()
        }
        return randomAuctionsData*/
        return arrayOf()
    }

    private fun getEndingAuctions(): Array<Auction> {
        var endingAuction: Array<Auction> = arrayOf()
        viewModelScope.launch {
            val firstAuctionResponse = ApiService.getAuction("85217e90-b233-4535-9111-249062f89832")
            val secondAuctionResponse = ApiService.getAuction("ccbdbf25-012c-401a-b776-0b5eb7bef49d")
            if(firstAuctionResponse.status.isSuccess() && secondAuctionResponse.status.isSuccess()){
                val firstAuction = Gson().fromJson(firstAuctionResponse.bodyAsText(), com.CioffiDeVivo.dietideals.data.requestModels.Auction::class.java).toDataModel()
                val secondAuction = Gson().fromJson(secondAuctionResponse.bodyAsText(), com.CioffiDeVivo.dietideals.data.requestModels.Auction::class.java).toDataModel()
                endingAuction += firstAuction
                endingAuction += secondAuction
            }
        }
        return endingAuction
    }


    private fun getParticipatedAuctions(): Array<Auction> {
        return arrayOf()
    }

    private fun setLoadingState(){
        _homeUiState.value = HomeUiState.Loading
    }

    fun fetchTestAuctions() {
        var endingAuction: Array<Auction> = arrayOf()
        viewModelScope.launch {
            setLoadingState()
            _homeUiState.value = try {
                val firstAuctionResponse = ApiService.getAuction("85217e90-b233-4535-9111-249062f89832")
                val secondAuctionResponse = ApiService.getAuction("ccbdbf25-012c-401a-b776-0b5eb7bef49d")
                if(firstAuctionResponse.status.isSuccess() && secondAuctionResponse.status.isSuccess()){
                    val firstAuction = Gson().fromJson(firstAuctionResponse.bodyAsText(), com.CioffiDeVivo.dietideals.data.requestModels.Auction::class.java).toDataModel()
                    val secondAuction = Gson().fromJson(secondAuctionResponse.bodyAsText(), com.CioffiDeVivo.dietideals.data.requestModels.Auction::class.java).toDataModel()
                    endingAuction += firstAuction
                    endingAuction += secondAuction
                    HomeUiState.Success(getLatestAuctions(), endingAuction, getParticipatedAuctions())
                } else{
                    Log.e("Error", "Error: REST UNSUCCESSFUL")
                    HomeUiState.Error
                }
            } catch (e: Exception){
                Log.e("Error", "Error: ${e.message}")
                HomeUiState.Error
            }
        }
    }

    //Rest fo getting the correct Auctions for each category(latest, ending, participated...)
}