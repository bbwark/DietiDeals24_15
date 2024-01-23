package com.CioffiDeVivo.dietideals.DataModels

sealed class LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    object Submit: LoginEvent()
}