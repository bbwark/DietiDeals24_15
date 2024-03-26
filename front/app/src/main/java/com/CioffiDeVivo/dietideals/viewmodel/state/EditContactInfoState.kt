package com.CioffiDeVivo.dietideals.viewmodel.state

data class EditContactInfoState(
    val address: String = "",
    val addressErrorMsg: String? = null,
    val zipCode: String = "",
    val zipCodeErrorMsg: String? = null,
    val country: String = "",
    val countryErrorMsg: String? = null,
    val phoneNumber: String = "",
    val phoneNumberErrorMsg: String? = null,
)
