package com.CioffiDeVivo.dietideals.viewmodel.state

data class CardsState(
    val creditCardNumber: String = "",
    val creditCardNumberErrorMsg: String? = null,
    val expirationDate: String = "",
    val expirationDateErrorMsg: String? = null,
    val cvv: String = "",
    val cvvErrorMsg: String? = null,
    val iban: String = "",
    val ibanErrorMsg: String? = null,
)
