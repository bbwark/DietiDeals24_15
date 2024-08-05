package com.CioffiDeVivo.dietideals.domain.use_case

class ValidateCreateAuctionForm{

    fun validateItemName(itemName: String): ValidationResult{
        if (itemName.isBlank()) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "The field cannot be empty"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    fun validateMinAccepted(minAccepted: Float): ValidationResult{
        if (minAccepted == 0.0f) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "The field cannot be empty"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    fun validateMinStep(minStep: Float): ValidationResult{
        if (minStep == 0.0f) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "The field cannot be empty"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    fun validateInterval(interval: String): ValidationResult{
        if (interval.isBlank()) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "The field cannot be empty"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    fun validateDescription(description: String): ValidationResult{
        if(description.isBlank()){
            return ValidationResult(
                positiveResult = false,
                errorMessage = "The field cannot be empty"
            )
        }
        return ValidationResult(positiveResult = true)
    }

}