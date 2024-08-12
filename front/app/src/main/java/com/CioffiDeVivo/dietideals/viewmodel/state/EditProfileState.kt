package com.CioffiDeVivo.dietideals.viewmodel.state

import com.CioffiDeVivo.dietideals.domain.DataModels.User

data class EditProfileState(
    val emailErrorMsg: String? = null,
    val nameErrorMsg: String? = null,
    val surnameErrorMsg: String? = null,
    val passwordErrorMsg: String? = null,
    val retypePasswordErrorMsg: String? = null,
    val retypePassword: String = "",
    val user: User = User()
)
