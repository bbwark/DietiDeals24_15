package com.CioffiDeVivo.dietideals.presentation.ui.addCard

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.mappers.toRequestModel
import com.CioffiDeVivo.dietideals.domain.validations.ValidateAddCardForm
import com.CioffiDeVivo.dietideals.domain.validations.ValidationState
import com.CioffiDeVivo.dietideals.utils.ApiService
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddCardViewModel(application: Application, private val validateAddCardForm: ValidateAddCardForm = ValidateAddCardForm() ): AndroidViewModel(application) {

    private val _userCardState = MutableStateFlow(AddCardState())
    val userCardState: StateFlow<AddCardState> = _userCardState.asStateFlow()
    private val validationEventChannel = Channel<ValidationState>()
    val validationAddCardEvent = validationEventChannel.receiveAsFlow()

    fun addCardAction(addCardEvents: AddCardEvents){
        when(addCardEvents){
            is AddCardEvents.CreditCardNumberChanged -> {
                updateCreditCardNumber(addCardEvents.creditCardNumber)
            }
            is AddCardEvents.CreditCardNumberDeleted -> {
                deleteCreditCardNumber()
            }
            is AddCardEvents.ExpirationDateChanged -> {
                updateExpirationDate(addCardEvents.expirationDate)
            }
            is AddCardEvents.ExpirationDateDeleted -> {
                deleteExpirationDate()
            }
            is AddCardEvents.CvvChanged -> {
                updateCvv(addCardEvents.cvv)
            }
            is AddCardEvents.CvvDeleted -> {
                deleteCvv()
            }
            is AddCardEvents.IBANChanged -> {
                updateIban(addCardEvents.iban)
            }
            is AddCardEvents.IBANDeleted -> {
                deleteIban()
            }
            is AddCardEvents.Submit -> {
                submitAddCard()
            }
        }
    }

    private fun submitAddCard(){
        if(validationBock()) {
            val sharedPreferences = getApplication<Application>().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getString("userId", null)
            if(userId != null) {
                val creditCardRequest = _userCardState.value.card.toRequestModel()
                creditCardRequest.ownerId = userId
                viewModelScope.launch {
                    val createCreditCardResponse = ApiService.createCreditCard(creditCardRequest)
                    //TODO create credit card response handling
                }
            }
        }
    }

    private fun validationBock() : Boolean {
        val creditCardNumberValidation = validateAddCardForm.validateCreditCardNumber(userCardState.value.card.creditCardNumber)
        val expirationDateValidation = validateAddCardForm.validateExpirationDate(userCardState.value.card.expirationDate.toString())
        val cvvValidation = validateAddCardForm.validateCvv(userCardState.value.card.cvv)
        val ibanValidation = validateAddCardForm.validateIban(userCardState.value.card.iban)

        val hasError = listOf(
            creditCardNumberValidation,
            expirationDateValidation,
            cvvValidation,
            ibanValidation
        ).any { !it.positiveResult }

        if (hasError){
            _userCardState.value = _userCardState.value.copy(
                creditCardNumberErrorMsg = creditCardNumberValidation.errorMessage,
                expirationDateErrorMsg = expirationDateValidation.errorMessage,
                cvvErrorMsg = cvvValidation.errorMessage,
                ibanErrorMsg = ibanValidation.errorMessage,
            )
            return false
        }

        viewModelScope.launch {
            validationEventChannel.send(ValidationState.Success)
        }

        return true
    }

    private fun updateCreditCardNumber(creditCardNumber: String){
        _userCardState.value = _userCardState.value.copy(
            card = _userCardState.value.card.copy(
                creditCardNumber = creditCardNumber
            )
        )
    }

    private fun deleteCreditCardNumber(){
       updateCreditCardNumber("")
    }

    private fun updateExpirationDate(expirationDate: String){
        _userCardState.value = _userCardState.value.copy(
            card = _userCardState.value.card.copy(
                expirationDate = LocalDate.parse(expirationDate)
            )
        )
    }

    private fun deleteExpirationDate(){
        updateExpirationDate("")
    }

    private fun updateCvv(cvv: String){
        _userCardState.value = _userCardState.value.copy(
            card = _userCardState.value.card.copy(
                cvv = cvv
            )
        )
    }

    private fun deleteCvv(){
        updateCvv("")
    }

    private fun updateIban(iban: String){
        _userCardState.value = _userCardState.value.copy(
            card = _userCardState.value.card.copy(
                iban = iban
            )
        )
    }

    private fun deleteIban(){
        updateIban("")
    }
}