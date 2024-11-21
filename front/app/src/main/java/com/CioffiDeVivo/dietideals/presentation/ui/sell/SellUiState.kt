package com.CioffiDeVivo.dietideals.presentation.ui.sell

import com.CioffiDeVivo.dietideals.data.models.Auction


sealed interface SellUiState {
    data class Success(val auctions: ArrayList<Auction>): SellUiState
    object Error: SellUiState
    object Loading: SellUiState

}