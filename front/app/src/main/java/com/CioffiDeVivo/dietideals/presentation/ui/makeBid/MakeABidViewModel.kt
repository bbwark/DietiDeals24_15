package com.CioffiDeVivo.dietideals.presentation.ui.makeBid

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.data.models.Auction
import com.CioffiDeVivo.dietideals.data.models.AuctionType
import com.CioffiDeVivo.dietideals.data.models.Bid
import com.CioffiDeVivo.dietideals.data.repositories.AuctionRepository
import com.CioffiDeVivo.dietideals.data.repositories.BidRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MakeABidViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val auctionRepository: AuctionRepository,
    private val bidRepository: BidRepository
): ViewModel() {

    private val _makeABidUiState = MutableStateFlow<MakeABidUiState>(MakeABidUiState.Loading)
    val makeABidUiState: StateFlow<MakeABidUiState> = _makeABidUiState.asStateFlow()

    fun fetchAuction(auctionId: String){
        _makeABidUiState.value = MakeABidUiState.Loading
        viewModelScope.launch {
            _makeABidUiState.value = try {
                val auction = auctionRepository.getAuction(auctionId)
                MakeABidUiState.MakeABidParams(auction, MakeABidUiState.MakeABidParams().bid)
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
            _makeABidUiState.value = MakeABidUiState.Loading
            viewModelScope.launch {
                _makeABidUiState.value = try {
                    val validator: Boolean = if(currentState.auction.type == AuctionType.English){
                        validateBidEnglish(currentState.bid, currentState.auction)
                    } else{
                        validateBidSilent(currentState.bid, currentState.auction)
                    }
                    if(validator){
                        val userId = userPreferencesRepository.getUserIdPreference()
                        val updatedBid = currentState.bid.copy(
                            userId = userId,
                            auctionId = auctionId
                        )
                        bidRepository.createBid(updatedBid)
                        MakeABidUiState.Success
                    } else{
                        Log.e("Error", "Error: Validator Error")
                        MakeABidUiState.Error
                    }
                } catch (e: Exception){
                    Log.e("Error", "Error: ${e.message}")
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
        return bid.value > auction.startingPrice.toFloat()
    }
}