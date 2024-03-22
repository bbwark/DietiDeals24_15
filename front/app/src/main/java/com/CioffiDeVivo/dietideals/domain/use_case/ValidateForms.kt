package com.CioffiDeVivo.dietideals.domain.use_case

import android.util.Patterns

const val passwordMinimumLength = 8
const val phoneNumberMinimumLength = 7
const val phoneNumberMaximumLength = 15
const val creditCardNumberLength = 16
const val cvvLength = 16
const val ibanLength = 27

class ValidateForms {

    fun validateEmail(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "The field cannot be empty"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "Invalid Email"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    fun validateName(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "The field cannot be empty"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    fun validateSurname(surname: String): ValidationResult {
        if (surname.isBlank()) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "The field cannot be empty"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    fun validatePassword(password: String): ValidationResult {
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
        return ValidationResult(positiveResult = true)

    }

    fun validateNewPassword(password: String, newPassword: String): ValidationResult {
        if (password != newPassword ) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "Passwords don't match"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    fun validateAddress(address: String): ValidationResult{
        if (address.isBlank()) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "The field cannot be empty"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    fun validateZipCode(zipCode: String): ValidationResult{
        if (zipCode.isBlank()) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "The field cannot be empty"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    fun validateCountry(country: String): ValidationResult{
        if (country.isBlank()) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "The field cannot be empty"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    fun validatePhoneNumber(phoneNumber: String): ValidationResult{
        if(phoneNumber.length < phoneNumberMinimumLength || phoneNumber.length > phoneNumberMaximumLength){
            return ValidationResult(
                positiveResult = false,
                errorMessage = "Invalid Phone Number"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    fun validateCreditCardNumber(creditCardNumber: String): ValidationResult{
        if(creditCardNumber.length != creditCardNumberLength){
            return ValidationResult(
                positiveResult = false,
                errorMessage = "Invalid Credit Card Number"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    fun validateExpirationDate(expirationDate: String): ValidationResult{
        return  ValidationResult(positiveResult = true)
    }

    fun validateCvv(cvv: String): ValidationResult{
        if(cvv.length != cvvLength){
            return ValidationResult(
                positiveResult = false,
                errorMessage = "Invalid Cvv"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    fun validateIban(iban: String): ValidationResult{
        if(iban.length != ibanLength){
            return ValidationResult(
                positiveResult = false,
                errorMessage = "Invalid IBAN"
            )
        }
        return ValidationResult(positiveResult = true)
    }

}