package com.CioffiDeVivo.dietideals.viewmodel.state

import com.CioffiDeVivo.dietideals.domain.DataModels.CreditCard

data class AddCardState(
    val creditCardNumberErrorMsg: String? = null,
    val expirationDateErrorMsg: String? = null,
    val cvvErrorMsg: String? = null,
    val ibanErrorMsg: String? = null,
    val card: CreditCard = CreditCard()
)
