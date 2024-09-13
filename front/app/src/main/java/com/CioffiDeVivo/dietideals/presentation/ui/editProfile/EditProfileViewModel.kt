package com.CioffiDeVivo.dietideals.presentation.ui.editProfile

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.models.User
import com.CioffiDeVivo.dietideals.domain.mappers.toRequestModel
import com.CioffiDeVivo.dietideals.domain.validations.ValidateEditProfileForm
import com.CioffiDeVivo.dietideals.domain.validations.ValidationState
import com.CioffiDeVivo.dietideals.utils.ApiService
import com.CioffiDeVivo.dietideals.utils.AuthService
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

    private val _userEditProfileState = MutableStateFlow(EditProfileState())
    val userEditProfileState: StateFlow<EditProfileState> = _userEditProfileState.asStateFlow()
    private val validationEventChannel = Channel<ValidationState>()
    val validationLogInEvent = validationEventChannel.receiveAsFlow()

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
            viewModelScope.launch {
                val requestUser = _userEditProfileState.value.user.toRequestModel()
                val updateUserResponse = ApiService.updateUser(requestUser)
                if (updateUserResponse.status.isSuccess()) {
                    val encryptedSharedPreferences = EncryptedPreferencesManager.getEncryptedPreferences()
                    encryptedSharedPreferences.edit().apply {
                        putString("email", _userEditProfileState.value.user.email)
                        putString("password", _userEditProfileState.value.user.password)
                        apply()
                    }
                    val userId = extractKeyFromJson(updateUserResponse.bodyAsText(), "id")
                    val sharedPreferences = getApplication<Application>().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
                    sharedPreferences.edit().apply {
                        putString("userId", userId)
                        apply()
                    }
                    val loginResponse = AuthService.loginUser(_userEditProfileState.value.user.email,  _userEditProfileState.value.user.password)
                    val sessionToken = extractKeyFromJson(loginResponse.bodyAsText(), "jwt")
                    if (sessionToken.isNotEmpty()) {
                        ApiService.initialize(sessionToken, getApplication<Application>().applicationContext)
                    }
                }
            }
        }
    }

    private fun validationBlock() : Boolean {
        val nameValidation = validateEditProfileForm.validateName(userEditProfileState.value.user.name)
        val surnameValidation = validateEditProfileForm.validateSurname(userEditProfileState.value.user.surname)
        val passwordValidation = validateEditProfileForm.validatePassword(userEditProfileState.value.user.password)
        val newPasswordValidation = validateEditProfileForm.validateRetypePassword(userEditProfileState.value.user.password, userEditProfileState.value.retypePassword)

        val hasError = listOf(
            nameValidation,
            surnameValidation,
            passwordValidation,
            newPasswordValidation
        ).any { !it.positiveResult }

        if(hasError){
            _userEditProfileState.value = _userEditProfileState.value.copy(
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
    }

    //Update & Delete State

    fun setUser(user: User) {
        _userEditProfileState.value = _userEditProfileState.value.copy(
            user = user
        )
    }

    private fun updateEmail(email: String){
        _userEditProfileState.value = _userEditProfileState.value.copy(
            user = _userEditProfileState.value.user.copy(
                email = email
            )
        )
    }

    private fun deleteEmail(){
        updateEmail("")
    }

    private fun updateName(name: String){
        _userEditProfileState.value = _userEditProfileState.value.copy(
            user = _userEditProfileState.value.user.copy(
                name = name
            )
        )
    }

    private fun deleteName(){
        updateName("")
    }

    private fun updateSurname(surname: String){
        _userEditProfileState.value = _userEditProfileState.value.copy(
            user = _userEditProfileState.value.user.copy(
                surname = surname
            )
        )
    }

    private fun deleteSurname(){
        updateSurname("")
    }

    private fun updateDescription(description: String){
        _userEditProfileState.value = _userEditProfileState.value.copy(
            user = _userEditProfileState.value.user.copy(
                bio = description
            )
        )
    }

    private fun deleteDescription(){
        updateDescription("")
    }

    private fun updatePassword(password: String){
        _userEditProfileState.value = _userEditProfileState.value.copy(
            user = _userEditProfileState.value.user.copy(
                password = password
            )
        )
    }

    private fun updateRetypePassword(retypePassword: String){
        _userEditProfileState.value = _userEditProfileState.value.copy(
            retypePassword = retypePassword
        )
    }

}