package com.CioffiDeVivo.dietideals.viewmodel

import androidx.lifecycle.ViewModel
import com.CioffiDeVivo.dietideals.domain.DataModels.Auction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel: ViewModel() {

    private val _userAuctionState = MutableStateFlow<ArrayList<Auction>>(arrayListOf())
    val userAuctionState = _userAuctionState.asStateFlow()
    //Stato della parola nella Search. Soluzioni: Stato delle auctions e della parola nella seach, oppure altra variale di stato String.
    //RESTAPI per la ricerca tramite keyword.

}