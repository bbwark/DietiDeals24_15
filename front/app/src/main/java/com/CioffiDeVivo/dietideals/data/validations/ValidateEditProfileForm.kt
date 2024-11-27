package com.CioffiDeVivo.dietideals.data.validations

class ValidateEditProfileForm: ValidateUserForms() {
    override fun validatePassword(password: String): ValidationResult {
        if(password.isNotBlank()){
            if (password.length < passwordMinimumLength) {
                return ValidationResult(
                    positiveResult = false,
                    errorMessage = "Password need at least 8 characters"
                )
            }
            val passwordFormat = password.any { it.isDigit() } && password.any { it.isLetter() }
            if (!passwordFormat) {
                return ValidationResult(
                    positiveResult = false,
                    errorMessage = "Password must contain digits and letters"
                )
            }
        }
        return ValidationResult(positiveResult = true)
    }
}