package com.CioffiDeVivo.dietideals.presentation.ui.favourites

import com.CioffiDeVivo.dietideals.data.models.Auction

sealed interface FavouritesUiState {
    data class Success(val favouritesAuctions: Array<Auction>): FavouritesUiState
    object Error: FavouritesUiState
    object Loading: FavouritesUiState
}