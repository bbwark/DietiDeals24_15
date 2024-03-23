package com.CioffiDeVivo.dietideals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.DataModels.User
import com.CioffiDeVivo.dietideals.Events.RegistrationEvent
import com.CioffiDeVivo.dietideals.domain.use_case.ValidateForms
import com.CioffiDeVivo.dietideals.state.RegistrationState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegistrationViewModel( private val validateForms: ValidateForms = ValidateForms() ) : ViewModel() {

    private val _userRegistrationState = MutableStateFlow(RegistrationState())
    val userRegistrationState: StateFlow<RegistrationState> = _userRegistrationState.asStateFlow()

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
                updateIsSeller(registrationEvent.isSeller)
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
        val emailValidation = validateForms.validateEmail(userRegistrationState.value.email)
        val nameValidation = validateForms.validateName(userRegistrationState.value.name)
        val surnameValidation = validateForms.validateSurname(userRegistrationState.value.surname)
        val passwordValidation = validateForms.validatePassword(userRegistrationState.value.password)
        val newPasswordValidation = validateForms.validateNewPassword(userRegistrationState.value.password, userRegistrationState.value.newPassword)
        val addressValidation = validateForms.validateAddress(userRegistrationState.value.address)
        val zipCodeValidation = validateForms.validateZipCode(userRegistrationState.value.zipCode)
        val countryValidation = validateForms.validateCountry(userRegistrationState.value.country)
        val phoneNumberValidation = validateForms.validatePhoneNumber(userRegistrationState.value.phoneNumber)
        val creditCardNumberValidation = validateForms.validateCreditCardNumber(userRegistrationState.value.creditCardNumber)
        val expirationDateValidation = validateForms.validateExpirationDate(userRegistrationState.value.expirationDate)
        val cvvValidation = validateForms.validateCvv(userRegistrationState.value.cvv)
        val ibanValidation = validateForms.validateIban(userRegistrationState.value.iban)

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

        if(hasErrorNotSeller && !userRegistrationState.value.isSeller){
            _userRegistrationState.value = _userRegistrationState.value.copy(
                emailErrorMsg = emailValidation.errorMessage,
                nameErrorMsg = nameValidation.errorMessage,
                surnameErrorMsg = surnameValidation.errorMessage,
                passwordErrorMsg = passwordValidation.errorMessage,
                newPasswordErrorMsg = newPasswordValidation.errorMessage
            )
            return
        } else if (hasErrorSeller && userRegistrationState.value.isSeller){
            _userRegistrationState.value = _userRegistrationState.value.copy(
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
        _userRegistrationState.value = _userRegistrationState.value.copy(
            email = email
        )
    }

    fun deleteEmail(){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            email = ""
        )
    }

    fun updateName(name: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            name = name
        )
    }

    fun deleteName(){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            name = ""
        )
    }

    fun updateSurname(surname: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            surname = surname
        )
    }

    fun deleteSurname(){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            surname = ""
        )
    }

    fun updatePassword(password: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            password = password
        )
    }

    fun updateNewPassword(newPassword: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            newPassword = newPassword
        )
    }

    fun updateIsSeller(isSeller: Boolean){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            isSeller = isSeller
        )
    }

    fun updateAddress(address: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            address = address
        )
    }

    fun deleteAddress(){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            address = ""
        )
    }

    fun updateZipCode(zipCode: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            zipCode = zipCode
        )
    }

    fun deleteZipCode(){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            zipCode = ""
        )
    }

    fun updateCountry(country: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            country = country
        )
    }

    fun updatePhoneNumber(phoneNumber: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            phoneNumber = phoneNumber
        )
    }

    fun deletePhoneNumber(){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            phoneNumber = ""
        )
    }

    fun updateCreditCardNumber(creditCardNumber: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            creditCardNumber = creditCardNumber
        )
    }

    fun deleteCreditCardNumber(){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            creditCardNumber = ""
        )
    }

    fun updateExpirationDate(expirationDate: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            expirationDate = expirationDate
        )
    }

    fun deleteExpirationDate(){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            expirationDate = ""
        )
    }

    fun updateCvv(cvv: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            cvv = cvv
        )
    }

    fun deleteCvv(){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            cvv = ""
        )
    }

    fun updateIban(iban: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            iban = iban
        )
    }

    fun deleteIban(){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            iban = ""
        )
    }
}