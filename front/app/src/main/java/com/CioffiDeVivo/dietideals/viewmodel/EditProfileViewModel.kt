package com.CioffiDeVivo.dietideals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.Events.EditProfileEvent
import com.CioffiDeVivo.dietideals.domain.use_case.ValidateEditProfileForm
import com.CioffiDeVivo.dietideals.domain.use_case.ValidationState
import com.CioffiDeVivo.dietideals.viewmodel.state.EditProfileState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EditProfileViewModel( private val validateEditProfileForm: ValidateEditProfileForm = ValidateEditProfileForm() ): ViewModel() {

    private val _userEditProfileState = MutableStateFlow(EditProfileState())
    val userEditProfileState: StateFlow<EditProfileState> = _userEditProfileState.asStateFlow()
    private val validationEventChannel = Channel<ValidationState>()
    val validationLogInEvent = validationEventChannel.receiveAsFlow()

    /*RESTAPI TO FILL THIS USERSTATE*/

    fun editProfileAction(editProfileEvent: EditProfileEvent){
        when(editProfileEvent){
            is EditProfileEvent.NameChanged -> {
                _userEditProfileState.value = _userEditProfileState.value.copy(
                    name = editProfileEvent.name
                )
            }
            is EditProfileEvent.SurnameChanged -> {
                _userEditProfileState.value = _userEditProfileState.value.copy(
                    surname = editProfileEvent.surname
                )
            }
            is EditProfileEvent.DescriptionChanged -> {
                _userEditProfileState.value = _userEditProfileState.value.copy(
                    description = editProfileEvent.description
                )
            }
            is EditProfileEvent.PasswordChanged -> {
                _userEditProfileState.value = _userEditProfileState.value.copy(
                    password = editProfileEvent.password
                )
            }
            is EditProfileEvent.NewPasswordChanged -> {
                _userEditProfileState.value = _userEditProfileState.value.copy(
                    newPassword = editProfileEvent.newPassword
                )
            }
            is EditProfileEvent.Submit -> {
                submitEditProfile()
            }
        }
    }

    private fun submitEditProfile(){
        val nameValidation = validateEditProfileForm.validateName(userEditProfileState.value.name)
        val surnameValidation = validateEditProfileForm.validateSurname(userEditProfileState.value.surname)
        val passwordValidation = validateEditProfileForm.validatePassword(userEditProfileState.value.password)
        val newPasswordValidation = validateEditProfileForm.validateNewPassword(userEditProfileState.value.password, userEditProfileState.value.newPassword)

        val hasError = listOf(
            nameValidation,
            surnameValidation,
            passwordValidation,
            newPasswordValidation
        ).any { it.positiveResult }

        if(hasError){
            _userEditProfileState.value = _userEditProfileState.value.copy(
                nameErrorMsg = nameValidation.errorMessage,
                surnameErrorMsg = surnameValidation.errorMessage,
                passwordErrorMsg = passwordValidation.errorMessage,
                newPasswordErrorMsg = newPasswordValidation.errorMessage
            )
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationState.Success)
        }
    }

    //Update & Delete State

    fun updateName(name: String){
        _userEditProfileState.value = _userEditProfileState.value.copy(
            name = name
        )
    }

    fun deleteName(){
        _userEditProfileState.value = _userEditProfileState.value.copy(
            name = ""
        )
    }

    fun updateSurname(surname: String){
        _userEditProfileState.value = _userEditProfileState.value.copy(
            surname = surname
        )
    }

    fun deleteSurname(){
        _userEditProfileState.value = _userEditProfileState.value.copy(
            surname = ""
        )
    }

    fun updateDescription(description: String){
        _userEditProfileState.value = _userEditProfileState.value.copy(
            description = description
        )
    }

    fun deleteDescription(){
        _userEditProfileState.value = _userEditProfileState.value.copy(
            description = ""
        )
    }

    fun updatePassword(password: String){
        _userEditProfileState.value = _userEditProfileState.value.copy(
            password = password
        )
    }

    fun updateNewPassword(newPassword: String){
        _userEditProfileState.value = _userEditProfileState.value.copy(
            newPassword = newPassword
        )
    }

}