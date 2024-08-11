package com.CioffiDeVivo.dietideals.domain.use_case

import android.util.Patterns
import java.time.LocalDate
import java.time.format.DateTimeFormatter

const val passwordMinimumLength = 8
const val phoneNumberMinimumLength = 7
const val phoneNumberMaximumLength = 15
const val creditCardNumberLength = 16
const val creditCardExpirationDateNumberLength = 5
const val cvvLength = 3
const val ibanLength = 27
val currentYear = java.time.Year.now().value.toString().substring(2)
val regexPattern = """^(0[1-9]|1[0-2])/$currentYear|[2-9]\d$""".toRegex()

open class ValidateRegistrationForms {

    open fun validateEmail(email: String): ValidationResult {
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

    open fun validateName(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "The field cannot be empty"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    open fun validateSurname(surname: String): ValidationResult {
        if (surname.isBlank()) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "Field cannot be empty"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    open fun validatePassword(password: String): ValidationResult {
        if (password.length < passwordMinimumLength) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "Password need at least 8 characters"
            )
        }
        if(password.isBlank()){
            return ValidationResult(
                positiveResult = false,
                errorMessage = "Field cannot be empty"
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

    open fun validateRetypePassword(password: String, newPassword: String): ValidationResult {
        if (password != newPassword ) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "Passwords don't match"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    open fun validateAddress(address: String): ValidationResult{
        if (address.isBlank()) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "The field cannot be empty"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    open fun validateZipCode(zipCode: String): ValidationResult{
        if (zipCode.isBlank()) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "The field cannot be empty"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    open fun validateCountry(country: String): ValidationResult{
        if (country.isBlank()) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "The field cannot be empty"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    open fun validatePhoneNumber(phoneNumber: String): ValidationResult{
        if(phoneNumber.length < phoneNumberMinimumLength || phoneNumber.length > phoneNumberMaximumLength){
            return ValidationResult(
                positiveResult = false,
                errorMessage = "Invalid Phone Number"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    open fun validateCreditCardNumber(creditCardNumber: String): ValidationResult{
        if(creditCardNumber.length != creditCardNumberLength){
            return ValidationResult(
                positiveResult = false,
                errorMessage = "Invalid Credit Card Number"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    open fun validateExpirationDate(expirationDate: String): ValidationResult{
        if (expirationDate.length != creditCardExpirationDateNumberLength && !regexPattern.matches(expirationDate)) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "Invalid Date"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    open fun validateCvv(cvv: String): ValidationResult{
        if(cvv.length != cvvLength){
            return ValidationResult(
                positiveResult = false,
                errorMessage = "Invalid CVV"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    open fun validateIban(iban: String): ValidationResult{
        if(iban.length != ibanLength){
            return ValidationResult(
                positiveResult = false,
                errorMessage = "Invalid IBAN"
            )
        }
        return ValidationResult(positiveResult = true)
    }

}