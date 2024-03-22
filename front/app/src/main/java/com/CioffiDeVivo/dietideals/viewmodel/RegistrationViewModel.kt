package com.CioffiDeVivo.dietideals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.Events.RegistrationEvent
import com.CioffiDeVivo.dietideals.domain.use_case.ValidateForms
import com.CioffiDeVivo.dietideals.state.RegistrationState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class RegistrationViewModel( private val validateForms: ValidateForms = ValidateForms() ) : ViewModel() {

    private val _registrationState = MutableStateFlow(RegistrationState())
    val registrationState: StateFlow<RegistrationState> = _registrationState.asStateFlow()
    private val validationEventChannel = Channel<ValidationState>()
    val validationEvent = validationEventChannel.receiveAsFlow()

    fun registrationAction(registrationEvent: RegistrationEvent){
        when(registrationEvent){
            is RegistrationEvent.EmailChanged -> {
                updateEmail(registrationEvent.email)
            }
            is RegistrationEvent.NameChanged -> {
                updateName(registrationEvent.name)
            }
            is RegistrationEvent.SurnameChanged -> {
                updateSurname(registrationEvent.surname)
            }
            is RegistrationEvent.PasswordChanged -> {
                updatePassword(registrationEvent.password)
            }
            is RegistrationEvent.NewPasswordChanged -> {
                updateNewPassword(registrationEvent.newPassword)
            }
            is RegistrationEvent.SellerChange -> {
                updateIsSeller()
            }
            is RegistrationEvent.AddressChanged -> {
                updateAddress(registrationEvent.address)
            }
            is RegistrationEvent.ZipCodeChanged -> {
                updateZipCode(registrationEvent.zipCode)
            }
            is RegistrationEvent.CountryChanged -> {
                updateCountry(registrationEvent.country)
            }
            is RegistrationEvent.PhoneNumberChanged -> {
                updatePhoneNumber(registrationEvent.phoneNumber)
            }
            is RegistrationEvent.CreditCardNumberChanged -> {
                updateCreditCardNumber(registrationEvent.creditCardNumber)
            }
            is RegistrationEvent.ExpirationDateChanged -> {
                updateExpirationDate(registrationEvent.expirationDate)
            }
            is RegistrationEvent.CvvChanged -> {
                updateCvv(registrationEvent.cvv)
            }
            is RegistrationEvent.IbanChanged -> {
                updateIban(registrationEvent.iban)
            }
            is RegistrationEvent.Submit -> {
                submitForm()
            }
        }
    }


    private fun submitForm() {
        val emailValidation = validateForms.validateEmail(registrationState.value.email)
        val nameValidation = validateForms.validateName(registrationState.value.name)
        val surnameValidation = validateForms.validateSurname(registrationState.value.surname)
        val passwordValidation = validateForms.validatePassword(registrationState.value.password)
        val newPasswordValidation = validateForms.validateNewPassword(registrationState.value.password, registrationState.value.newPassword)
        val addressValidation = validateForms.validateAddress(registrationState.value.address)
        val zipCodeValidation = validateForms.validateZipCode(registrationState.value.zipCode)
        val countryValidation = validateForms.validateCountry(registrationState.value.country)
        val phoneNumberValidation = validateForms.validatePhoneNumber(registrationState.value.phoneNumber)
        val creditCardNumberValidation = validateForms.validateCreditCardNumber(registrationState.value.creditCardNumber)
        val expirationDateValidation = validateForms.validateExpirationDate(registrationState.value.expirationDate)
        val cvvValidation = validateForms.validateCvv(registrationState.value.cvv)
        val ibanValidation = validateForms.validateIban(registrationState.value.iban)

        val hasErrorNotSeller = listOf(
            emailValidation,
            nameValidation,
            surnameValidation,
            passwordValidation,
            newPasswordValidation,
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

        if(hasErrorNotSeller && !registrationState.value.isSeller){
            _registrationState.value = _registrationState.value.copy(
                emailErrorMsg = emailValidation.errorMessage,
                nameErrorMsg = nameValidation.errorMessage,
                surnameErrorMsg = surnameValidation.errorMessage,
                passwordErrorMsg = passwordValidation.errorMessage,
                newPasswordErrorMsg = newPasswordValidation.errorMessage
            )
            return
        } else if (hasErrorSeller){
            _registrationState.value = _registrationState.value.copy(
                emailErrorMsg = emailValidation.errorMessage,
                nameErrorMsg = nameValidation.errorMessage,
                surnameErrorMsg = surnameValidation.errorMessage,
                passwordErrorMsg = passwordValidation.errorMessage,
                newPasswordErrorMsg = newPasswordValidation.errorMessage,
                addressErrorMsg = addressValidation.errorMessage,
                zipCodeErrorMsg = zipCodeValidation.errorMessage,
                countryErrorMsg = countryValidation.errorMessage,
                phoneNumberErrorMsg = phoneNumberValidation.errorMessage,
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

    sealed class ValidationState {
        object Loading:ValidationState()
        object Success: ValidationState()
        data class Error(val errorMessage:String):ValidationState()

    }

    /*Update & Delete for RegistrationState*/

    fun updateEmail(email: String){
        _registrationState.value = _registrationState.value.copy(
            email = email
        )
    }

    fun deleteEmail(){
        _registrationState.value = _registrationState.value.copy(
            email = ""
        )
    }

    fun updateName(name: String){
        _registrationState.value = _registrationState.value.copy(
            name = name
        )
    }

    fun deleteName(){
        _registrationState.value = _registrationState.value.copy(
            name = ""
        )
    }

    fun updateSurname(surname: String){
        _registrationState.value = _registrationState.value.copy(
            surname = surname
        )
    }

    fun deleteSurname(){
        _registrationState.value = _registrationState.value.copy(
            surname = ""
        )
    }

    fun updatePassword(password: String){
        _registrationState.value = _registrationState.value.copy(
            password = password
        )
    }

    fun updateNewPassword(newPassword: String){
        _registrationState.value = _registrationState.value.copy(
            newPassword = newPassword
        )
    }

    fun updateIsSeller(){
        _registrationState.value = _registrationState.value.copy(
            isSeller = !_registrationState.value.isSeller
        )
    }

    fun updateAddress(address: String){
        _registrationState.value = _registrationState.value.copy(
            address = address
        )
    }

    fun deleteAddress(){
        _registrationState.value = _registrationState.value.copy(
            address = ""
        )
    }

    fun updateZipCode(zipCode: String){
        _registrationState.value = _registrationState.value.copy(
            zipCode = zipCode
        )
    }

    fun deleteZipCode(){
        _registrationState.value = _registrationState.value.copy(
            zipCode = ""
        )
    }

    fun updateCountry(country: String){
        _registrationState.value = _registrationState.value.copy(
            country = country
        )
    }

    fun updatePhoneNumber(phoneNumber: String){
        _registrationState.value = _registrationState.value.copy(
            phoneNumber = phoneNumber
        )
    }

    fun deletePhoneNumber(){
        _registrationState.value = _registrationState.value.copy(
            phoneNumber = ""
        )
    }

    fun updateCreditCardNumber(creditCardNumber: String){
        _registrationState.value = _registrationState.value.copy(
            creditCardNumber = creditCardNumber
        )
    }

    fun deleteCreditCardNumber(){
        _registrationState.value = _registrationState.value.copy(
            creditCardNumber = ""
        )
    }

    fun updateExpirationDate(expirationDate: String){
        _registrationState.value = _registrationState.value.copy(
            expirationDate = expirationDate
        )
    }

    fun deleteExpirationDate(){
        _registrationState.value = _registrationState.value.copy(
            expirationDate = ""
        )
    }

    fun updateCvv(cvv: String){
        _registrationState.value = _registrationState.value.copy(
            cvv = cvv
        )
    }

    fun deleteCvv(){
        _registrationState.value = _registrationState.value.copy(
            cvv = ""
        )
    }

    fun updateIban(iban: String){
        _registrationState.value = _registrationState.value.copy(
            iban = iban
        )
    }

    fun deleteIban(){
        _registrationState.value = _registrationState.value.copy(
            iban = ""
        )
    }
}