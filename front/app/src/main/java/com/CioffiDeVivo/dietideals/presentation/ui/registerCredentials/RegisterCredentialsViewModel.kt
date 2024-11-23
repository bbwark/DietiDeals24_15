package com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.data.repositories.AuthRepository
import com.CioffiDeVivo.dietideals.data.requestModels.LogInRequest
import com.CioffiDeVivo.dietideals.data.validations.ValidateRegistrationForms
import com.CioffiDeVivo.dietideals.data.validations.ValidationState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterCredentialsViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val authRepository: AuthRepository,
    private val validateRegistrationForms: ValidateRegistrationForms = ValidateRegistrationForms()
): ViewModel() {

    private val _registerCredentialsUiState = MutableStateFlow<RegisterCredentialsUiState>(RegisterCredentialsUiState.RegisterParams())
    val registerCredentialsUiState: StateFlow<RegisterCredentialsUiState> = _registerCredentialsUiState.asStateFlow()

    private val validationEventChannel = Channel<ValidationState>()
    val validationRegistrationEvent = validationEventChannel.receiveAsFlow()

    fun registrationAction(registrationEvents: RegistrationEvents){
        try {
            val currentState = _registerCredentialsUiState.value
            if(currentState is RegisterCredentialsUiState.RegisterParams){
                when(registrationEvents){
                    is RegistrationEvents.EmailChanged -> {
                        _registerCredentialsUiState.value = currentState.copy(user = currentState.user.copy(email = registrationEvents.email))
                    }
                    is RegistrationEvents.EmailDeleted -> {
                        _registerCredentialsUiState.value = currentState.copy(user = currentState.user.copy(email = ""))
                    }
                    is RegistrationEvents.NameChanged -> {
                        _registerCredentialsUiState.value = currentState.copy(user = currentState.user.copy(name = registrationEvents.name))
                    }
                    is RegistrationEvents.NameDeleted -> {
                        _registerCredentialsUiState.value = currentState.copy(user = currentState.user.copy(name = ""))
                    }
                    is RegistrationEvents.SurnameChanged -> {
                        _registerCredentialsUiState.value = currentState.copy(user = currentState.user.copy(surname = registrationEvents.surname))
                    }
                    is RegistrationEvents.SurnameDeleted -> {
                        _registerCredentialsUiState.value = currentState.copy(user = currentState.user.copy(surname = ""))
                    }
                    is RegistrationEvents.PasswordChanged -> {
                        _registerCredentialsUiState.value = currentState.copy(user = currentState.user.copy(password = registrationEvents.password))
                    }
                    is RegistrationEvents.RetypePasswordChanged -> {
                        _registerCredentialsUiState.value = currentState.copy(retypePassword = registrationEvents.newPassword)
                    }
                    is RegistrationEvents.SellerChange -> {
                        _registerCredentialsUiState.value = currentState.copy(user = currentState.user.copy(isSeller = registrationEvents.isSeller))
                    }
                    is RegistrationEvents.AddressChanged -> {
                        _registerCredentialsUiState.value = currentState.copy(user = currentState.user.copy(address = registrationEvents.address))
                    }
                    is RegistrationEvents.AddressDeleted -> {
                        _registerCredentialsUiState.value = currentState.copy(user = currentState.user.copy(address = ""))
                    }
                    is RegistrationEvents.ZipCodeChanged -> {
                        _registerCredentialsUiState.value = currentState.copy(user = currentState.user.copy(zipcode = registrationEvents.zipCode))
                    }
                    is RegistrationEvents.ZipCodeDeleted -> {
                        _registerCredentialsUiState.value = currentState.copy(user = currentState.user.copy(zipcode = ""))
                    }
                    is RegistrationEvents.CountryChanged -> {
                        _registerCredentialsUiState.value = currentState.copy(user = currentState.user.copy(country = registrationEvents.country))
                    }
                    is RegistrationEvents.PhoneNumberChanged -> {
                        _registerCredentialsUiState.value = currentState.copy(user = currentState.user.copy(phoneNumber = registrationEvents.phoneNumber))
                    }
                    is RegistrationEvents.PhoneNumberDeleted -> {
                        _registerCredentialsUiState.value = currentState.copy(user = currentState.user.copy(phoneNumber = ""))
                    }
                    is RegistrationEvents.CreditCardNumberChanged -> {
                        _registerCredentialsUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(creditCardNumber = registrationEvents.creditCardNumber))
                    }
                    is RegistrationEvents.CreditCardNumberDeleted -> {
                        _registerCredentialsUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(creditCardNumber = ""))
                    }
                    is RegistrationEvents.ExpirationDateChanged -> {
                        _registerCredentialsUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(expirationDate = registrationEvents.expirationDate))
                    }
                    is RegistrationEvents.ExpirationDateDeleted -> {
                        _registerCredentialsUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(expirationDate = ""))
                    }
                    is RegistrationEvents.CvvChanged -> {
                        _registerCredentialsUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(cvv = registrationEvents.cvv))
                    }
                    is RegistrationEvents.CvvDeleted -> {
                        _registerCredentialsUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(cvv = ""))
                    }
                    is RegistrationEvents.IbanChanged -> {
                        _registerCredentialsUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(iban = registrationEvents.iban))
                    }
                    is RegistrationEvents.IbanDeleted -> {
                        _registerCredentialsUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(iban = ""))
                    }
                    is RegistrationEvents.Submit -> {
                        submitForm()
                    }
                }
            }
        } catch (e: Exception){
            _registerCredentialsUiState.value = RegisterCredentialsUiState.Error
        }
    }

    private fun submitForm() {
        if (validationBlock()) {
            val currentState = _registerCredentialsUiState.value
            if(currentState is RegisterCredentialsUiState.RegisterParams){
                _registerCredentialsUiState.value = RegisterCredentialsUiState.Loading
                viewModelScope.launch {
                    _registerCredentialsUiState.value = try {
                        val registerRequest = currentState.user.copy(creditCards = currentState.user.creditCards + currentState.creditCard)
                        val registerResponse = authRepository.registerUser(registerRequest)
                        val logInRequest = LogInRequest(registerResponse.email, currentState.user.password)
                        val loginResponse = authRepository.loginUser(logInRequest)
                        userPreferencesRepository.saveUserId(loginResponse.user.id)
                        userPreferencesRepository.saveToken(loginResponse.jwt)
                        userPreferencesRepository.saveEmail(loginResponse.user.email)
                        userPreferencesRepository.saveName(loginResponse.user.name)
                        userPreferencesRepository.saveIsSeller(loginResponse.user.isSeller)
                        RegisterCredentialsUiState.Success
                    } catch (e: Exception) {
                        Log.e("Error", "Error: ${e.message}")
                        RegisterCredentialsUiState.Error
                    }
                }
            }

        }
    }

    private fun validationBlock(): Boolean {
        val currentState = _registerCredentialsUiState.value
        if(currentState is RegisterCredentialsUiState.RegisterParams){
            try {
                val emailValidation = validateRegistrationForms.validateEmail(currentState.user.email)
                val nameValidation = validateRegistrationForms.validateName(currentState.user.name)
                val surnameValidation = validateRegistrationForms.validateSurname(currentState.user.surname)
                val passwordValidation = validateRegistrationForms.validatePassword(currentState.user.password)
                val newPasswordValidation = validateRegistrationForms.validateRetypePassword(currentState.user.password, currentState.retypePassword)
                val addressValidation = validateRegistrationForms.validateAddress(currentState.user.address)
                val zipCodeValidation = validateRegistrationForms.validateZipCode(currentState.user.zipcode)
                val phoneNumberValidation = validateRegistrationForms.validatePhoneNumber(currentState.user.phoneNumber)
                val creditCardNumberValidation = validateRegistrationForms.validateCreditCardNumber(currentState.creditCard.creditCardNumber)
                val expirationDateValidation = validateRegistrationForms.validateExpirationDate(currentState.creditCard.expirationDate)
                val cvvValidation = validateRegistrationForms.validateCvv(currentState.creditCard.cvv)
                val ibanValidation = validateRegistrationForms.validateIban(currentState.creditCard.iban)

                val hasErrorNotSeller = listOf(
                    emailValidation,
                    nameValidation,
                    surnameValidation,
                    passwordValidation,
                    newPasswordValidation
                ).any { !it.positiveResult }

                val hasErrorSeller = listOf(
                    emailValidation,
                    nameValidation,
                    surnameValidation,
                    passwordValidation,
                    newPasswordValidation,
                    addressValidation,
                    zipCodeValidation,
                    phoneNumberValidation,
                    creditCardNumberValidation,
                    expirationDateValidation,
                    cvvValidation,
                    ibanValidation
                ).any { !it.positiveResult }

                if(hasErrorNotSeller && !currentState.user.isSeller){
                    _registerCredentialsUiState.value = currentState.copy(
                        emailErrorMsg = emailValidation.errorMessage,
                        nameErrorMsg = nameValidation.errorMessage,
                        surnameErrorMsg = surnameValidation.errorMessage,
                        passwordErrorMsg = passwordValidation.errorMessage,
                        retypePasswordErrorMsg = newPasswordValidation.errorMessage
                    )
                    return false
                }
                if (hasErrorSeller && currentState.user.isSeller){
                    _registerCredentialsUiState.value = currentState.copy(
                        emailErrorMsg = emailValidation.errorMessage,
                        nameErrorMsg = nameValidation.errorMessage,
                        surnameErrorMsg = surnameValidation.errorMessage,
                        passwordErrorMsg = passwordValidation.errorMessage,
                        retypePasswordErrorMsg = newPasswordValidation.errorMessage,
                        addressErrorMsg = addressValidation.errorMessage,
                        zipCodeErrorMsg = zipCodeValidation.errorMessage,
                        phoneNumberErrorMsg = phoneNumberValidation.errorMessage,
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
                _registerCredentialsUiState.value = RegisterCredentialsUiState.Error
                return false
            }
        } else{
            return false
        }
    }
}