package com.CioffiDeVivo.dietideals.presentation.ui.editContactInfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.mappers.toRequestModel
import com.CioffiDeVivo.dietideals.domain.validations.ValidateEditContactInfoForm
import com.CioffiDeVivo.dietideals.domain.validations.ValidationState
import com.CioffiDeVivo.dietideals.utils.ApiService
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EditContactInfoViewModel(application: Application, private val validateEditContactInfoForm: ValidateEditContactInfoForm = ValidateEditContactInfoForm() ): AndroidViewModel(application) {

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
        if (validationBlock()) {
            viewModelScope.launch {
                val requestUser = _userEditContactInfoState.value.user.toRequestModel()
                val updateUserResponse = ApiService.updateUser(requestUser)
                //TODO handling response
            }
        }
    }

    private fun validationBlock() : Boolean {
        val addressValidation = validateEditContactInfoForm.validateAddress(userEditContactInfoState.value.user.address)
        val zipCodeValidation = validateEditContactInfoForm.validateZipCode(userEditContactInfoState.value.user.zipCode)
        val countryValidation = validateEditContactInfoForm.validateCountry(userEditContactInfoState.value.user.country)
        val phoneNumberValidation = validateEditContactInfoForm.validatePhoneNumber(userEditContactInfoState.value.user.phoneNumber)

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
            return false
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationState.Success)
        }
        return true
    }

    //Update & Delete State

    private fun updateAddress(address: String){
        _userEditContactInfoState.value = _userEditContactInfoState.value.copy(
            user = _userEditContactInfoState.value.user.copy(
                address = address
            )
        )
    }

    private fun deleteAddress() {
        updateAddress("")
    }

    private fun updateZipCode(zipCode: String){
        _userEditContactInfoState.value = _userEditContactInfoState.value.copy(
            user = _userEditContactInfoState.value.user.copy(
                zipCode = zipCode
            )
        )
    }

    private fun deleteZipCode(){
        updateZipCode("")
    }

    private fun updateCountry(country: String){
        _userEditContactInfoState.value = _userEditContactInfoState.value.copy(
            user = _userEditContactInfoState.value.user.copy(
                country = country
            )
        )
    }

    private fun updatePhoneNumber(phoneNumber: String){
        _userEditContactInfoState.value = _userEditContactInfoState.value.copy(
            user = _userEditContactInfoState.value.user.copy(
                phoneNumber = phoneNumber
            )
        )
    }

    private fun deletePhoneNumber(){
        updatePhoneNumber("")
    }

}