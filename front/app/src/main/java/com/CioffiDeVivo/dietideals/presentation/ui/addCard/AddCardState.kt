package com.CioffiDeVivo.dietideals.presentation.ui.addCard

import com.CioffiDeVivo.dietideals.domain.models.CreditCard

data class AddCardState(
    val creditCardNumberErrorMsg: String? = null,
    val expirationDate: String = "",
    val expirationDateErrorMsg: String? = null,
    val cvvErrorMsg: String? = null,
    val ibanErrorMsg: String? = null,
    val card: CreditCard = CreditCard()
)
