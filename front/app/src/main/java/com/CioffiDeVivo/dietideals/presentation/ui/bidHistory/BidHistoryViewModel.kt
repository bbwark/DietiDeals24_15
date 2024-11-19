package com.CioffiDeVivo.dietideals.presentation.ui.bidHistory

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.models.Auction
import com.CioffiDeVivo.dietideals.domain.models.Bid
import com.CioffiDeVivo.dietideals.domain.models.User
import com.CioffiDeVivo.dietideals.domain.mappers.toDataModel
import com.CioffiDeVivo.dietideals.domain.mappers.toRequestModel
import com.CioffiDeVivo.dietideals.services.ApiService
import com.google.gson.Gson
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BidHistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val _bidHistoryUiState = MutableStateFlow<BidHistoryUiState>(BidHistoryUiState.Loading)
    val bidHistoryUiState: StateFlow<BidHistoryUiState> = _bidHistoryUiState.asStateFlow()

    private val sharedPreferences by lazy {
        application.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    }

    fun fetchAuctionBidders(auctionId: String) {
        viewModelScope.launch {
            setLoadingState()
            _bidHistoryUiState.value = try {
                val auctionResponse = ApiService.getAuction(auctionId)
                if (auctionResponse.status.isSuccess()) {
                    val auction = Gson().fromJson(auctionResponse.bodyAsText(), com.CioffiDeVivo.dietideals.domain.requestModels.Auction::class.java).toDataModel()
                    val bidders = getBiddersFromServer(auctionId)
                    BidHistoryUiState.Success(auction, bidders)
                } else{
                    BidHistoryUiState.Error
                }
            } catch (e: Exception){
                BidHistoryUiState.Error
            }
        }
    }

    private suspend fun getBiddersFromServer(auctionId: String): MutableList<User> {
        val result = emptyList<User>().toMutableList()
        val requestUsers = ApiService.getAuctionBidders(auctionId)
        for (user in requestUsers) {
            result += user.toDataModel()
        }
        return result
    }

    fun chooseWinningBid(bid: Bid) {
        viewModelScope.launch {
            setLoadingState()
            _bidHistoryUiState.value = try {
                val chooseWinningBidResponse = ApiService.chooseWinningBid(bid.toRequestModel())
                if (chooseWinningBidResponse.status.isSuccess()) {
                    //questa response non ritorna nulla, manda solo le notifiche a chi ha vinto e chi ha perso
                    BidHistoryUiState.Success()
                } else {
                    BidHistoryUiState.Error
                }
            } catch (e: Exception) {
                BidHistoryUiState.Error
            }
        }
    }

    private fun setLoadingState(){
        _bidHistoryUiState.value = BidHistoryUiState.Loading
    }

}