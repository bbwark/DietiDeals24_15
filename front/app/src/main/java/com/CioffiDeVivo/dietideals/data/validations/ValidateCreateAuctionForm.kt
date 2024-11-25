package com.CioffiDeVivo.dietideals.data.validations

import com.CioffiDeVivo.dietideals.data.models.AuctionType

val intervalRegex = Regex("^(3[0-9]|[4-8][0-9]|90)[0-5][0-9]$")

class ValidateCreateAuctionForm{

    fun validateItemName(itemName: String): ValidationResult {
        if (itemName.isBlank()) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "The field cannot be empty"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    fun validateMinAccepted(minAccepted: String): ValidationResult {
        if (minAccepted.isBlank()) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "The field cannot be empty"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    fun validateMaxBid(maxBid: String): ValidationResult {
        if (maxBid.isEmpty() || maxBid.toFloat() == 0.0f) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "The field cannot be empty"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    fun validateMinStep(minStep: String): ValidationResult {
        if (minStep.isEmpty() || minStep.toFloat() == 0.0f) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "The field cannot be empty"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    fun validateInterval(interval: String): ValidationResult {
        if (interval.isBlank()) {
            return ValidationResult(
                positiveResult = false,
                errorMessage = "The field cannot be empty"
            )
        }
        if(!intervalRegex.matches(interval)){
            return ValidationResult(
                positiveResult = false,
                errorMessage = "Invalid Interval"
            )
        }
        return ValidationResult(positiveResult = true)
    }

    fun validateAuctionType(auctionType: AuctionType): ValidationResult {
        if(auctionType == AuctionType.None){
            return ValidationResult(
                positiveResult = false,
                errorMessage = "You have to choose an Auction Type!"
            )
        }
        return ValidationResult(positiveResult = true)
    }

}