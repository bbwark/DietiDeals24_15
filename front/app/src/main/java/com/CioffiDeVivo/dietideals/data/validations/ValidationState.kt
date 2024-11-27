package com.CioffiDeVivo.dietideals.data.validations

sealed interface ValidationState {
    object Loading: ValidationState
    object Success: ValidationState
    data class Error(val errorMessage:String): ValidationState
}