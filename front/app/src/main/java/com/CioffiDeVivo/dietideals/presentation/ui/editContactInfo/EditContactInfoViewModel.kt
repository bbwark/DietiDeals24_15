package com.CioffiDeVivo.dietideals.presentation.ui.editContactInfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.mappers.toRequestModel
import com.CioffiDeVivo.dietideals.domain.models.Country
import com.CioffiDeVivo.dietideals.domain.validations.ValidateEditContactInfoForm
import com.CioffiDeVivo.dietideals.domain.validations.ValidationState
import com.CioffiDeVivo.dietideals.services.ApiService
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EditContactInfoViewModel(application: Application, private val validateEditContactInfoForm: ValidateEditContactInfoForm = ValidateEditContactInfoForm() ): AndroidViewModel(application) {

    private val _editContactInfoUiState = MutableStateFlow<EditContactInfoUiState>(EditContactInfoUiState.EditContactInfoParams())
    val editContactInfoUiState: StateFlow<EditContactInfoUiState> = _editContactInfoUiState.asStateFlow()
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
            val currentState = _editContactInfoUiState.value
            if(currentState is EditContactInfoUiState.EditContactInfoParams){
                viewModelScope.launch {
                    val requestUser = currentState.user.toRequestModel()
                    val updateUserResponse = ApiService.updateUser(requestUser)
                    //TODO handling response
                }
            }
        }
    }

    private fun validationBlock() : Boolean {
        val currentState = _editContactInfoUiState.value
        if(currentState is EditContactInfoUiState.EditContactInfoParams){
            try {
                val addressValidation = validateEditContactInfoForm.validateAddress(currentState.user.address)
                val zipCodeValidation = validateEditContactInfoForm.validateZipCode(currentState.user.zipCode)
                val phoneNumberValidation = validateEditContactInfoForm.validatePhoneNumber(currentState.user.phoneNumber)

                val hasError = listOf(
                    addressValidation,
                    zipCodeValidation,
                    phoneNumberValidation
                ).any { it.positiveResult }

                if(hasError){
                    _editContactInfoUiState.value = currentState.copy(
                        addressErrorMsg = addressValidation.errorMessage,
                        zipCodeErrorMsg = zipCodeValidation.errorMessage,
                        phoneNumberErrorMsg = phoneNumberValidation.errorMessage
                    )
                    return false
                }
                viewModelScope.launch {
                    validationEventChannel.send(ValidationState.Success)
                }
                return true
            } catch (e: Exception){
                _editContactInfoUiState.value = EditContactInfoUiState.Error
                return false
            }
        } else{
            return false
        }
    }

    //Update & Delete State

    private fun updateAddress(address: String){
        try {
            val currentState = _editContactInfoUiState.value
            if(currentState is EditContactInfoUiState.EditContactInfoParams){
                _editContactInfoUiState.value = currentState.copy(
                    user = currentState.user.copy(
                        address = address
                    )
                )
            }
        } catch (e: Exception){
            _editContactInfoUiState.value = EditContactInfoUiState.Error
        }
    }

    private fun deleteAddress() {
        updateAddress("")
    }

    private fun updateZipCode(zipCode: String){
        try {
            val currentState = _editContactInfoUiState.value
            if(currentState is EditContactInfoUiState.EditContactInfoParams){
                _editContactInfoUiState.value = currentState.copy(
                    user = currentState.user.copy(
                        zipCode = zipCode
                    )
                )
            }
        } catch (e: Exception){
            _editContactInfoUiState.value = EditContactInfoUiState.Error
        }
    }

    private fun deleteZipCode(){
        updateZipCode("")
    }

    private fun updateCountry(country: Country){
        try {
            val currentState = _editContactInfoUiState.value
            if(currentState is EditContactInfoUiState.EditContactInfoParams){
                _editContactInfoUiState.value = currentState.copy(
                    user = currentState.user.copy(
                        country = country
                    )
                )
            }
        } catch (e: Exception){
            _editContactInfoUiState.value = EditContactInfoUiState.Error
        }
    }

    private fun updatePhoneNumber(phoneNumber: String){
        try {
            val currentState = _editContactInfoUiState.value
            if(currentState is EditContactInfoUiState.EditContactInfoParams){
                _editContactInfoUiState.value = currentState.copy(
                    user = currentState.user.copy(
                        phoneNumber = phoneNumber
                    )
                )
            }
        } catch (e: Exception){
            _editContactInfoUiState.value = EditContactInfoUiState.Error
        }
    }

    private fun deletePhoneNumber(){
        updatePhoneNumber("")
    }

}