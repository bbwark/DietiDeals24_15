package com.CioffiDeVivo.dietideals.domain.validations

data class ValidationResult (

    val positiveResult: Boolean,
    val errorMessage: String? = null
)

