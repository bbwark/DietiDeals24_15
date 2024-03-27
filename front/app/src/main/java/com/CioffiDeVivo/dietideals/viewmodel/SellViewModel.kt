package com.CioffiDeVivo.dietideals.viewmodel

import androidx.lifecycle.ViewModel
import com.CioffiDeVivo.dietideals.domain.DataModels.Auction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SellViewModel: ViewModel() {

    private val _userAuctionState = MutableStateFlow<ArrayList<Auction>>(arrayListOf())
    val userAuctionState = _userAuctionState.asStateFlow()
    //RESTAPI per prendere auction che corrispondono all' owner id dell utente loggato (Forse con SharedViewModel cosi si ha il dato del utente loggato)

}