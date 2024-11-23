package com.CioffiDeVivo.dietideals.presentation.ui.editContactInfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.data.repositories.UserRepository
import com.CioffiDeVivo.dietideals.data.validations.ValidateEditContactInfoForm
import com.CioffiDeVivo.dietideals.data.validations.ValidationState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EditContactInfoViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userRepository: UserRepository,
    private val validateEditContactInfoForm: ValidateEditContactInfoForm = ValidateEditContactInfoForm()
): ViewModel() {

    private val _editContactInfoUiState = MutableStateFlow<EditContactInfoUiState>(EditContactInfoUiState.EditContactInfoParams())
    val editContactInfoUiState: StateFlow<EditContactInfoUiState> = _editContactInfoUiState.asStateFlow()
    private val validationEventChannel = Channel<ValidationState>()
    val validationEditContactInfoEvents = validationEventChannel.receiveAsFlow()

    fun getUserInfo(){
        _editContactInfoUiState.value = EditContactInfoUiState.Loading
        viewModelScope.launch {
            _editContactInfoUiState.value = try {
                val userId = userPreferencesRepository.getUserIdPreference()
                val user = userRepository.getUser(userId)
                EditContactInfoUiState.EditContactInfoParams(user = user)
            } catch (e: Exception){
                Log.e("Error", "Error: ${e.message}")
                EditContactInfoUiState.Error
            }
        }
    }

    fun editContactInfoAction(editContactInfoEvents: EditContactInfoEvents){
        try {
            val currentState = _editContactInfoUiState.value
            if(currentState is EditContactInfoUiState.EditContactInfoParams){
                when(editContactInfoEvents){
                    is EditContactInfoEvents.AddressChanged -> {
                        _editContactInfoUiState.value = currentState.copy(user = currentState.user.copy(address = editContactInfoEvents.address))
                    }
                    is EditContactInfoEvents.AddressDeleted -> {
                        _editContactInfoUiState.value = currentState.copy(user = currentState.user.copy(address = ""))
                    }
                    is EditContactInfoEvents.ZipCodeChanged -> {
                        _editContactInfoUiState.value = currentState.copy(user = currentState.user.copy(zipCode = editContactInfoEvents.zipCode))
                    }
                    is EditContactInfoEvents.ZipCodeDeleted -> {
                        _editContactInfoUiState.value = currentState.copy(user = currentState.user.copy(zipCode = ""))
                    }
                    is EditContactInfoEvents.CountryChanged -> {
                        _editContactInfoUiState.value = currentState.copy(user = currentState.user.copy(country = editContactInfoEvents.country))
                    }
                    is EditContactInfoEvents.PhoneNumberChanged -> {
                        _editContactInfoUiState.value = currentState.copy(user = currentState.user.copy(phoneNumber = editContactInfoEvents.phoneNumber))
                    }
                    is EditContactInfoEvents.PhoneNumberDeleted -> {
                        _editContactInfoUiState.value = currentState.copy(user = currentState.user.copy(phoneNumber = ""))
                    }
                    is EditContactInfoEvents.Submit -> {
                        submitEditContactInfo()
                    }
                }
            }
        } catch (e: Exception){
            _editContactInfoUiState.value = EditContactInfoUiState.Error
        }
    }

    private fun submitEditContactInfo(){
        if (validationBlock()) {
            val currentState = _editContactInfoUiState.value
            if(currentState is EditContactInfoUiState.EditContactInfoParams){
                _editContactInfoUiState.value = EditContactInfoUiState.Loading
                viewModelScope.launch {
                    _editContactInfoUiState.value = try {
                        val updatedUser = currentState.user
                        val userId = userPreferencesRepository.getUserIdPreference()
                        userRepository.updateUser(userId, updatedUser)
                        EditContactInfoUiState.Success
                    } catch(e: Exception){
                        Log.e("Error", "Error: ${e.message}")
                        EditContactInfoUiState.Error
                    }
                }
            }
        }
    }

    private fun validationBlock(): Boolean {
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

}