package com.CioffiDeVivo.dietideals.presentation.ui.addCard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.data.repositories.CreditCardRepository
import com.CioffiDeVivo.dietideals.data.requestModels.CreditCardRequest
import com.CioffiDeVivo.dietideals.data.validations.ValidateAddCardForm
import com.CioffiDeVivo.dietideals.data.validations.ValidationState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AddCardViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val creditCardRepository: CreditCardRepository,
    private val validateAddCardForm: ValidateAddCardForm = ValidateAddCardForm()
): ViewModel() {

    private val _addCardUiState = MutableStateFlow<AddCardUiState>(AddCardUiState.AddCardParams())
    val addCardUiState: StateFlow<AddCardUiState> = _addCardUiState.asStateFlow()
    private val validationEventChannel = Channel<ValidationState>()
    val validationAddCardEvent = validationEventChannel.receiveAsFlow()

    fun addCardAction(addCardEvents: AddCardEvents){
        try {
            val currentState = _addCardUiState.value
            if(currentState is AddCardUiState.AddCardParams){
                when(addCardEvents){
                    is AddCardEvents.CreditCardNumberChanged -> {
                        _addCardUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(creditCardNumber = addCardEvents.creditCardNumber))
                    }
                    is AddCardEvents.CreditCardNumberDeleted -> {
                        _addCardUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(creditCardNumber = ""))
                    }
                    is AddCardEvents.ExpirationDateChanged -> {
                        _addCardUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(expirationDate = addCardEvents.expirationDate))
                    }
                    is AddCardEvents.ExpirationDateDeleted -> {
                        _addCardUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(expirationDate = ""))
                    }
                    is AddCardEvents.CvvChanged -> {
                        _addCardUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(cvv = addCardEvents.cvv))
                    }
                    is AddCardEvents.CvvDeleted -> {
                        _addCardUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(cvv = ""))
                    }
                    is AddCardEvents.IBANChanged -> {
                        _addCardUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(iban = addCardEvents.iban))
                    }
                    is AddCardEvents.IBANDeleted -> {
                        _addCardUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(iban = ""))
                    }
                    is AddCardEvents.Submit -> {
                        submitAddCard()
                    }
                }
            }
        } catch (e: Exception){
            _addCardUiState.value = AddCardUiState.Error
        }
    }

    private fun submitAddCard(){
        if(validationBock()) {
            val currentState = _addCardUiState.value
            if(currentState is AddCardUiState.AddCardParams){
                _addCardUiState.value = AddCardUiState.Loading
                viewModelScope.launch(Dispatchers.IO) {
                    _addCardUiState.value = try {
                        val userId = userPreferencesRepository.getUserIdPreference()
                        val cardRequest = CreditCardRequest(currentState.creditCard.creditCardNumber, currentState.creditCard.expirationDate, currentState.creditCard.cvv, currentState.creditCard.iban, userId)
                        creditCardRepository.createCreditCard(cardRequest)
                        AddCardUiState.Success
                    } catch (e: Exception){
                        AddCardUiState.Error
                    }
                }
            }
        }
    }

    private fun validationBock() : Boolean {
        val currentState = _addCardUiState.value
        if(currentState is AddCardUiState.AddCardParams){
            try {
                val creditCardNumberValidation = validateAddCardForm.validateCreditCardNumber(currentState.creditCard.creditCardNumber)
                val expirationDateValidation = validateAddCardForm.validateExpirationDate(currentState.creditCard.expirationDate)
                val cvvValidation = validateAddCardForm.validateCvv(currentState.creditCard.cvv)
                val ibanValidation = validateAddCardForm.validateIban(currentState.creditCard.iban)

                val hasError = listOf(
                    creditCardNumberValidation,
                    expirationDateValidation,
                    cvvValidation,
                    ibanValidation
                ).any { !it.positiveResult }

                if (hasError){
                    _addCardUiState.value = currentState.copy(
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
            } catch (e: Exception){
                _addCardUiState.value = AddCardUiState.Error
                return false
            }
        } else{
            return false
        }
    }
}