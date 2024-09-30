package com.CioffiDeVivo.dietideals.presentation.ui.editContactInfo

import com.CioffiDeVivo.dietideals.domain.models.User

data class EditContactInfoState(
    val addressErrorMsg: String? = null,
    val zipCodeErrorMsg: String? = null,
    val phoneNumberErrorMsg: String? = null,
    val user: User = User()
)
