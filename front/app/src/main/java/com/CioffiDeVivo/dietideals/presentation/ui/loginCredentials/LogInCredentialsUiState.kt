package com.CioffiDeVivo.dietideals.presentation.ui.loginCredentials

sealed interface LogInCredentialsUiState {
    data class LogInParams(
        val email:  String  = "",
        val emailErrorMsg: String? = null,
        val password: String = "",
        val passwordErrorMsg: String? = null
    ): LogInCredentialsUiState
    object Success: LogInCredentialsUiState
    object Error: LogInCredentialsUiState
    object Loading: LogInCredentialsUiState
}