package com.CioffiDeVivo.dietideals.presentation.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.models.Auction
import com.CioffiDeVivo.dietideals.data.repositories.AuctionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val auctionRepository: AuctionRepository
): ViewModel() {

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
                val auctions = auctionRepository.getAuctionsByItemName(searchWord)
                if(auctions.isNotEmpty()){
                    val list: ArrayList<Auction> = arrayListOf()
                    for (auction in auctions) {
                        list.add(auction)
                    }
                    SearchUiState.Success(list)
                } else{
                    Log.e("Error", "Error: REST Unsuccessful")
                    SearchUiState.Error
                }
            } catch (e: Exception){
                Log.e("Error", "Error: ${e.message}")
                SearchUiState.Error
            }
        }
    }

    private fun setLoadingState(){
        _searchUiState.value = SearchUiState.Loading
    }
}