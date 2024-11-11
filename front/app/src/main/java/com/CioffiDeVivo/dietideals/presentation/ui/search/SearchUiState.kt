package com.CioffiDeVivo.dietideals.presentation.ui.search

import com.CioffiDeVivo.dietideals.domain.models.Auction

sealed interface SearchUiState {
    data class Success(val auctions: ArrayList<Auction>): SearchUiState
    object Error: SearchUiState
    object Loading: SearchUiState
    object Idle: SearchUiState
}