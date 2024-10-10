package com.CioffiDeVivo.dietideals.presentation.ui.manageCards

sealed interface ManageCardsUiState {
    object Success: ManageCardsUiState
    object Error: ManageCardsUiState
    object Loading: ManageCardsUiState
}