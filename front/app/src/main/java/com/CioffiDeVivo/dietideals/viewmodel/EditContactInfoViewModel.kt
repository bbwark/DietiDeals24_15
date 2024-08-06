package com.CioffiDeVivo.dietideals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.Events.EditContactInfoEvents
import com.CioffiDeVivo.dietideals.domain.use_case.ValidateEditContactInfoForm
import com.CioffiDeVivo.dietideals.domain.use_case.ValidationState
import com.CioffiDeVivo.dietideals.viewmodel.state.EditContactInfoState
import com.CioffiDeVivo.dietideals.viewmodel.state.RegistrationState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EditContactInfoViewModel( private val validateEditContactInfoForm: ValidateEditContactInfoForm = ValidateEditContactInfoForm() ): ViewModel() {

    private val _userEditContactInfoState = MutableStateFlow(EditContactInfoState())
    val userEditContactInfoState: StateFlow<EditContactInfoState> = _userEditContactInfoState.asStateFlow()
    private val validationEventChannel = Channel<ValidationState>()
    val validationEditContactInfoEvents = validationEventChannel.receiveAsFlow()

    fun editProfileAction(editContactInfoEvents: EditContactInfoEvents){
        when(editContactInfoEvents){
            is EditContactInfoEvents.AddressChanged -> {
                updateAddress(editContactInfoEvents.address)
            }
            is EditContactInfoEvents.AddressDeleted -> {
                deleteAddress()
            }
            is EditContactInfoEvents.ZipCodeChanged -> {
                updateZipCode(editContactInfoEvents.zipcode)
            }
            is EditContactInfoEvents.ZipCodeDeleted -> {
                deleteZipCode()
            }
            is EditContactInfoEvents.CountryChanged -> {
                updateCountry(editContactInfoEvents.country)
            }
            is EditContactInfoEvents.PhoneNumberChanged -> {
                updatePhoneNumber(editContactInfoEvents.phoneNumber)
            }
            is EditContactInfoEvents.PhoneNumberDeleted -> {
                deletePhoneNumber()
            }
            is EditContactInfoEvents.Submit -> {
                submitEditContactInfo()
            }
        }
    }

    private fun submitEditContactInfo(){
        val addressValidation = validateEditContactInfoForm.validateAddress(userEditContactInfoState.value.address)
        val zipCodeValidation = validateEditContactInfoForm.validateZipCode(userEditContactInfoState.value.zipCode)
        val countryValidation = validateEditContactInfoForm.validateCountry(userEditContactInfoState.value.country)
        val phoneNumberValidation = validateEditContactInfoForm.validatePhoneNumber(userEditContactInfoState.value.phoneNumber)

        val hasError = listOf(
            addressValidation,
            zipCodeValidation,
            countryValidation,
            phoneNumberValidation
        ).any { it.positiveResult }

        if(hasError){
            _userEditContactInfoState.value = _userEditContactInfoState.value.copy(
                addressErrorMsg = addressValidation.errorMessage,
                zipCodeErrorMsg = zipCodeValidation.errorMessage,
                countryErrorMsg = countryValidation.errorMessage,
                phoneNumberErrorMsg = phoneNumberValidation.errorMessage
            )
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationState.Success)
        }
    }

    //Update & Delete State

    fun updateAddress(address: String){
        _userEditContactInfoState.value = _userEditContactInfoState.value.copy(
            address = address
        )
    }

    fun deleteAddress(){
        _userEditContactInfoState.value = _userEditContactInfoState.value.copy(
            address = ""
        )
    }

    fun updateZipCode(zipCode: String){
        _userEditContactInfoState.value = _userEditContactInfoState.value.copy(
            zipCode = zipCode
        )
    }

    fun deleteZipCode(){
        _userEditContactInfoState.value = _userEditContactInfoState.value.copy(
            zipCode = ""
        )
    }

    fun updateCountry(country: String){
        _userEditContactInfoState.value = _userEditContactInfoState.value.copy(
            country = country
        )
    }

    fun updatePhoneNumber(phoneNumber: String){
        _userEditContactInfoState.value = _userEditContactInfoState.value.copy(
            phoneNumber = phoneNumber
        )
    }

    fun deletePhoneNumber(){
        _userEditContactInfoState.value = _userEditContactInfoState.value.copy(
            phoneNumber = ""
        )
    }

}