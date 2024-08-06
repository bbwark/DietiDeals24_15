package com.CioffiDeVivo.dietideals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.Events.AddCardEvents
import com.CioffiDeVivo.dietideals.domain.use_case.ValidateAddCardForm
import com.CioffiDeVivo.dietideals.domain.use_case.ValidationState
import com.CioffiDeVivo.dietideals.viewmodel.state.AddCardState
import com.CioffiDeVivo.dietideals.viewmodel.state.RegistrationState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AddCardViewModel(private val validateAddCardForm: ValidateAddCardForm = ValidateAddCardForm() ): ViewModel() {

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
        val creditCardNumberValidation = validateAddCardForm.validateCreditCardNumber(userCardState.value.creditCardNumber)
        val expirationDateValidation = validateAddCardForm.validateExpirationDate(userCardState.value.expirationDate)
        val cvvValidation = validateAddCardForm.validateCvv(userCardState.value.cvv)
        val ibanValidation = validateAddCardForm.validateIban(userCardState.value.iban)

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
            return
        }

        viewModelScope.launch {
            validationEventChannel.send(ValidationState.Success)
        }
    }

    private fun updateCreditCardNumber(creditCardNumber: String){
        _userCardState.value = _userCardState.value.copy(
            creditCardNumber = creditCardNumber
        )
    }

    private fun deleteCreditCardNumber(){
        _userCardState.value = _userCardState.value.copy(
            creditCardNumber = ""
        )
    }

    private fun updateExpirationDate(expirationDate: String){
        _userCardState.value = _userCardState.value.copy(
            expirationDate = expirationDate.toString()
        )
    }

    private fun deleteExpirationDate(){
        _userCardState.value = _userCardState.value.copy(
            expirationDate = ""
        )
    }

    private fun updateCvv(cvv: String){
        _userCardState.value = _userCardState.value.copy(
            cvv = cvv
        )
    }

    private fun deleteCvv(){
        _userCardState.value = _userCardState.value.copy(
            cvv = ""
        )
    }

    private fun updateIban(iban: String){
        _userCardState.value = _userCardState.value.copy(
            iban = iban
        )
    }

    private fun deleteIban(){
        _userCardState.value = _userCardState.value.copy(
            iban = ""
        )
    }
}