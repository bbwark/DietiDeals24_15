package com.CioffiDeVivo.dietideals.viewmodel.state

import com.CioffiDeVivo.dietideals.domain.DataModels.User

data class EditContactInfoState(
    val addressErrorMsg: String? = null,
    val zipCodeErrorMsg: String? = null,
    val countryErrorMsg: String? = null,
    val phoneNumberErrorMsg: String? = null,
    val user: User = User()
)
