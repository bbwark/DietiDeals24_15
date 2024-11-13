package com.CioffiDeVivo.dietideals.presentation.ui.makeBid

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.mappers.toDataModel
import com.CioffiDeVivo.dietideals.domain.models.Auction
import com.CioffiDeVivo.dietideals.domain.models.AuctionType
import com.CioffiDeVivo.dietideals.domain.models.Bid
import com.CioffiDeVivo.dietideals.domain.mappers.toRequestModel
import com.CioffiDeVivo.dietideals.services.ApiService
import com.google.gson.Gson
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MakeABidViewModel(application: Application) : AndroidViewModel(application) {

    private val _auctionState = MutableStateFlow(Auction())
    val auctionState: StateFlow<Auction> = _auctionState.asStateFlow()
    private val _bidState = MutableStateFlow(Bid())
    val bidState: StateFlow<Bid> = _bidState.asStateFlow()

    private val _makeABidUiState = MutableStateFlow<MakeABidUiState>(MakeABidUiState.Loading)
    val makeABidUiState: StateFlow<MakeABidUiState> = _makeABidUiState.asStateFlow()

    private val sharedPreferences by lazy {
        application.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    }

    fun fetchAuction(auctionId: String){
        viewModelScope.launch {
            setLoadingState()
            _makeABidUiState.value = try {
                val auctionResponse = ApiService.getAuction(auctionId)
                if(auctionResponse.status.isSuccess()){
                    val auction = Gson().fromJson(auctionResponse.bodyAsText(), com.CioffiDeVivo.dietideals.domain.requestModels.Auction::class.java).toDataModel()
                    MakeABidUiState.MakeABidParams(auction, Bid())
                } else{
                    MakeABidUiState.Error
                }
            } catch (e: Exception){
                MakeABidUiState.Error
            }
        }
    }

    fun updateBidValue(value: String){
        try {
            val currentState = _makeABidUiState.value
            if(currentState is MakeABidUiState.MakeABidParams){
                if(value.isEmpty()){
                    _makeABidUiState.value = currentState.copy(
                        bid = currentState.bid.copy(
                            value = 0F
                        )
                    )
                } else{
                    _makeABidUiState.value = currentState.copy(
                        bid = currentState.bid.copy(
                            value = value.toFloat()
                        )
                    )
                }
            }
        } catch (e: Exception){
            _makeABidUiState.value = MakeABidUiState.Error
        }
    }

    fun submitBid(auctionId: String){
        val currentState = _makeABidUiState.value
        if(currentState is MakeABidUiState.MakeABidParams){
            setLoadingState()
            viewModelScope.launch {
                _makeABidUiState.value = try {
                    val validator: Boolean = if(currentState.auction.type == AuctionType.English){
                        validateBidEnglish(currentState.bid, currentState.auction)
                    } else{
                        validateBidSilent(currentState.bid, currentState.auction)
                    }
                    if(validator){
                        val userId = sharedPreferences.getString("userId", null)
                        if(userId != null){
                            val updatedBid = currentState.bid.copy(
                                userId = userId,
                                auctionId = auctionId
                            )
                            val bidRequest = updatedBid.toRequestModel()
                            val bidResponse = ApiService.createBid(bidRequest)
                            if(bidResponse.status.isSuccess()){
                                MakeABidUiState.Success
                            } else{
                                MakeABidUiState.Error
                            }
                        } else{
                            MakeABidUiState.Error
                        }
                    } else{
                        MakeABidUiState.Error
                    }
                } catch (e: Exception){
                    MakeABidUiState.Error
                }
            }
        } else{
            _makeABidUiState.value = MakeABidUiState.Error
        }
    }

    private fun validateBidEnglish(bid: Bid, auction: Auction) : Boolean {
        return bid.value > (auction.bids.last().value + auction.minStep.toFloat())
    }

    private fun validateBidSilent(bid: Bid, auction: Auction) : Boolean {
        return bid.value > auction.minAccepted.toFloat()
    }

    private fun setLoadingState(){
        _makeABidUiState.value = MakeABidUiState.Loading
    }
}