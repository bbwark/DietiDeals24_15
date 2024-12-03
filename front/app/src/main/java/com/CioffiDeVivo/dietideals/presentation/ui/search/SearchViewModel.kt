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

    private var currentPage = 0
    var isLoadingMore = false
    var isLastPage = false

    fun setCategoriesToHide(categoriesToHide: Set<String>) {
        _categoriesToHide.value = categoriesToHide
    }

    fun searchWordUpdate(searchWord: String) {
        if(searchWord.isBlank() || isLoadingMore || isLastPage){
            return
        }
        val currentList = (_searchUiState.value as? SearchUiState.Success)?.auctions ?: arrayListOf()
        if(currentPage == 0){
            _searchUiState.value = SearchUiState.Loading
        }
        viewModelScope.launch {
            try {
                val categoryList: List<String> = _categoriesToHide.value.toMutableList()
                val auctions = auctionRepository.getAuctionsByItemName(searchWord, currentPage, categoryList)
                if(auctions.isNotEmpty()){
                    val updatedList = ArrayList(currentList).apply {
                        addAll(auctions)
                    }
                    _searchUiState.value = SearchUiState.Success(updatedList, searchWord)
                    if(auctions.size < 10){
                        isLastPage = true
                    } else{
                        currentPage++
                    }
                } else{
                    isLastPage = true
                    if(currentPage == 0){
                        _searchUiState.value = SearchUiState.Empty
                    }
                }
            } catch (e: Exception){
                Log.e("Error", "Error: ${e.message}")
                _searchUiState.value = SearchUiState.Error
            } finally {
                isLoadingMore = false
            }
        }
    }

    fun resetPagination(){
        currentPage = 0
        isLastPage = false
        isLoadingMore = false
    }
}