package com.CioffiDeVivo.dietideals.presentation.ui.loginCredentials

sealed class LoginEvent {
    data class EmailChanged(val email: String): LoginEvent()
    object EmailDeleted: LoginEvent()
    data class PasswordChanged(val password: String): LoginEvent()
    object Submit: LoginEvent()
}