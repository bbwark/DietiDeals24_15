package com.CioffiDeVivo.dietideals.presentation.ui.editProfile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.data.repositories.UserRepository
import com.CioffiDeVivo.dietideals.data.validations.ValidateEditProfileForm
import com.CioffiDeVivo.dietideals.data.validations.ValidationState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userRepository: UserRepository,
    private val validateEditProfileForm: ValidateEditProfileForm = ValidateEditProfileForm()
): ViewModel() {

    private val _editProfileUiState = MutableStateFlow<EditProfileUiState>(EditProfileUiState.Loading)
    val editProfileUiState: StateFlow<EditProfileUiState> = _editProfileUiState.asStateFlow()
    private val validationEventChannel = Channel<ValidationState>()
    val validationEditProfileEvent = validationEventChannel.receiveAsFlow()

    fun getUserInfo(){
        _editProfileUiState.value = EditProfileUiState.Loading
        viewModelScope.launch {
            _editProfileUiState.value = try {
                val userId = userPreferencesRepository.getUserIdPreference()
                val user = userRepository.getUser(userId)
                val updatedUser = user.copy(password = "")
                EditProfileUiState.EditProfileParams(user = updatedUser)
            } catch (e: Exception){
                Log.e("Error", "Error: ${e.message}")
                EditProfileUiState.Error
            }
        }
    }

    fun editProfileAction(editProfileEvent: EditProfileEvent){
        try {
            val currentState = _editProfileUiState.value
            if(currentState is EditProfileUiState.EditProfileParams){
                when(editProfileEvent){
                    is EditProfileEvent.EmailChanged -> {
                        _editProfileUiState.value = currentState.copy(user = currentState.user.copy(email = editProfileEvent.email))
                    }
                    is EditProfileEvent.EmailDeleted -> {
                        _editProfileUiState.value = currentState.copy(user = currentState.user.copy(email = ""))
                    }
                    is EditProfileEvent.NameChanged -> {
                        _editProfileUiState.value = currentState.copy(user = currentState.user.copy(name = editProfileEvent.name))
                    }
                    is EditProfileEvent.NameDeleted -> {
                        _editProfileUiState.value = currentState.copy(user = currentState.user.copy(name = ""))
                    }
                    is EditProfileEvent.DescriptionChanged -> {
                        _editProfileUiState.value = currentState.copy(user = currentState.user.copy(bio = editProfileEvent.description))
                    }
                    is EditProfileEvent.DescriptionDeleted -> {
                        _editProfileUiState.value = currentState.copy(user = currentState.user.copy(bio = ""))
                    }
                    is EditProfileEvent.PasswordChanged -> {
                        _editProfileUiState.value = currentState.copy(user = currentState.user.copy(password = editProfileEvent.password))
                    }
                    is EditProfileEvent.NewPasswordChanged -> {
                        _editProfileUiState.value = currentState.copy(retypePassword = editProfileEvent.newPassword)
                    }
                    is EditProfileEvent.Submit -> {
                        submitEditProfile()
                    }
                }
            }
        } catch (e: Exception){
            _editProfileUiState.value = EditProfileUiState.Error
        }
    }

    private fun submitEditProfile(){
        if (validationBlock()) {
            val currentState = _editProfileUiState.value
            if(currentState is EditProfileUiState.EditProfileParams){
                _editProfileUiState.value = EditProfileUiState.Loading
                viewModelScope.launch {
                    _editProfileUiState.value = try {
                        val updatedUser = currentState.user
                        val userId = userPreferencesRepository.getUserIdPreference()
                        val updatedUserResponse = userRepository.updateUser(userId ,updatedUser)
                        userPreferencesRepository.saveName(updatedUserResponse.name)
                        userPreferencesRepository.saveEmail(updatedUserResponse.email)
                        EditProfileUiState.Success
                    } catch (e: Exception){
                        EditProfileUiState.Error
                    }
                }
            }
        }
    }

    private fun validationBlock(): Boolean {
        val currentState = _editProfileUiState.value
        if(currentState is EditProfileUiState.EditProfileParams){
            try {
                val emailValidation = validateEditProfileForm.validateEmail(currentState.user.email)
                val nameValidation = validateEditProfileForm.validateName(currentState.user.name)
                val passwordValidation = validateEditProfileForm.validatePassword(currentState.user.password)
                val newPasswordValidation = validateEditProfileForm.validateRetypePassword(currentState.user.password, currentState.retypePassword)

                val hasError = listOf(
                    emailValidation,
                    nameValidation,
                    passwordValidation,
                    newPasswordValidation
                ).any { !it.positiveResult }

                if(hasError){
                    _editProfileUiState.value = currentState.copy(
                        emailErrorMsg = emailValidation.errorMessage,
                        nameErrorMsg = nameValidation.errorMessage,
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
                _editProfileUiState.value = EditProfileUiState.Error
                return false
            }
        } else{
            return false
        }
    }

}