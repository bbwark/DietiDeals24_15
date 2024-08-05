package com.CioffiDeVivo.dietideals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.Events.RegistrationEvent
import com.CioffiDeVivo.dietideals.domain.use_case.ValidateRegistrationForms
import com.CioffiDeVivo.dietideals.domain.use_case.ValidationState
import com.CioffiDeVivo.dietideals.viewmodel.state.RegistrationState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterCredentialsViewModel(private val validateRegistrationForms: ValidateRegistrationForms = ValidateRegistrationForms() ) : ViewModel() {

    private val _userRegistrationState = MutableStateFlow(RegistrationState())
    val userRegistrationState: StateFlow<RegistrationState> = _userRegistrationState.asStateFlow()

    private val validationEventChannel = Channel<ValidationState>()
    val validationRegistrationEvent = validationEventChannel.receiveAsFlow()

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
            is RegistrationEvent.NewPasswordChanged -> {
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
        val emailValidation = validateRegistrationForms.validateEmail(userRegistrationState.value.email)
        val nameValidation = validateRegistrationForms.validateName(userRegistrationState.value.name)
        val surnameValidation = validateRegistrationForms.validateSurname(userRegistrationState.value.surname)
        val passwordValidation = validateRegistrationForms.validatePassword(userRegistrationState.value.password)
        val newPasswordValidation = validateRegistrationForms.validateNewPassword(userRegistrationState.value.password, userRegistrationState.value.newPassword)
        val addressValidation = validateRegistrationForms.validateAddress(userRegistrationState.value.address)
        val zipCodeValidation = validateRegistrationForms.validateZipCode(userRegistrationState.value.zipCode)
        val countryValidation = validateRegistrationForms.validateCountry(userRegistrationState.value.country)
        val phoneNumberValidation = validateRegistrationForms.validatePhoneNumber(userRegistrationState.value.phoneNumber)
        val creditCardNumberValidation = validateRegistrationForms.validateCreditCardNumber(userRegistrationState.value.creditCardNumber)
        val expirationDateValidation = validateRegistrationForms.validateExpirationDate(userRegistrationState.value.expirationDate)
        val cvvValidation = validateRegistrationForms.validateCvv(userRegistrationState.value.cvv)
        val ibanValidation = validateRegistrationForms.validateIban(userRegistrationState.value.iban)

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

        if(hasErrorNotSeller && !userRegistrationState.value.isSeller){
            _userRegistrationState.value = _userRegistrationState.value.copy(
                emailErrorMsg = emailValidation.errorMessage,
                nameErrorMsg = nameValidation.errorMessage,
                surnameErrorMsg = surnameValidation.errorMessage,
                passwordErrorMsg = passwordValidation.errorMessage,
                newPasswordErrorMsg = newPasswordValidation.errorMessage
            )
            return
        }
        if (hasErrorSeller && userRegistrationState.value.isSeller){
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

    /*Update & Delete for RegistrationState*/

    private fun updateEmail(email: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            email = email
        )
    }

    private fun deleteEmail(){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            email = ""
        )
    }

    private fun updateName(name: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            name = name
        )
    }

    private fun deleteName(){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            name = ""
        )
    }

    private fun updateSurname(surname: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            surname = surname
        )
    }

    private fun deleteSurname(){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            surname = ""
        )
    }

    private fun updatePassword(password: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            password = password
        )
    }

    private fun updateNewPassword(newPassword: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            newPassword = newPassword
        )
    }

    private fun updateIsSeller(isSeller: Boolean){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            isSeller = isSeller
        )
    }

    private fun updateAddress(address: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            address = address
        )
    }

    private fun deleteAddress(){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            address = ""
        )
    }

    private fun updateZipCode(zipCode: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            zipCode = zipCode
        )
    }

    private fun deleteZipCode(){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            zipCode = ""
        )
    }

    private fun updateCountry(country: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            country = country
        )
    }

    private fun updatePhoneNumber(phoneNumber: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            phoneNumber = phoneNumber
        )
    }

    private fun deletePhoneNumber(){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            phoneNumber = ""
        )
    }

    private fun updateCreditCardNumber(creditCardNumber: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            creditCardNumber = creditCardNumber
        )
    }

    private fun deleteCreditCardNumber(){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            creditCardNumber = ""
        )
    }

    private fun updateExpirationDate(expirationDate: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            expirationDate = expirationDate
        )
    }

    private fun deleteExpirationDate(){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            expirationDate = ""
        )
    }

    private fun updateCvv(cvv: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            cvv = cvv
        )
    }

    private fun deleteCvv(){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            cvv = ""
        )
    }

    private fun updateIban(iban: String){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            iban = iban
        )
    }

    private fun deleteIban(){
        _userRegistrationState.value = _userRegistrationState.value.copy(
            iban = ""
        )
    }
}