package com.CioffiDeVivo.dietideals.domain.use_case

sealed class ValidationState {
    object Loading:ValidationState()
    object Success: ValidationState()
    data class Error(val errorMessage:String):ValidationState()

}