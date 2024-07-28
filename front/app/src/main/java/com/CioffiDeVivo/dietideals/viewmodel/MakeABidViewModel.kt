package com.CioffiDeVivo.dietideals.viewmodel

import androidx.lifecycle.ViewModel
import com.CioffiDeVivo.dietideals.domain.DataModels.Auction
import com.CioffiDeVivo.dietideals.domain.DataModels.Bid
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MakeABidViewModel : ViewModel() {

    private val _auctionState = MutableStateFlow(Auction())
    val auctionState: StateFlow<Auction> = _auctionState.asStateFlow()
    private val _bidState = MutableStateFlow(Bid())
    val bidState: StateFlow<Bid> = _bidState.asStateFlow()

    fun updateBidValue(value: String){
        _bidState.value = _bidState.value.copy(
            value = value.toFloat()
        )
    }

    fun deleteBidValue(){
        _bidState.value = _bidState.value.copy(
            value = 0.0f
        )
    }

}