package com.CioffiDeVivo.dietideals.presentation.ui.makeBid

sealed interface MakeABidUiState {
    object Success: MakeABidUiState
    object Error: MakeABidUiState
    object Loading: MakeABidUiState
}