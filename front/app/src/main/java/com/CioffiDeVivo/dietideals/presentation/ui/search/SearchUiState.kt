package com.CioffiDeVivo.dietideals.presentation.ui.search

import com.CioffiDeVivo.dietideals.data.models.Auction

sealed interface SearchUiState {
    data class Success(val auctions: ArrayList<Auction>, val searchWord: String): SearchUiState
    object Error: SearchUiState
    object Loading: SearchUiState
    object Idle: SearchUiState
    object Empty: SearchUiState
}