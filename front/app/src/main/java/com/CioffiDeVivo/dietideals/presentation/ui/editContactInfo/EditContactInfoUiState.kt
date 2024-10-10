package com.CioffiDeVivo.dietideals.presentation.ui.editContactInfo

import com.CioffiDeVivo.dietideals.domain.models.User

sealed interface EditContactInfoUiState {
    object Success: EditContactInfoUiState
    object Error: EditContactInfoUiState
    object Loading: EditContactInfoUiState
    data class EditContactInfoParams(
        val addressErrorMsg: String? = null,
        val zipCodeErrorMsg: String? = null,
        val phoneNumberErrorMsg: String? = null,
        val user: User = User()
    ): EditContactInfoUiState
}