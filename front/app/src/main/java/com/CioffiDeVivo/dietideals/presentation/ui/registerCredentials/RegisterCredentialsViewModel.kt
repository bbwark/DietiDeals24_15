package com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.data.repositories.AuthRepository
import com.CioffiDeVivo.dietideals.data.repositories.UserRepository
import com.CioffiDeVivo.dietideals.data.requestModels.LogInRequest
import com.CioffiDeVivo.dietideals.data.validations.ValidateUserForms
import com.CioffiDeVivo.dietideals.data.validations.ValidationState
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterCredentialsViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val validateUserForms: ValidateUserForms = ValidateUserForms()
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
                        if(!currentState.user.isSeller){
                            authRepository.registerUser(currentState.user)
                        } else{
                            val registerRequest = currentState.user.copy(creditCards = currentState.user.creditCards + currentState.creditCard)
                            authRepository.registerUser(registerRequest)
                        }
                        val logInRequest = LogInRequest(currentState.user.email, currentState.user.password)
                        val loginResponse = authRepository.loginUser(logInRequest)
                        userPreferencesRepository.saveUserId(loginResponse.user.id)
                        userPreferencesRepository.saveToken(loginResponse.jwt)
                        userPreferencesRepository.saveEmail(loginResponse.user.email)
                        userPreferencesRepository.saveName(loginResponse.user.name)
                        userPreferencesRepository.saveIsSeller(loginResponse.user.isSeller)
                        val firebase = FirebaseMessaging.getInstance().token.await()
                        val deviceTokenArray = loginResponse.user.deviceTokens.plus(firebase)
                        userRepository.updateUser(loginResponse.user.id, loginResponse.user.copy(
                            password = "",
                            deviceTokens = deviceTokenArray)
                        )
                        userPreferencesRepository.saveDeviceToken(firebase)
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
                val emailValidation = validateUserForms.validateEmail(currentState.user.email)
                val nameValidation = validateUserForms.validateName(currentState.user.name)
                val passwordValidation = validateUserForms.validatePassword(currentState.user.password)
                val newPasswordValidation = validateUserForms.validateRetypePassword(currentState.user.password, currentState.retypePassword)
                val addressValidation = validateUserForms.validateAddress(currentState.user.address)
                val zipCodeValidation = validateUserForms.validateZipCode(currentState.user.zipcode)
                val phoneNumberValidation = validateUserForms.validatePhoneNumber(currentState.user.phoneNumber)
                val creditCardNumberValidation = validateUserForms.validateCreditCardNumber(currentState.creditCard.creditCardNumber)
                val expirationDateValidation = validateUserForms.validateExpirationDate(currentState.creditCard.expirationDate)
                val cvvValidation = validateUserForms.validateCvv(currentState.creditCard.cvv)
                val ibanValidation = validateUserForms.validateIban(currentState.creditCard.iban)

                val hasErrorNotSeller = listOf(
                    emailValidation,
                    nameValidation,
                    passwordValidation,
                    newPasswordValidation
                ).any { !it.positiveResult }

                val hasErrorSeller = listOf(
                    emailValidation,
                    nameValidation,
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
                        passwordErrorMsg = passwordValidation.errorMessage,
                        retypePasswordErrorMsg = newPasswordValidation.errorMessage
                    )
                    return false
                }
                if (hasErrorSeller && currentState.user.isSeller){
                    _registerCredentialsUiState.value = currentState.copy(
                        emailErrorMsg = emailValidation.errorMessage,
                        nameErrorMsg = nameValidation.errorMessage,
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