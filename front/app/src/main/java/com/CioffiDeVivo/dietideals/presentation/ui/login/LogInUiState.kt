package com.CioffiDeVivo.dietideals.presentation.ui.login


interface LogInUiState {
    object Success: LogInUiState
    object SuccessWithGoogle: LogInUiState
    object Error: LogInUiState
    object Loading: LogInUiState
}