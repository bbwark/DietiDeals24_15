package com.CioffiDeVivo.dietideals.viewmodel.state

data class LogInState (
    val email:  String  = "",
    val emailErrorMsg: String? = null,
    val password: String = "",
    val passwordErrorMsg: String? = null
)
