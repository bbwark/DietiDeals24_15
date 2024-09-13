package com.CioffiDeVivo.dietideals.presentation.ui.makeBid

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.models.Auction
import com.CioffiDeVivo.dietideals.domain.models.AuctionType
import com.CioffiDeVivo.dietideals.domain.models.Bid
import com.CioffiDeVivo.dietideals.domain.mappers.toRequestModel
import com.CioffiDeVivo.dietideals.utils.ApiService
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

    fun updateBidValue(value: String){
        if(value.isEmpty()){
            _bidState.value = _bidState.value.copy(
                value = 0F
            )
        } else{
            _bidState.value = _bidState.value.copy(
                value = value.toFloat()
            )
        }
    }

    fun deleteBidValue(){
        _bidState.value = _bidState.value.copy(
            value = 0.0f
        )
    }

    fun setAuction(auction: Auction){
        _auctionState.value = auction
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
}