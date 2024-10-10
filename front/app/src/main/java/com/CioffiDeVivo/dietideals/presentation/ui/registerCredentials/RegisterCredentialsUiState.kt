package com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials

sealed interface RegisterCredentialsUiState {
    object Success: RegisterCredentialsUiState
    object Error: RegisterCredentialsUiState
    object Loading: RegisterCredentialsUiState
}