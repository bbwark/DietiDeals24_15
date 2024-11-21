package com.CioffiDeVivo.dietideals.presentation.ui.editProfile

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.requestModels.User
import com.CioffiDeVivo.dietideals.data.mappers.toDataModel
import com.CioffiDeVivo.dietideals.data.mappers.toRequestModel
import com.CioffiDeVivo.dietideals.data.validations.ValidateEditProfileForm
import com.CioffiDeVivo.dietideals.data.validations.ValidationState
import com.CioffiDeVivo.dietideals.presentation.ui.editContactInfo.EditContactInfoUiState
import com.CioffiDeVivo.dietideals.services.ApiService
import com.CioffiDeVivo.dietideals.services.AuthService
import com.CioffiDeVivo.dietideals.utils.EncryptedPreferencesManager
import com.CioffiDeVivo.dietideals.utils.extractKeyFromJson
import com.google.gson.Gson
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EditProfileViewModel(application: Application, private val validateEditProfileForm: ValidateEditProfileForm = ValidateEditProfileForm() ): AndroidViewModel(application) {

    private val _editUiProfileState = MutableStateFlow<EditProfileUiState>(EditProfileUiState.EditProfileParams())
    val editUiProfileState: StateFlow<EditProfileUiState> = _editUiProfileState.asStateFlow()
    private val validationEventChannel = Channel<ValidationState>()
    val validationEditProfileEvent = validationEventChannel.receiveAsFlow()

    private val sharedPreferences by lazy {
        application.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    }

    fun editProfileAction(editProfileEvent: EditProfileEvent){
        when(editProfileEvent){
            is EditProfileEvent.EmailChanged -> {
                updateEmail(editProfileEvent.email)
            }
            is EditProfileEvent.EmailDeleted -> {
                deleteEmail()
            }
            is EditProfileEvent.NameChanged -> {
                updateName(editProfileEvent.name)
            }
            is EditProfileEvent.NameDeleted -> {
                deleteName()
            }
            is EditProfileEvent.SurnameChanged -> {
                updateSurname(editProfileEvent.surname)
            }
            is EditProfileEvent.SurnameDeleted -> {
                deleteSurname()
            }
            is EditProfileEvent.DescriptionChanged -> {
                updateDescription(editProfileEvent.description)
            }
            is EditProfileEvent.DescriptionDeleted -> {
                deleteDescription()
            }
            is EditProfileEvent.PasswordChanged -> {
                updatePassword(editProfileEvent.password)
            }
            is EditProfileEvent.NewPasswordChanged -> {
                updateRetypePassword(editProfileEvent.newPassword)
            }
            is EditProfileEvent.Submit -> {
                submitEditProfile()
            }
        }
    }

    private fun submitEditProfile(){
        if (true) {
            val currentState = _editUiProfileState.value
            if(currentState is EditProfileUiState.EditProfileParams){
                _editUiProfileState.value = EditProfileUiState.Loading
                viewModelScope.launch {
                    _editUiProfileState.value = try {
                        val requestUser = currentState.user.toRequestModel()
                        val updateUserResponse = ApiService.updateUser(requestUser)
                        if(updateUserResponse.status.isSuccess()){
                            EditProfileUiState.Success
                        } else{
                            Log.e("Error", "Error: Error on Update User!")
                            EditProfileUiState.Error
                        }
                        EditProfileUiState.Success
                    } catch (e: Exception){
                        EditProfileUiState.Error
                    }
                }
            }
        }
    }

    private fun validationBlock() : Boolean {
        val currentState = _editUiProfileState.value
        if(currentState is EditProfileUiState.EditProfileParams){
            try {
                val emailValidation = validateEditProfileForm.validateEmail(currentState.user.email)
                val nameValidation = validateEditProfileForm.validateName(currentState.user.name)
                val surnameValidation = validateEditProfileForm.validateSurname(currentState.user.surname)
                val passwordValidation = validateEditProfileForm.validatePassword(currentState.user.password)
                val newPasswordValidation = validateEditProfileForm.validateRetypePassword(currentState.user.password, currentState.retypePassword)

                val hasError = listOf(
                    emailValidation,
                    nameValidation,
                    surnameValidation,
                    passwordValidation,
                    newPasswordValidation
                ).any { !it.positiveResult }

                if(hasError){
                    _editUiProfileState.value = currentState.copy(
                        emailErrorMsg = emailValidation.errorMessage,
                        nameErrorMsg = nameValidation.errorMessage,
                        surnameErrorMsg = surnameValidation.errorMessage,
                        passwordErrorMsg = passwordValidation.errorMessage,
                        retypePasswordErrorMsg = newPasswordValidation.errorMessage
                    )
                    return false
                }
                viewModelScope.launch {
                    validationEventChannel.send(ValidationState.Success)
                }
                return true
            } catch (e: Exception){
                _editUiProfileState.value = EditProfileUiState.Error
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
                _editUiProfileState.value = try {
                    val userInfoResponse = ApiService.getUser(userId)
                    if(userInfoResponse.status.isSuccess()){
                        val user = Gson().fromJson(userInfoResponse.bodyAsText(), User::class.java).toDataModel()
                        EditProfileUiState.EditProfileParams(user = user)
                    } else{
                        Log.e("Error", "Error: Error on GET User!")
                        EditProfileUiState.Error
                    }
                } catch (e: Exception){
                    Log.e("Error", "Error: ${e.message}")
                    EditProfileUiState.Error
                }
            }
        }
    }

    private fun setLoadingState(){
        _editUiProfileState.value = EditProfileUiState.Loading
    }

    //Update & Delete State

    private fun updateEmail(email: String){
        try {
            val currentState = _editUiProfileState.value
            if(currentState is EditProfileUiState.EditProfileParams){
                _editUiProfileState.value = currentState.copy(
                    user = currentState.user.copy(
                        email = email
                    )
                )
            }
        } catch (e: Exception){
            _editUiProfileState.value = EditProfileUiState.Error
        }
    }

    private fun deleteEmail(){
        updateEmail("")
    }

    private fun updateName(name: String){
        try {
            val currentState = _editUiProfileState.value
            if(currentState is EditProfileUiState.EditProfileParams){
                _editUiProfileState.value = currentState.copy(
                    user = currentState.user.copy(
                        name = name
                    )
                )
            }
        } catch (e: Exception){
            _editUiProfileState.value = EditProfileUiState.Error
        }
    }

    private fun deleteName(){
        updateName("")
    }

    private fun updateSurname(surname: String){
        try {
            val currentState = _editUiProfileState.value
            if(currentState is EditProfileUiState.EditProfileParams){
                _editUiProfileState.value = currentState.copy(
                    user = currentState.user.copy(
                        surname = surname
                    )
                )
            }
        } catch (e: Exception){
            _editUiProfileState.value = EditProfileUiState.Error
        }
    }

    private fun deleteSurname(){
        updateSurname("")
    }

    private fun updateDescription(description: String){
        try {
            val currentState = _editUiProfileState.value
            if(currentState is EditProfileUiState.EditProfileParams){
                _editUiProfileState.value = currentState.copy(
                    user = currentState.user.copy(
                        bio = description
                    )
                )
            }
        } catch (e: Exception){
            _editUiProfileState.value = EditProfileUiState.Error
        }
    }

    private fun deleteDescription(){
        updateDescription("")
    }

    private fun updatePassword(password: String){
        try {
            val currentState = _editUiProfileState.value
            if(currentState is EditProfileUiState.EditProfileParams){
                _editUiProfileState.value = currentState.copy(
                    user = currentState.user.copy(
                        password = password
                    )
                )
            }
        } catch (e: Exception){
            _editUiProfileState.value = EditProfileUiState.Error
        }
    }

    private fun updateRetypePassword(retypePassword: String){
        try {
            val currentState = _editUiProfileState.value
            if(currentState is EditProfileUiState.EditProfileParams){
                _editUiProfileState.value = currentState.copy(
                    retypePassword = retypePassword
                )
            }
        } catch (e: Exception){
            _editUiProfileState.value = EditProfileUiState.Error
        }
    }

}