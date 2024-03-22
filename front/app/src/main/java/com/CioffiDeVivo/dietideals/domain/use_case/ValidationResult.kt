package com.CioffiDeVivo.dietideals.domain.use_case

data class ValidationResult (

    val positiveResult: Boolean,
    val errorMessage: String? = null
)

