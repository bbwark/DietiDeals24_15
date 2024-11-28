package com.CioffiDeVivo.dietideals.presentation.ui.bidHistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.models.Bid
import com.CioffiDeVivo.dietideals.data.repositories.AuctionRepository
import com.CioffiDeVivo.dietideals.data.repositories.BidRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BidHistoryViewModel(
    private val auctionRepository: AuctionRepository,
    private val bidRepository: BidRepository
): ViewModel() {

    private val _bidHistoryUiState = MutableStateFlow<BidHistoryUiState>(BidHistoryUiState.Loading)
    val bidHistoryUiState: StateFlow<BidHistoryUiState> = _bidHistoryUiState.asStateFlow()

    fun fetchAuctionBidders(auctionId: String) {
        _bidHistoryUiState.value = BidHistoryUiState.Loading
        viewModelScope.launch {
            _bidHistoryUiState.value = try {
                val auction = async { auctionRepository.getAuction(auctionId) }
                val bidders = async { auctionRepository.getAuctionBidders(auctionId) }
                val resultAuction = auction.await()
                val resultBidders = bidders.await()
                BidHistoryUiState.Success(resultAuction, resultBidders)
            } catch (e: Exception){
                BidHistoryUiState.Error
            }
        }
    }

    fun chooseWinningBid(bid: Bid) {
        _bidHistoryUiState.value = BidHistoryUiState.Loading
        viewModelScope.launch {
            _bidHistoryUiState.value = try {
                bidRepository.chooseWinningBid(bid)
                BidHistoryUiState.SuccessOnWinningBid
            } catch (e: Exception) {
                BidHistoryUiState.Error
            }
        }
    }

}