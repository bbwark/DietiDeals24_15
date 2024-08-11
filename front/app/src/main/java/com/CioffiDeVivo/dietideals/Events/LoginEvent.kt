package com.CioffiDeVivo.dietideals.Events

open class LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent()
    data class EmailDeleted(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data class Submit(val submitted: Boolean = true) : LoginEvent()
}