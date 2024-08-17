package com.CioffiDeVivo.dietideals.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.DataModels.Auction
import com.CioffiDeVivo.dietideals.domain.Mappers.toDataModel
import com.CioffiDeVivo.dietideals.utils.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(application: Application): AndroidViewModel(application) {

    private val _searchedAuctionState = MutableStateFlow<ArrayList<Auction>>(arrayListOf())
    val searchedAuctionState : StateFlow<ArrayList<Auction>> = _searchedAuctionState.asStateFlow()

    private val _categoriesToHide = MutableStateFlow<Set<String>>(mutableSetOf())
    val categoriesToHide: StateFlow<Set<String>> = _categoriesToHide.asStateFlow()

    fun setCategoriesToHide(categoriesToHide: Set<String>) {
        _categoriesToHide.value = categoriesToHide
    }

    fun searchWordUpdate(searchWord: String) {
        viewModelScope.launch {
            val auctions = ApiService.getAuctionsByItemName("can")
            val list: ArrayList<Auction> = arrayListOf()
            for (auction in auctions) {
                list.add(auction.toDataModel())
            }
            _searchedAuctionState.value = list
        }
    }
}