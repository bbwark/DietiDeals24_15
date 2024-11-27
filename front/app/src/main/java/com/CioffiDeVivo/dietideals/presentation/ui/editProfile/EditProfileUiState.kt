package com.CioffiDeVivo.dietideals.presentation.ui.editProfile

import com.CioffiDeVivo.dietideals.data.models.User

sealed interface EditProfileUiState {
    object Success: EditProfileUiState
    object Error: EditProfileUiState
    object Loading: EditProfileUiState
    data class EditProfileParams(
        val emailErrorMsg: String? = null,
        val nameErrorMsg: String? = null,
        val passwordErrorMsg: String? = null,
        val retypePasswordErrorMsg: String? = null,
        val retypePassword: String = "",
        val user: User = User()
    ): EditProfileUiState
}