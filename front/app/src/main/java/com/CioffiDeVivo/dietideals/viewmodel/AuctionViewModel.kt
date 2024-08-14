package com.CioffiDeVivo.dietideals.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.DataModels.Auction
import com.CioffiDeVivo.dietideals.domain.DataModels.User
import com.CioffiDeVivo.dietideals.domain.Mappers.toDataModel
import com.CioffiDeVivo.dietideals.utils.ApiService
import com.google.gson.Gson
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuctionViewModel(application: Application) : AndroidViewModel(application){
    private val _auctionState = MutableStateFlow(Auction())
    val auctionState: StateFlow<Auction> = _auctionState.asStateFlow()
    private val _isOwnerState = MutableStateFlow(false)
    val isOwnerState: StateFlow<Boolean> = _isOwnerState.asStateFlow()
    private val _insertionistState = MutableStateFlow(User())
    val insertionsState = _insertionistState.asStateFlow()

    fun setAuction(auction: Auction) {
        _auctionState.value = auction
    }

    fun fetchIsOwnerState() {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)
        if(userId != null) {
            _isOwnerState.value = _auctionState.value.ownerId == userId
        }
    }

    fun fetchAuctionState() {
        viewModelScope.launch {
            val getAuctionResponse = ApiService.getAuction(_auctionState.value.id)
            if(getAuctionResponse.status.isSuccess()) {
                val auctionResponse = Gson().fromJson(getAuctionResponse.bodyAsText(), com.CioffiDeVivo.dietideals.domain.RequestModels.Auction::class.java)
                setAuction(auctionResponse.toDataModel())
            }
        }
    }

    fun fetchInsertionist() {
        viewModelScope.launch {
            val getUserInfoResponse = ApiService.getUserInfo(_auctionState.value.ownerId)
            if (getUserInfoResponse.status.isSuccess()) {
                _insertionistState.value = Gson().fromJson(getUserInfoResponse.bodyAsText(), com.CioffiDeVivo.dietideals.domain.RequestModels.User::class.java).toDataModel()
            }
        }
    }
}