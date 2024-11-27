package com.CioffiDeVivo.dietideals.data.validations

class ValidateLogInForm(): ValidateUserForms() {

    //Aggiungere Errors per email o password errate
    override fun validateEmail(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "The field cannot be empty"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    override fun validatePassword(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "The field cannot be empty"
            )
        }
        return ValidationResult(positiveResult = true)
    }

}