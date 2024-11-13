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

    fun deleteBidValue(){
        try {
            val currentState = _makeABidUiState.value
            if(currentState is MakeABidUiState.MakeABidParams){
                _makeABidUiState.value = currentState.copy(
                    bid = currentState.bid.copy(
                        value = 0F
                    )
                )

            }
        } catch (e: Exception){
            _makeABidUiState.value = MakeABidUiState.Error
        }
    }

    fun submitBid() : Boolean {
        var createBidCallSuccessful  = false
        var validationSuccessful = false
        when(_auctionState.value.type){
            AuctionType.English -> {
                validationSuccessful = validateBidEnglish(_bidState.value, _auctionState.value)
            }
            AuctionType.Silent -> {
                validationSuccessful = validateBidSilent(_bidState.value, _auctionState.value)
            }
            else -> {}
        }
        if(validationSuccessful){
            val bid = _bidState.value.toRequestModel()
            val sharedPreferences = getApplication<Application>().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            bid.userId = sharedPreferences.getString("userId", null)
            bid.auctionId = _auctionState.value.id

            if (bid.userId != null) {
                viewModelScope.launch {
                    val createBidResponse = ApiService.createBid(bid)
                    createBidCallSuccessful = createBidResponse.status.isSuccess()
                }
            }
        }
        return createBidCallSuccessful && validationSuccessful
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