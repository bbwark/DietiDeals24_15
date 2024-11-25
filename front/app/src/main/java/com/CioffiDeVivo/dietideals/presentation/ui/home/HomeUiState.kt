package com.CioffiDeVivo.dietideals.presentation.ui.home

import com.CioffiDeVivo.dietideals.data.models.Auction

sealed interface HomeUiState {

    data class Success(val latestAuctions: Array<Auction>, val endingAuction: Array<Auction>, val participatedAuction: Array<Auction>): HomeUiState
    object Error: HomeUiState
    object Loading: HomeUiState

}