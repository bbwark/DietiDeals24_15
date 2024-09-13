package com.CioffiDeVivo.dietideals.presentation.ui.loginCredentials

data class LogInState (
    val email:  String  = "",
    val emailErrorMsg: String? = null,
    val password: String = "",
    val passwordErrorMsg: String? = null
)
