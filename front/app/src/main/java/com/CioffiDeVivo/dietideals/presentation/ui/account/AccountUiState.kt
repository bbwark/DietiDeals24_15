package com.CioffiDeVivo.dietideals.presentation.ui.account

interface AccountUiState {
    data class Success(val name: String, val email: String, val isSeller: Boolean): AccountUiState
    object SuccessLogout: AccountUiState
    object Error: AccountUiState
    object Loading: AccountUiState
}