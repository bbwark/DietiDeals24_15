package com.CioffiDeVivo.dietideals.presentation.ui.favourites

import com.CioffiDeVivo.dietideals.domain.models.Auction

sealed interface FavouritesUiState {
    data class Success(val auctions: ArrayList<Auction>): FavouritesUiState
    object Error: FavouritesUiState
    object Loading: FavouritesUiState
}