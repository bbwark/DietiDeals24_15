package com.CioffiDeVivo.dietideals.Events

sealed class LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    object Submit: LoginEvent()
}