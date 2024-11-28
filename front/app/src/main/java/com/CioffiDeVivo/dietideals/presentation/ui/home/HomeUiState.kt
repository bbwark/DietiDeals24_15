package com.CioffiDeVivo.dietideals.presentation.ui.home

import com.CioffiDeVivo.dietideals.data.models.Auction

sealed interface HomeUiState {

    data class Success(val latestAuctions: Array<Auction>, val endingAuction: Array<Auction>, val participatedAuction: Array<Auction>): HomeUiState {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Success

            if (!latestAuctions.contentEquals(other.latestAuctions)) return false
            if (!endingAuction.contentEquals(other.endingAuction)) return false
            return participatedAuction.contentEquals(other.participatedAuction)
        }

        override fun hashCode(): Int {
            var result = latestAuctions.contentHashCode()
            result = 31 * result + endingAuction.contentHashCode()
            result = 31 * result + participatedAuction.contentHashCode()
            return result
        }
    }

    object Error: HomeUiState
    object Loading: HomeUiState

}