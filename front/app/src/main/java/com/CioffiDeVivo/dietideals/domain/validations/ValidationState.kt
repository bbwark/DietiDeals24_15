package com.CioffiDeVivo.dietideals.domain.validations

sealed class ValidationState {
    object Loading:ValidationState()
    object Success: ValidationState()
    data class Error(val errorMessage:String):ValidationState()

}