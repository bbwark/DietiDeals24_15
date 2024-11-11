package com.CioffiDeVivo.dietideals.presentation.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.models.Auction
import com.CioffiDeVivo.dietideals.domain.mappers.toDataModel
import com.CioffiDeVivo.dietideals.services.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(application: Application): AndroidViewModel(application) {

    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val searchUiState: StateFlow<SearchUiState> = _searchUiState.asStateFlow()

    private val _categoriesToHide = MutableStateFlow<Set<String>>(mutableSetOf())
    val categoriesToHide: StateFlow<Set<String>> = _categoriesToHide.asStateFlow()

    fun setCategoriesToHide(categoriesToHide: Set<String>) {
        _categoriesToHide.value = categoriesToHide
    }

    fun searchWordUpdate(searchWord: String) {
        if(searchWord.isBlank()){
            return
        }
        viewModelScope.launch {
            setLoadingState()
            _searchUiState.value = try {
                val auctions = ApiService.getAuctionsByItemName(searchWord)
                if(auctions.isNotEmpty()){
                    val list: ArrayList<Auction> = arrayListOf()
                    for (auction in auctions) {
                        list.add(auction.toDataModel())
                    }
                    SearchUiState.Success(list)
                } else{
                    SearchUiState.Error
                }
            } catch (e: Exception){
                SearchUiState.Error
            }
        }
    }

    private fun setLoadingState(){
        _searchUiState.value = SearchUiState.Loading
    }
}