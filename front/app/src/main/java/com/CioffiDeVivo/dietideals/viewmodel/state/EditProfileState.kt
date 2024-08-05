package com.CioffiDeVivo.dietideals.viewmodel.state

data class EditProfileState(
    val email: String = "",
    val emailErrorMsg: String? = null,
    val name:  String  = "",
    val nameErrorMsg: String? = null,
    val surname:  String  = "",
    val surnameErrorMsg: String? = null,
    val description:  String  = "",
    val password: String = "",
    val passwordErrorMsg: String? = null,
    val newPassword: String = "",
    val newPasswordErrorMsg: String? = null
)
