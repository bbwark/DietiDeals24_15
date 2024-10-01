package com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.mappers.toRequestModel
import com.CioffiDeVivo.dietideals.domain.models.Country
import com.CioffiDeVivo.dietideals.domain.validations.ValidateRegistrationForms
import com.CioffiDeVivo.dietideals.domain.validations.ValidationState
import com.CioffiDeVivo.dietideals.utils.ApiService
import com.CioffiDeVivo.dietideals.utils.AuthService
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RegisterCredentialsViewModel(
    application: Application,
    private val validateRegistrationForms: ValidateRegistrationForms = ValidateRegistrationForms()
) : AndroidViewModel(application) {

    private val _userRegistrationState = MutableStateFlow(RegistrationState())
    val userRegistrationState: StateFlow<RegistrationState> = _userRegistrationState.asStateFlow()

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
            viewModelScope.launch {
                try {

                    if (_userRegistrationState.value.user.isSeller) {
                        var cards = _userRegistrationState.value.user.creditCards
                        cards += _userRegistrationState.value.card

                        _userRegistrationState.value = _userRegistrationState.value.copy(
                            user = _userRegistrationState.value.user.copy(
                                creditCards = cards
                            )
                        )
                    }

                    val user = _userRegistrationState.value.user.toRequestModel()
                    val registerResponse = AuthService.registerUser(user)
                    if (registerResponse.status.isSuccess()) {
                        val loginResponse = user.email?.let {
                            user.password?.let { it1 ->
                                AuthService.loginUser(
                                    it,
                                    it1
                                )
                            }
                        }
                        if (loginResponse != null && loginResponse.status.isSuccess()) {
                            val registerJsonObject = Gson().fromJson(
                                registerResponse.bodyAsText(),
                                JsonObject::class.java
                            )
                            val loginJsonObject =
                                Gson().fromJson(loginResponse.bodyAsText(), JsonObject::class.java)
                            val userId = registerJsonObject.get("id").asString
                            val token = loginJsonObject.get("jwt").asString

                            sharedPreferences.edit().apply {
                                putString("userId", userId)
                                apply()
                            }

                            val encryptedSharedPreferences =
                                EncryptedPreferencesManager.getEncryptedPreferences()
                            encryptedSharedPreferences.edit().apply() {
                                putString("email", _userRegistrationState.value.user.email)
                                putString("password", _userRegistrationState.value.user.password)
                                apply()
                            }

                            ApiService.initialize(
                                token,
                                getApplication<Application>().applicationContext
                            )
                        }
                    }

                } catch (e: Exception) {
                    //TODO error handling
                }
            }
        }
    }

    private fun validationBlock(): Boolean {
        val emailValidation = validateRegistrationForms.validateEmail(userRegistrationState.value.user.email)
        val nameValidation = validateRegistrationForms.validateName(userRegistrationState.value.user.name)
        val surnameValidation = validateRegistrationForms.validateSurname(userRegistrationState.value.user.surname)
        val passwordValidation = validateRegistrationForms.validatePassword(userRegistrationState.value.user.password)
        val newPasswordValidation = validateRegistrationForms.validateRetypePassword(userRegistrationState.value.user.password, userRegistrationState.value.retypePassword)
        val addressValidation = validateRegistrationForms.validateAddress(userRegistrationState.value.user.address)
        val zipCodeValidation = validateRegistrationForms.validateZipCode(userRegistrationState.value.user.zipCode)
        val phoneNumberValidation = validateRegistrationForms.validatePhoneNumber(userRegistrationState.value.user.phoneNumber)
        val creditCardNumberValidation = validateRegistrationForms.validateCreditCardNumber(userRegistrationState.value.card.creditCardNumber)
        val expirationDateValidation = validateRegistrationForms.validateExpirationDate(userRegistrationState.value.expirationDate)
        val cvvValidation = validateRegistrationForms.validateCvv(userRegistrationState.value.card.cvv)
        val ibanValidation = validateRegistrationForms.validateIban(userRegistrationState.value.card.iban)

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

        if(hasErrorNotSeller && !userRegistrationState.value.user.isSeller){
            _userRegistrationState.value = _userRegistrationState.value.copy(
                emailErrorMsg = emailValidation.errorMessage,
                nameErrorMsg = nameValidation.errorMessage,
                surnameErrorMsg = surnameValidation.errorMessage,
                passwordErrorMsg = passwordValidation.errorMessage,
                retypePasswordErrorMsg = newPasswordValidation.errorMessage
            )
            return false
        }
        if (hasErrorSeller && userRegistrationState.value.user.isSeller){
            _userRegistrationState.value = _userRegistrationState.value.copy(
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
    }

    /*Update & Delete for RegistrationState*/

    private fun updateEmail(email: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            user = _userRegistrationState.value.user.copy(
                email = email
            )
        )
    }

    private fun deleteEmail(){
        updateEmail("")
    }

    private fun updateName(name: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            user = _userRegistrationState.value.user.copy(
                name = name
            )
        )
    }

    private fun deleteName(){
        updateName("")
    }

    private fun updateSurname(surname: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            user = _userRegistrationState.value.user.copy(
                surname = surname
            )
        )
    }

    private fun deleteSurname(){
        updateSurname("")
    }

    private fun updatePassword(password: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            user = _userRegistrationState.value.user.copy(
                password = password
            )
        )
    }

    private fun updateNewPassword(newPassword: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            retypePassword = newPassword
        )
    }

    private fun updateIsSeller(isSeller: Boolean){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            user = _userRegistrationState.value.user.copy(
                isSeller = isSeller
            )
        )
    }

    private fun updateAddress(address: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            user = _userRegistrationState.value.user.copy(
                address = address
            )
        )
    }

    private fun deleteAddress(){
        updateAddress("")
    }

    private fun updateZipCode(zipCode: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            user = _userRegistrationState.value.user.copy(
                zipCode = zipCode
            )
        )
    }

    private fun deleteZipCode(){
        updateZipCode("")
    }

    private fun updateCountry(country: Country){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            user = _userRegistrationState.value.user.copy(
                country = country
            )
        )
    }

    private fun updatePhoneNumber(phoneNumber: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            user = _userRegistrationState.value.user.copy(
                phoneNumber = phoneNumber
            )
        )
    }

    private fun deletePhoneNumber(){
        updatePhoneNumber("")
    }

    private fun updateCreditCardNumber(creditCardNumber: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            card = _userRegistrationState.value.card.copy(
                creditCardNumber = creditCardNumber
            )
        )
    }

    private fun deleteCreditCardNumber(){
        updateCreditCardNumber("")
    }

    private fun updateExpirationDate(expirationDate: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            expirationDate = expirationDate
        )
    }

    private fun deleteExpirationDate(){
        updateExpirationDate("")
    }

    private fun updateCvv(cvv: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            card = _userRegistrationState.value.card.copy(
                cvv = cvv
            )
        )
    }

    private fun deleteCvv(){
        updateCvv("")
    }

    private fun updateIban(iban: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            card = _userRegistrationState.value.card.copy(
                iban = iban
            )
        )
    }

    private fun deleteIban(){
        updateIban("")
    }
}