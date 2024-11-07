package com.CioffiDeVivo.dietideals.presentation.ui.editProfile

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.mappers.toRequestModel
import com.CioffiDeVivo.dietideals.domain.validations.ValidateEditProfileForm
import com.CioffiDeVivo.dietideals.domain.validations.ValidationState
import com.CioffiDeVivo.dietideals.services.ApiService
import com.CioffiDeVivo.dietideals.services.AuthService
import com.CioffiDeVivo.dietideals.utils.EncryptedPreferencesManager
import com.CioffiDeVivo.dietideals.utils.extractKeyFromJson
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
        if (validationBlock()) {
            val currentState = _editUiProfileState.value
            if(currentState is EditProfileUiState.EditProfileParams){
                _editUiProfileState.value = EditProfileUiState.Loading
                viewModelScope.launch {
                    _editUiProfileState.value = try {
                        val requestUser = currentState.user.toRequestModel()
                        val updateUserResponse = ApiService.updateUser(requestUser)
                        if (updateUserResponse.status.isSuccess()) {
                            val encryptedSharedPreferences = EncryptedPreferencesManager.getEncryptedPreferences()
                            encryptedSharedPreferences.edit().apply {
                                putString("email", currentState.user.email)
                                putString("password", currentState.user.password)
                                apply()
                            }
                            val userId = extractKeyFromJson(updateUserResponse.bodyAsText(), "id")
                            val sharedPreferences = getApplication<Application>().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
                            sharedPreferences.edit().apply {
                                putString("userId", userId)
                                apply()
                            }
                            val loginResponse = AuthService.loginUser(currentState.user.email,  currentState.user.password)
                            val sessionToken = extractKeyFromJson(loginResponse.bodyAsText(), "jwt")
                            if (sessionToken.isNotEmpty()) {
                                ApiService.initialize(sessionToken, getApplication<Application>().applicationContext)
                            }
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