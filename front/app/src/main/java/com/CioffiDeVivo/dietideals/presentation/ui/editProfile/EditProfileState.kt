package com.CioffiDeVivo.dietideals.presentation.ui.editProfile

import com.CioffiDeVivo.dietideals.domain.models.User

data class EditProfileState(
    val emailErrorMsg: String? = null,
    val nameErrorMsg: String? = null,
    val surnameErrorMsg: String? = null,
    val passwordErrorMsg: String? = null,
    val retypePasswordErrorMsg: String? = null,
    val retypePassword: String = "",
    val user: User = User()
)
