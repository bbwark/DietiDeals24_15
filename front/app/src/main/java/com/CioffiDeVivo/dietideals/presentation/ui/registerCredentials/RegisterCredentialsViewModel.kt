package com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.mappers.toRequestModel
import com.CioffiDeVivo.dietideals.data.models.Country
import com.CioffiDeVivo.dietideals.data.validations.ValidateRegistrationForms
import com.CioffiDeVivo.dietideals.data.validations.ValidationState
import com.CioffiDeVivo.dietideals.presentation.ui.loginCredentials.LogInCredentialsUiState
import com.CioffiDeVivo.dietideals.services.ApiService
import com.CioffiDeVivo.dietideals.services.AuthService
import com.CioffiDeVivo.dietideals.utils.EncryptedPreferencesManager
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterCredentialsViewModel(
    application: Application,
    private val validateRegistrationForms: ValidateRegistrationForms = ValidateRegistrationForms()
) : AndroidViewModel(application) {

    private val _registerCredentialsUiState = MutableStateFlow<RegisterCredentialsUiState>(RegisterCredentialsUiState.RegisterParams())
    val registerCredentialsUiState: StateFlow<RegisterCredentialsUiState> = _registerCredentialsUiState.asStateFlow()

    private val validationEventChannel = Channel<ValidationState>()
    val validationRegistrationEvent = validationEventChannel.receiveAsFlow()

    private val sharedPreferences by lazy {
        application.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    }

    fun registrationAction(registrationEvents: RegistrationEvents){
        when(registrationEvents){
            is RegistrationEvents.EmailChanged -> {
                updateEmail(registrationEvents.email)
            }
            is RegistrationEvents.EmailDeleted -> {
                deleteEmail()
            }
            is RegistrationEvents.NameChanged -> {
                updateName(registrationEvents.name)
            }
            is RegistrationEvents.NameDeleted -> {
                deleteName()
            }
            is RegistrationEvents.SurnameChanged -> {
                updateSurname(registrationEvents.surname)
            }
            is RegistrationEvents.SurnameDeleted -> {
                deleteSurname()
            }
            is RegistrationEvents.PasswordChanged -> {
                updatePassword(registrationEvents.password)
            }
            is RegistrationEvents.RetypePasswordChanged -> {
                updateNewPassword(registrationEvents.newPassword)
            }
            is RegistrationEvents.SellerChange -> {
                updateIsSeller(registrationEvents.isSeller)
            }
            is RegistrationEvents.AddressChanged -> {
                updateAddress(registrationEvents.address)
            }
            is RegistrationEvents.AddressDeleted -> {
                deleteAddress()
            }
            is RegistrationEvents.ZipCodeChanged -> {
                updateZipCode(registrationEvents.zipCode)
            }
            is RegistrationEvents.ZipCodeDeleted -> {
                deleteZipCode()
            }
            is RegistrationEvents.CountryChanged -> {
                updateCountry(registrationEvents.country)
            }
            is RegistrationEvents.PhoneNumberChanged -> {
                updatePhoneNumber(registrationEvents.phoneNumber)
            }
            is RegistrationEvents.PhoneNumberDeleted -> {
                deletePhoneNumber()
            }
            is RegistrationEvents.CreditCardNumberChanged -> {
                updateCreditCardNumber(registrationEvents.creditCardNumber)
            }
            is RegistrationEvents.CreditCardNumberDeleted -> {
                deleteCreditCardNumber()
            }
            is RegistrationEvents.ExpirationDateChanged -> {
                updateExpirationDate(registrationEvents.expirationDate)
            }
            is RegistrationEvents.ExpirationDateDeleted -> {
                deleteExpirationDate()
            }
            is RegistrationEvents.CvvChanged -> {
                updateCvv(registrationEvents.cvv)
            }
            is RegistrationEvents.CvvDeleted -> {
                deleteCvv()
            }
            is RegistrationEvents.IbanChanged -> {
                updateIban(registrationEvents.iban)
            }
            is RegistrationEvents.IbanDeleted -> {
                deleteIban()
            }
            is RegistrationEvents.Submit -> {
                submitForm()
            }
        }
    }

    private fun submitForm() {
        if (validationBlock()) {
            val currentState = _registerCredentialsUiState.value
            if(currentState is RegisterCredentialsUiState.RegisterParams){
                _registerCredentialsUiState.value = RegisterCredentialsUiState.Loading
                viewModelScope.launch {
                    _registerCredentialsUiState.value = try {
                        val updatedUser = currentState.user.copy(creditCards = currentState.user.creditCards + currentState.creditCard)
                        Log.i("INFORMATION USER", updatedUser.creditCards[0].creditCardNumber)
                        val userRequest = updatedUser.toRequestModel()
                        val registerResponse = AuthService.registerUser(userRequest)
                        if (registerResponse.status.isSuccess()) {
                            val loginResponse = AuthService.loginUser(
                                currentState.user.email,
                                currentState.user.password
                            )
                            if (loginResponse.status.isSuccess()) {
                                val jsonObject = Gson().fromJson(loginResponse.bodyAsText(), JsonObject::class.java)
                                val token = jsonObject.get("jwt").asString
                                if (token.isNotEmpty()) {
                                    val userId = jsonObject.getAsJsonObject("user").get("id").asString
                                    sharedPreferences.edit().apply {
                                        putString("token", token)
                                        putString("userId", userId)
                                        apply()
                                    }
                                    ApiService.initialize(
                                        token,
                                        getApplication<Application>().applicationContext
                                    )
                                }
                                RegisterCredentialsUiState.Success
                            } else{
                                Log.e("Error", "Error: REST Unsuccessful on Login")
                                RegisterCredentialsUiState.Error
                            }
                        } else{
                            Log.e("Error", "Error: REST Unsuccessful on Register")
                            RegisterCredentialsUiState.Error
                        }
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
                val zipCodeValidation = validateRegistrationForms.validateZipCode(currentState.user.zipCode)
                val phoneNumberValidation = validateRegistrationForms.validatePhoneNumber(currentState.user.phoneNumber)
                val creditCardNumberValidation = validateRegistrationForms.validateCreditCardNumber(currentState.creditCard.creditCardNumber)
                val expirationDateValidation = validateRegistrationForms.validateExpirationDate(currentState.expirationDate)
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

    /*Update & Delete for RegistrationState*/

    private fun updateEmail(email: String){
        try {
            val currentState = _registerCredentialsUiState.value
            if(currentState is RegisterCredentialsUiState.RegisterParams){
                _registerCredentialsUiState.value = currentState.copy(
                    user = currentState.user.copy(
                        email = email
                    )
                )
            }
        } catch (e: Exception){
            _registerCredentialsUiState.value = RegisterCredentialsUiState.Error
        }
    }

    private fun deleteEmail(){
        updateEmail("")
    }

    private fun updateName(name: String){
        try {
            val currentState = _registerCredentialsUiState.value
            if(currentState is RegisterCredentialsUiState.RegisterParams){
                _registerCredentialsUiState.value = currentState.copy(
                    user = currentState.user.copy(
                        name = name
                    )
                )
            }
        } catch (e: Exception){
            _registerCredentialsUiState.value = RegisterCredentialsUiState.Error
        }
    }

    private fun deleteName(){
        updateName("")
    }

    private fun updateSurname(surname: String){
        try {
            val currentState = _registerCredentialsUiState.value
            if(currentState is RegisterCredentialsUiState.RegisterParams){
                _registerCredentialsUiState.value = currentState.copy(
                    user = currentState.user.copy(
                        surname = surname
                    )
                )
            }
        } catch (e: Exception){
            _registerCredentialsUiState.value = RegisterCredentialsUiState.Error
        }
    }

    private fun deleteSurname(){
        updateSurname("")
    }

    private fun updatePassword(password: String){
        try {
            val currentState = _registerCredentialsUiState.value
            if(currentState is RegisterCredentialsUiState.RegisterParams){
                _registerCredentialsUiState.value = currentState.copy(
                    user = currentState.user.copy(
                        password = password
                    )
                )
            }
        } catch (e: Exception){
            _registerCredentialsUiState.value = RegisterCredentialsUiState.Error
        }
    }

    private fun updateNewPassword(newPassword: String){
        try {
            val currentState = _registerCredentialsUiState.value
            if(currentState is RegisterCredentialsUiState.RegisterParams){
                _registerCredentialsUiState.value = currentState.copy(
                    retypePassword = newPassword
                )
            }
        } catch (e: Exception){
            _registerCredentialsUiState.value = RegisterCredentialsUiState.Error
        }
    }

    private fun updateIsSeller(isSeller: Boolean){
        try {
            val currentState = _registerCredentialsUiState.value
            if(currentState is RegisterCredentialsUiState.RegisterParams){
                _registerCredentialsUiState.value = currentState.copy(
                    user = currentState.user.copy(
                        isSeller = isSeller
                    )
                )
            }
        } catch (e: Exception){
            _registerCredentialsUiState.value = RegisterCredentialsUiState.Error
        }
    }

    private fun updateAddress(address: String){
        try {
            val currentState = _registerCredentialsUiState.value
            if(currentState is RegisterCredentialsUiState.RegisterParams){
                _registerCredentialsUiState.value = currentState.copy(
                    user = currentState.user.copy(
                        address = address
                    )
                )
            }
        } catch (e: Exception){
            _registerCredentialsUiState.value = RegisterCredentialsUiState.Error
        }
    }

    private fun deleteAddress(){
        updateAddress("")
    }

    private fun updateZipCode(zipCode: String){
        try {
            val currentState = _registerCredentialsUiState.value
            if(currentState is RegisterCredentialsUiState.RegisterParams){
                _registerCredentialsUiState.value = currentState.copy(
                    user = currentState.user.copy(
                        zipCode = zipCode
                    )
                )
            }
        } catch (e: Exception){
            _registerCredentialsUiState.value = RegisterCredentialsUiState.Error
        }
    }

    private fun deleteZipCode(){
        updateZipCode("")
    }

    private fun updateCountry(country: Country){
        try {
            val currentState = _registerCredentialsUiState.value
            if(currentState is RegisterCredentialsUiState.RegisterParams){
                _registerCredentialsUiState.value = currentState.copy(
                    user = currentState.user.copy(
                        country = country
                    )
                )
            }
        } catch (e: Exception){
            _registerCredentialsUiState.value = RegisterCredentialsUiState.Error
        }
    }

    private fun updatePhoneNumber(phoneNumber: String){
        try {
            val currentState = _registerCredentialsUiState.value
            if(currentState is RegisterCredentialsUiState.RegisterParams){
                _registerCredentialsUiState.value = currentState.copy(
                    user = currentState.user.copy(
                        phoneNumber = phoneNumber
                    )
                )
            }
        } catch (e: Exception){
            _registerCredentialsUiState.value = RegisterCredentialsUiState.Error
        }
    }

    private fun deletePhoneNumber(){
        updatePhoneNumber("")
    }

    private fun updateCreditCardNumber(creditCardNumber: String){
        try {
            val currentState = _registerCredentialsUiState.value
            if(currentState is RegisterCredentialsUiState.RegisterParams){
                _registerCredentialsUiState.value = currentState.copy(
                    creditCard = currentState.creditCard.copy(
                        creditCardNumber = creditCardNumber
                    )
                )
            }
        } catch (e: Exception){
            _registerCredentialsUiState.value = RegisterCredentialsUiState.Error
        }
    }

    private fun deleteCreditCardNumber(){
        updateCreditCardNumber("")
    }

    private fun updateExpirationDate(expirationDate: String){
        try {
            val currentState = _registerCredentialsUiState.value
            if(currentState is RegisterCredentialsUiState.RegisterParams){
                _registerCredentialsUiState.value = currentState.copy(
                    expirationDate = expirationDate
                )
            }
        } catch (e: Exception){
            _registerCredentialsUiState.value = RegisterCredentialsUiState.Error
        }
    }

    private fun deleteExpirationDate(){
        updateExpirationDate("")
    }

    private fun updateCvv(cvv: String){
        try {
            val currentState = _registerCredentialsUiState.value
            if(currentState is RegisterCredentialsUiState.RegisterParams){
                _registerCredentialsUiState.value = currentState.copy(
                    creditCard = currentState.creditCard.copy(
                        cvv = cvv
                    )
                )
            }
        } catch (e: Exception){
            _registerCredentialsUiState.value = RegisterCredentialsUiState.Error
        }
    }

    private fun deleteCvv(){
        updateCvv("")
    }

    private fun updateIban(iban: String){
        try {
            val currentState = _registerCredentialsUiState.value
            if(currentState is RegisterCredentialsUiState.RegisterParams){
                _registerCredentialsUiState.value = currentState.copy(
                    creditCard = currentState.creditCard.copy(
                        iban = iban
                    )
                )
            }
        } catch (e: Exception){
            _registerCredentialsUiState.value = RegisterCredentialsUiState.Error
        }
    }

    private fun deleteIban(){
        updateIban("")
    }
}