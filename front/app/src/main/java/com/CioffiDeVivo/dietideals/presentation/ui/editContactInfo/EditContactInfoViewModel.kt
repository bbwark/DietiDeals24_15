package com.CioffiDeVivo.dietideals.presentation.ui.editContactInfo

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.mappers.toDataModel
import com.CioffiDeVivo.dietideals.data.mappers.toRequestModel
import com.CioffiDeVivo.dietideals.data.models.Country
import com.CioffiDeVivo.dietideals.data.requestModels.User
import com.CioffiDeVivo.dietideals.data.validations.ValidateEditContactInfoForm
import com.CioffiDeVivo.dietideals.data.validations.ValidationState
import com.CioffiDeVivo.dietideals.services.ApiService
import com.google.gson.Gson
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
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

    private val sharedPreferences by lazy {
        application.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    }

    fun editContactInfoAction(editContactInfoEvents: EditContactInfoEvents){
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
        if (true) {
            val currentState = _editContactInfoUiState.value
            if(currentState is EditContactInfoUiState.EditContactInfoParams){
                _editContactInfoUiState.value = EditContactInfoUiState.Loading
                viewModelScope.launch {
                    _editContactInfoUiState.value = try {
                        val requestUser = currentState.user.toRequestModel()
                        val updateUserResponse = ApiService.updateUser(requestUser)
                        if(updateUserResponse.status.isSuccess()){
                            EditContactInfoUiState.Success
                        } else{
                            Log.e("Error", "Error: Error on Update User!")
                            EditContactInfoUiState.Error
                        }
                    } catch(e: Exception){
                        Log.e("Error", "Error: ${e.message}")
                        EditContactInfoUiState.Error
                    }
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

    fun getUserInfo(){
        val userId = sharedPreferences.getString("userId", null)
        if(userId != null){
            viewModelScope.launch {
                setLoadingState()
                _editContactInfoUiState.value = try {
                    val userInfoResponse = ApiService.getUser(userId)
                    if(userInfoResponse.status.isSuccess()){
                        val user = Gson().fromJson(userInfoResponse.bodyAsText(), User::class.java).toDataModel()
                        EditContactInfoUiState.EditContactInfoParams(user = user)
                    } else{
                        Log.e("Error", "Error: Error on GET User!")
                        EditContactInfoUiState.Error
                    }
                } catch (e: Exception){
                    Log.e("Error", "Error: ${e.message}")
                    EditContactInfoUiState.Error
                }
            }
        }
    }

    private fun setLoadingState(){
        _editContactInfoUiState.value = EditContactInfoUiState.Loading
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