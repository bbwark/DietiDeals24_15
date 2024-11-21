package com.CioffiDeVivo.dietideals.presentation.ui.auction

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.models.Auction
import com.CioffiDeVivo.dietideals.data.models.AuctionType
import com.CioffiDeVivo.dietideals.data.models.Bid
import com.CioffiDeVivo.dietideals.data.models.CreditCard
import com.CioffiDeVivo.dietideals.data.models.Item
import com.CioffiDeVivo.dietideals.data.models.User
import com.CioffiDeVivo.dietideals.data.mappers.toDataModel
import com.CioffiDeVivo.dietideals.services.ApiService
import com.google.gson.Gson
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime

class AuctionViewModel(application: Application) : AndroidViewModel(application){

    private val _auctionUiState = MutableStateFlow<AuctionUiState>(AuctionUiState.Loading)
    val auctionUiState: StateFlow<AuctionUiState> = _auctionUiState.asStateFlow()

    private val sharedPreferences by lazy {
        application.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    }

    fun fetchAuctionUiState(auctionId: String){
        viewModelScope.launch {
            setLoadingState()
            _auctionUiState.value = try{
                val auctionResponse = ApiService.getAuction(auctionId)
                if(auctionResponse.status.isSuccess()){
                    val auction = Gson().fromJson(auctionResponse.bodyAsText(), com.CioffiDeVivo.dietideals.data.requestModels.Auction::class.java).toDataModel()
                    val ownerResponse = ApiService.getUserInfo(auction.ownerId)
                    if(ownerResponse.status.isSuccess()){
                        val owner = Gson().fromJson(ownerResponse.bodyAsText(), com.CioffiDeVivo.dietideals.data.requestModels.User::class.java).toDataModel()
                        val userId = sharedPreferences.getString("userId", null)
                        if(owner.id == userId){
                            AuctionUiState.Success(auction, owner, true)
                        } else{
                            AuctionUiState.Success(auction, owner, false)
                        }
                    } else{
                        AuctionUiState.Error
                    }
                } else{
                    AuctionUiState.Error
                }
            } catch (e: Exception){
                AuctionUiState.Error
            }
        }
    }

    private fun setLoadingState(){
        _auctionUiState.value = AuctionUiState.Loading
    }

    fun addOnFavourites(){

    }

    fun removeFromFavourites(){

    }
}