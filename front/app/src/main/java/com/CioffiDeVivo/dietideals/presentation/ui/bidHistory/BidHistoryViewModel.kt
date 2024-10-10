package com.CioffiDeVivo.dietideals.presentation.ui.bidHistory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.models.Auction
import com.CioffiDeVivo.dietideals.domain.models.Bid
import com.CioffiDeVivo.dietideals.domain.models.User
import com.CioffiDeVivo.dietideals.domain.mappers.toDataModel
import com.CioffiDeVivo.dietideals.utils.ApiService
import com.google.gson.Gson
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BidHistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val _auctionState = MutableStateFlow(Auction())
    val auctionState: StateFlow<Auction> = _auctionState.asStateFlow()
    private val _bidState = MutableStateFlow(Bid())
    val bidState: StateFlow<Bid> = _bidState.asStateFlow()

    private val _auctionBidders = MutableStateFlow<List<User>>(emptyList())
    val auctionBidders: StateFlow<List<User>> = _auctionBidders.asStateFlow()

    fun setAuction(auction: Auction){
        _auctionState.value = auction
    }

    fun fetchAuctionBidders(auctionId: String) {
        viewModelScope.launch {
            val getAuctionResponse = ApiService.getAuction(_auctionState.value.id)
            if (getAuctionResponse.status.isSuccess()) {
                _auctionState.value = Gson().fromJson(getAuctionResponse.bodyAsText(), com.CioffiDeVivo.dietideals.domain.requestModels.Auction::class.java).toDataModel()
                val bidders = getBiddersFromServer(_auctionState.value.id)
                _auctionBidders.value = bidders
            }
        }
    }

    private suspend fun getBiddersFromServer(auctionId: String): List<User> {
        val result = emptyList<User>().toMutableList()
        val requestUsers = ApiService.getAuctionBidders(auctionId)
        for (user in requestUsers) {
            result += user.toDataModel()
        }
        return result
    }


}