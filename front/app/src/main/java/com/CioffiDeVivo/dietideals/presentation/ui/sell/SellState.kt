package com.CioffiDeVivo.dietideals.presentation.ui.sell

import com.CioffiDeVivo.dietideals.domain.models.Auction


sealed interface SellState {
    data class Success(val auctions: ArrayList<Auction>): SellState
    object Error: SellState
    object Loading: SellState

}