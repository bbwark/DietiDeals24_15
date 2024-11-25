package com.CioffiDeVivo.dietideals.data.validations

sealed class ValidationState {
    object Loading: ValidationState()
    object Success: ValidationState()
    data class Error(val errorMessage:String): ValidationState()
}