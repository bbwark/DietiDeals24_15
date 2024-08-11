package com.CioffiDeVivo.dietideals.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.Events.RegistrationEvent
import com.CioffiDeVivo.dietideals.domain.Mappers.toRequestModel
import com.CioffiDeVivo.dietideals.domain.use_case.ValidateRegistrationForms
import com.CioffiDeVivo.dietideals.domain.use_case.ValidationState
import com.CioffiDeVivo.dietideals.utils.ApiService
import com.CioffiDeVivo.dietideals.utils.AuthService
import com.CioffiDeVivo.dietideals.utils.EncryptedPreferencesManager
import com.CioffiDeVivo.dietideals.viewmodel.state.RegistrationState
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

    fun registrationAction(registrationEvent: RegistrationEvent){
        when(registrationEvent){
            is RegistrationEvent.EmailChanged -> {
                updateEmail(registrationEvent.email)
            }
            is RegistrationEvent.EmailDeleted -> {
                deleteEmail()
            }
            is RegistrationEvent.NameChanged -> {
                updateName(registrationEvent.name)
            }
            is RegistrationEvent.NameDeleted -> {
                deleteName()
            }
            is RegistrationEvent.SurnameChanged -> {
                updateSurname(registrationEvent.surname)
            }
            is RegistrationEvent.SurnameDeleted -> {
                deleteSurname()
            }
            is RegistrationEvent.PasswordChanged -> {
                updatePassword(registrationEvent.password)
            }
            is RegistrationEvent.RetypePasswordChanged -> {
                updateNewPassword(registrationEvent.newPassword)
            }
            is RegistrationEvent.SellerChange -> {
                updateIsSeller(registrationEvent.isSeller)
            }
            is RegistrationEvent.AddressChanged -> {
                updateAddress(registrationEvent.address)
            }
            is RegistrationEvent.AddressDeleted -> {
                deleteAddress()
            }
            is RegistrationEvent.ZipCodeChanged -> {
                updateZipCode(registrationEvent.zipCode)
            }
            is RegistrationEvent.ZipCodeDeleted -> {
                deleteZipCode()
            }
            is RegistrationEvent.CountryChanged -> {
                updateCountry(registrationEvent.country)
            }
            is RegistrationEvent.PhoneNumberChanged -> {
                updatePhoneNumber(registrationEvent.phoneNumber)
            }
            is RegistrationEvent.PhoneNumberDeleted -> {
                deletePhoneNumber()
            }
            is RegistrationEvent.CreditCardNumberChanged -> {
                updateCreditCardNumber(registrationEvent.creditCardNumber)
            }
            is RegistrationEvent.CreditCardNumberDeleted -> {
                deleteCreditCardNumber()
            }
            is RegistrationEvent.ExpirationDateChanged -> {
                updateExpirationDate(registrationEvent.expirationDate)
            }
            is RegistrationEvent.ExpirationDateDeleted -> {
                deleteExpirationDate()
            }
            is RegistrationEvent.CvvChanged -> {
                updateCvv(registrationEvent.cvv)
            }
            is RegistrationEvent.CvvDeleted -> {
                deleteCvv()
            }
            is RegistrationEvent.IbanChanged -> {
                updateIban(registrationEvent.iban)
            }
            is RegistrationEvent.IbanDeleted -> {
                deleteIban()
            }
            is RegistrationEvent.Submit -> {
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
                            validationEventChannel.send(ValidationState.Success)
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
        val countryValidation = validateRegistrationForms.validateCountry(userRegistrationState.value.user.country)
        val phoneNumberValidation = validateRegistrationForms.validatePhoneNumber(userRegistrationState.value.user.phoneNumber)
        val creditCardNumberValidation = validateRegistrationForms.validateCreditCardNumber(userRegistrationState.value.card.creditCardNumber)
        val expirationDateValidation = validateRegistrationForms.validateExpirationDate(userRegistrationState.value.card.expirationDate.format(DateTimeFormatter.ofPattern("MM/yy")))
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
            countryValidation,
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
                countryErrorMsg = countryValidation.errorMessage,
                phoneNumberErrorMsg = phoneNumberValidation.errorMessage,
                creditCardNumberErrorMsg = creditCardNumberValidation.errorMessage,
                expirationDateErrorMsg = expirationDateValidation.errorMessage,
                cvvErrorMsg = cvvValidation.errorMessage,
                ibanErrorMsg = ibanValidation.errorMessage,
            )
            return false
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

    private fun updateCountry(country: String){
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
            card = _userRegistrationState.value.card.copy(
                expirationDate = LocalDate.parse("01/$expirationDate", DateTimeFormatter.ofPattern("dd/MM/yy"))
            )
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