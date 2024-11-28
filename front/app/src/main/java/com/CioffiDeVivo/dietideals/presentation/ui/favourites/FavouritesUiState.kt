package com.CioffiDeVivo.dietideals.presentation.ui.favourites

import com.CioffiDeVivo.dietideals.data.models.Auction

sealed interface FavouritesUiState {
    data class Success(val favouritesAuctions: Array<Auction>): FavouritesUiState {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Success

            return favouritesAuctions.contentEquals(other.favouritesAuctions)
        }

        override fun hashCode(): Int {
            return favouritesAuctions.contentHashCode()
        }
    }

    object Error: FavouritesUiState
    object Loading: FavouritesUiState
}