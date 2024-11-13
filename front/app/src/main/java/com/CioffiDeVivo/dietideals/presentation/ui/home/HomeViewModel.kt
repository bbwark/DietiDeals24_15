package com.CioffiDeVivo.dietideals.presentation.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.models.Auction
import com.CioffiDeVivo.dietideals.domain.mappers.toDataModel
import com.CioffiDeVivo.dietideals.services.ApiService
import com.CioffiDeVivo.dietideals.utils.EncryptedPreferencesManager
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    private val sharedPreferences by lazy {
        application.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    }


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
                    getLoggedUserData(userId)
                    HomeUiState.Success(getLatestAuctions(), getEndingAuctions(), getParticipatedAuctions())
                } else{
                    HomeUiState.Error
                }
            } catch (e: Exception){
                HomeUiState.Error
            }
        }
    }

    private fun getLoggedUserData(userId: String){
        viewModelScope.launch {
            val userResponse = ApiService.getUser(userId)
            if(userResponse.status.isSuccess()){
                val jsonObject = Gson().fromJson(userResponse.bodyAsText(), JsonObject::class.java)
                val email = jsonObject.get("email").asString
                val name = jsonObject.get("name").asString
                val isSeller = jsonObject.get("isSeller").asBoolean
                val encryptedSharedPreferences =
                    EncryptedPreferencesManager.getEncryptedPreferences()
                encryptedSharedPreferences.edit().apply {
                    putString("email", email)
                    putString("name", name)
                    putBoolean("isSeller", isSeller)
                    apply()
                }
            }
        }
    }

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
                val firstAuction = Gson().fromJson(firstAuctionResponse.bodyAsText(), com.CioffiDeVivo.dietideals.domain.requestModels.Auction::class.java).toDataModel()
                val secondAuction = Gson().fromJson(secondAuctionResponse.bodyAsText(), com.CioffiDeVivo.dietideals.domain.requestModels.Auction::class.java).toDataModel()
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
                    val firstAuction = Gson().fromJson(firstAuctionResponse.bodyAsText(), com.CioffiDeVivo.dietideals.domain.requestModels.Auction::class.java).toDataModel()
                    val secondAuction = Gson().fromJson(secondAuctionResponse.bodyAsText(), com.CioffiDeVivo.dietideals.domain.requestModels.Auction::class.java).toDataModel()
                    endingAuction += firstAuction
                    endingAuction += secondAuction
                    HomeUiState.Success(getLatestAuctions(), endingAuction, getParticipatedAuctions())
                } else{
                    HomeUiState.Error
                }
            } catch (e: Exception){
                HomeUiState.Error
            }
        }
    }

    //Rest fo getting the correct Auctions for each category(latest, ending, participated...)
}