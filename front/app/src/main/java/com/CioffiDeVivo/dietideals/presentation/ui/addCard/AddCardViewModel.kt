package com.CioffiDeVivo.dietideals.presentation.ui.addCard

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.mappers.toRequestModel
import com.CioffiDeVivo.dietideals.domain.validations.ValidateAddCardForm
import com.CioffiDeVivo.dietideals.domain.validations.ValidationState
import com.CioffiDeVivo.dietideals.services.ApiService
import io.ktor.http.isSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AddCardViewModel(application: Application, private val validateAddCardForm: ValidateAddCardForm = ValidateAddCardForm() ): AndroidViewModel(application) {

    private val _addCardUiState = MutableStateFlow<AddCardUiState>(AddCardUiState.AddCardParams())
    val addCardUiState: StateFlow<AddCardUiState> = _addCardUiState.asStateFlow()
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
            val currentState = _addCardUiState.value
            if(currentState is AddCardUiState.AddCardParams){
                val sharedPreferences = getApplication<Application>().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
                val userId = sharedPreferences.getString("userId", null)
                if(userId != null) {
                    val cardRequest = currentState.creditCard.toRequestModel()
                    cardRequest.ownerId = userId
                    viewModelScope.launch {
                        setLoadingState()
                        _addCardUiState.value = try {
                            val cardResponse = ApiService.createCreditCard(cardRequest)
                            if(cardResponse.status.isSuccess()){
                                AddCardUiState.Success
                            } else{
                                AddCardUiState.Error
                            }
                        } catch (e: Exception){
                            AddCardUiState.Error
                        }
                    }
                }
            }
        }
    }

    private fun setLoadingState(){
        _addCardUiState.value = AddCardUiState.Loading
    }

    private fun validationBock() : Boolean {
        val currentState = _addCardUiState.value
        if(currentState is AddCardUiState.AddCardParams){
            try {
                val creditCardNumberValidation = validateAddCardForm.validateCreditCardNumber(currentState.creditCard.creditCardNumber)
                val expirationDateValidation = validateAddCardForm.validateExpirationDate(currentState.expirationDate)
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

    private fun updateCreditCardNumber(creditCardNumber: String){
        try {
            val currentState = _addCardUiState.value
            if(currentState is AddCardUiState.AddCardParams){
                _addCardUiState.value = currentState.copy(
                    creditCard = currentState.creditCard.copy(
                        creditCardNumber = creditCardNumber
                    )
                )
            }
        } catch (e: Exception){
            _addCardUiState.value = AddCardUiState.Error
        }
    }

    private fun deleteCreditCardNumber(){
       updateCreditCardNumber("")
    }

    private fun updateExpirationDate(expirationDate: String){
        try {
            val currentState = _addCardUiState.value
            if(currentState is AddCardUiState.AddCardParams){
                _addCardUiState.value = currentState.copy(
                    expirationDate = expirationDate
                )
            }
        } catch (e: Exception){
            _addCardUiState.value = AddCardUiState.Error
        }
    }

    private fun deleteExpirationDate(){
        updateExpirationDate("")
    }

    private fun updateCvv(cvv: String){
        try {
            val currentState = _addCardUiState.value
            if(currentState is AddCardUiState.AddCardParams){
                _addCardUiState.value = currentState.copy(
                    creditCard = currentState.creditCard.copy(
                        cvv = cvv
                    )
                )
            }
        } catch (e: Exception){
            _addCardUiState.value = AddCardUiState.Error
        }
    }

    private fun deleteCvv(){
        updateCvv("")
    }

    private fun updateIban(iban: String){
        try {
            val currentState = _addCardUiState.value
            if(currentState is AddCardUiState.AddCardParams){
                _addCardUiState.value = currentState.copy(
                    creditCard = currentState.creditCard.copy(
                        iban = iban
                    )
                )
            }
        } catch (e: Exception){
            _addCardUiState.value = AddCardUiState.Error
        }
    }

    private fun deleteIban(){
        updateIban("")
    }
}