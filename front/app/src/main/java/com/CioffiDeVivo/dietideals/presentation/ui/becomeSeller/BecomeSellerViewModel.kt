package com.CioffiDeVivo.dietideals.presentation.ui.becomeSeller

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.data.repositories.UserRepository
import com.CioffiDeVivo.dietideals.data.validations.ValidateBecomeSellerForm
import com.CioffiDeVivo.dietideals.data.validations.ValidationState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class BecomeSellerViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userRepository: UserRepository,
    private val validateBecomeSellerForm: ValidateBecomeSellerForm = ValidateBecomeSellerForm()
): ViewModel() {

    private val _becomeSellerUiState = MutableStateFlow<BecomeSellerUiState>(BecomeSellerUiState.BecomeSellerParams())
    val becomeSellerUiState: StateFlow<BecomeSellerUiState> = _becomeSellerUiState.asStateFlow()
    private val validationEventChannel = Channel<ValidationState>()
    val validationBecomeSellerEvents = validationEventChannel.receiveAsFlow()

    fun getUserInfo(){
        _becomeSellerUiState.value = BecomeSellerUiState.Loading
        viewModelScope.launch {
            _becomeSellerUiState.value = try {
                val userId = userPreferencesRepository.getUserIdPreference()
                val user = userRepository.getUser(userId)
                val updatedUser = user.copy(password = "")
                BecomeSellerUiState.BecomeSellerParams(updatedUser)
            } catch (e: Exception){
                Log.e("Error", "Error: ${e.message}")
                BecomeSellerUiState.Error
            }
        }

    }

    fun becomeSellerOnAction(becomeSellerEvents: BecomeSellerEvents){
        try {
            val currentState = _becomeSellerUiState.value
            if(currentState is BecomeSellerUiState.BecomeSellerParams){
                when(becomeSellerEvents){
                    is BecomeSellerEvents.AddressChanged -> {
                        _becomeSellerUiState.value = currentState.copy(user = currentState.user.copy(address = becomeSellerEvents.address))
                    }
                    is BecomeSellerEvents.AddressDeleted -> {
                        _becomeSellerUiState.value = currentState.copy(user = currentState.user.copy(address = ""))
                    }
                    is BecomeSellerEvents.ZipCodeChanged -> {
                        _becomeSellerUiState.value = currentState.copy(user = currentState.user.copy(zipcode = becomeSellerEvents.zipCode))
                    }
                    is BecomeSellerEvents.ZipCodeDeleted -> {
                        _becomeSellerUiState.value = currentState.copy(user = currentState.user.copy(zipcode = ""))
                    }
                    is BecomeSellerEvents.CountryChanged -> {
                        _becomeSellerUiState.value = currentState.copy(user = currentState.user.copy(country = becomeSellerEvents.country))
                    }
                    is BecomeSellerEvents.PhoneNumberChanged -> {
                        _becomeSellerUiState.value = currentState.copy(user = currentState.user.copy(phoneNumber = becomeSellerEvents.phoneNumber))
                    }
                    is BecomeSellerEvents.PhoneNumberDeleted -> {
                        _becomeSellerUiState.value = currentState.copy(user = currentState.user.copy(phoneNumber = ""))
                    }
                    is BecomeSellerEvents.CreditCardNumberChanged -> {
                        _becomeSellerUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(creditCardNumber = becomeSellerEvents.creditCardNumber))
                    }
                    is BecomeSellerEvents.CreditCardNumberDeleted -> {
                        _becomeSellerUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(creditCardNumber = ""))
                    }
                    is BecomeSellerEvents.ExpirationDateChanged -> {
                        _becomeSellerUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(expirationDate = becomeSellerEvents.expirationDate))
                    }
                    is BecomeSellerEvents.ExpirationDateDeleted -> {
                        _becomeSellerUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(expirationDate = ""))
                    }
                    is BecomeSellerEvents.CvvChanged -> {
                        _becomeSellerUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(cvv = becomeSellerEvents.cvv))
                    }
                    is BecomeSellerEvents.CvvDeleted -> {
                        _becomeSellerUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(cvv = ""))
                    }
                    is BecomeSellerEvents.IbanChanged -> {
                        _becomeSellerUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(iban = becomeSellerEvents.iban))
                    }
                    is BecomeSellerEvents.IbanDeleted -> {
                        _becomeSellerUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(iban = ""))
                    }
                    is BecomeSellerEvents.Submit -> {
                        submitForm()
                    }
                }
            }
        } catch (e: Exception){
            Log.e("Error", "Error: ${e.message}")
            _becomeSellerUiState.value = BecomeSellerUiState.Error
        }
    }

    private fun submitForm(){
        if(validationBlock()){
            val currentState = _becomeSellerUiState.value
            if(currentState is BecomeSellerUiState.BecomeSellerParams){
                _becomeSellerUiState.value = BecomeSellerUiState.Loading
                viewModelScope.launch {
                    _becomeSellerUiState.value = try {
                        val userId = userPreferencesRepository.getUserIdPreference()
                        val deviceToken = userPreferencesRepository.getDeviceToken()
                        if(currentState.user.creditCards.isNotEmpty()){
                            val updatedUser = currentState.user.copy(
                                isSeller = true
                            )
                            if(currentState.user.deviceTokens.contains(deviceToken)){
                                val mutableList = updatedUser.deviceTokens.toMutableList()
                                mutableList.remove(deviceToken)
                                val updatedArray = mutableList.toTypedArray()
                                val updatedUserNoToken = updatedUser.copy(
                                    password = "",
                                    deviceTokens = updatedArray
                                )
                                userRepository.updateUser(userId, updatedUserNoToken)
                            }
                            userPreferencesRepository.clearPreferences()
                            userPreferencesRepository.saveEmail(currentState.user.email)
                        } else{
                            val creditCard = currentState.creditCard.copy(
                                ownerId = userId
                            )
                            val updatedUser = currentState.user.copy(
                                isSeller = true,
                                creditCards = currentState.user.creditCards + creditCard
                            )
                            if(currentState.user.deviceTokens.contains(deviceToken)){
                                val mutableList = updatedUser.deviceTokens.toMutableList()
                                mutableList.remove(deviceToken)
                                val updatedArray = mutableList.toTypedArray()
                                val updatedUserNoToken = updatedUser.copy(
                                    password = "",
                                    deviceTokens = updatedArray
                                )
                                userRepository.updateUser(userId, updatedUserNoToken)
                            }
                            userPreferencesRepository.clearPreferences()
                            userPreferencesRepository.saveEmail(currentState.user.email)
                        }
                        BecomeSellerUiState.Success
                    } catch(e: Exception){
                        Log.e("Error", "Error: ${e.message}")
                        BecomeSellerUiState.Error
                    }
                }
            }
        }
    }

    private fun validationBlock(): Boolean{
        val currentState = _becomeSellerUiState.value
        if(currentState is BecomeSellerUiState.BecomeSellerParams){
            try {
                val addressValidation = validateBecomeSellerForm.validateAddress(currentState.user.address)
                val zipCodeValidation = validateBecomeSellerForm.validateZipCode(currentState.user.zipcode)
                val phoneNumberValidation = validateBecomeSellerForm.validatePhoneNumber(currentState.user.phoneNumber)
                val creditCardNumberValidation = validateBecomeSellerForm.validateCreditCardNumber(currentState.creditCard.creditCardNumber)
                val expirationDateValidation = validateBecomeSellerForm.validateExpirationDate(currentState.creditCard.expirationDate)
                val cvvValidation = validateBecomeSellerForm.validateCvv(currentState.creditCard.cvv)
                val ibanValidation = validateBecomeSellerForm.validateIban(currentState.creditCard.iban)

                val hasErrorWithCreditCard = listOf(
                    addressValidation,
                    zipCodeValidation,
                    phoneNumberValidation,
                    creditCardNumberValidation,
                    expirationDateValidation,
                    cvvValidation,
                    ibanValidation
                ).any { !it.positiveResult }

                val hasErrorWithoutCreditCard = listOf(
                    addressValidation,
                    zipCodeValidation,
                    phoneNumberValidation
                ).any { !it.positiveResult }

                if(hasErrorWithCreditCard && currentState.user.creditCards.isEmpty()){
                    _becomeSellerUiState.value = currentState.copy(
                        addressErrorMsg = addressValidation.errorMessage,
                        zipCodeErrorMsg = zipCodeValidation.errorMessage,
                        phoneNumberErrorMsg = phoneNumberValidation.errorMessage,
                        creditCardNumberErrorMsg = creditCardNumberValidation.errorMessage,
                        expirationDateErrorMsg = expirationDateValidation.errorMessage,
                        cvvErrorMsg = cvvValidation.errorMessage,
                        ibanErrorMsg = ibanValidation.errorMessage
                    )
                    return false
                }
                if(hasErrorWithoutCreditCard && currentState.user.creditCards.isNotEmpty()){
                    _becomeSellerUiState.value = currentState.copy(
                        addressErrorMsg = addressValidation.errorMessage,
                        zipCodeErrorMsg = zipCodeValidation.errorMessage,
                        phoneNumberErrorMsg = phoneNumberValidation.errorMessage
                    )
                    return false
                }
                viewModelScope.launch {
                    validationEventChannel.send(ValidationState.Success)
                }
                return true
            } catch (e: Exception){
                _becomeSellerUiState.value = BecomeSellerUiState.Error
                return false
            }
        } else{
            return false
        }
    }

}