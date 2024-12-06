package com.CioffiDeVivo.dietideals.presentation.ui.register


interface RegisterUiState {
    object Success: RegisterUiState
    object SuccessWithGoogle: RegisterUiState
    object Error: RegisterUiState
    object Loading: RegisterUiState
}