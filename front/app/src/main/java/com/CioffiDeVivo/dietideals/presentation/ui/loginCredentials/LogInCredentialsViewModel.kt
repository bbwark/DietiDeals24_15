package com.CioffiDeVivo.dietideals.presentation.ui.loginCredentials

import android.app.Application
import android.content.Context
import android.text.Spannable.Factory
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.CioffiDeVivo.dietideals.DietiDealsApplication
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.data.UserRepository
import com.CioffiDeVivo.dietideals.domain.mappers.toDataModel
import com.CioffiDeVivo.dietideals.domain.models.User
import com.CioffiDeVivo.dietideals.domain.requestModels.LogInRequest
import com.CioffiDeVivo.dietideals.services.ApiService
import com.CioffiDeVivo.dietideals.services.AuthService
import com.CioffiDeVivo.dietideals.domain.validations.ValidateLogInForm
import com.CioffiDeVivo.dietideals.domain.validations.ValidationState
import com.CioffiDeVivo.dietideals.utils.EncryptedPreferencesManager
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LogInCredentialsViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userRepository: UserRepository,
    private val validateLogInForms: ValidateLogInForm = ValidateLogInForm()
) : ViewModel() {

    private val _logInCredentialsUiState = MutableStateFlow<LogInCredentialsUiState>(LogInCredentialsUiState.LogInParams())
    val logInCredentialsUiState: StateFlow<LogInCredentialsUiState> = _logInCredentialsUiState.asStateFlow()

    private val validationEventChannel = Channel<ValidationState>()
    val validationLogInEvent = validationEventChannel.receiveAsFlow()

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DietiDealsApplication)
                LogInCredentialsViewModel(application.userPreferencesRepository, application.container.userRepository)
            }
        }
    }

    fun loginOnAction(loginEvent: LoginEvent) {
        try {
            val currentState = _logInCredentialsUiState.value
            if(currentState is LogInCredentialsUiState.LogInParams){
                when (loginEvent) {
                    is LoginEvent.EmailChanged -> {
                        _logInCredentialsUiState.value = currentState.copy(email = loginEvent.email)
                    }
                    is LoginEvent.EmailDeleted -> {
                        _logInCredentialsUiState.value = currentState.copy(email = "")
                    }
                    is LoginEvent.PasswordChanged -> {
                        _logInCredentialsUiState.value = currentState.copy(password = loginEvent.password)
                    }
                    is LoginEvent.Submit -> {
                        submitLogIn()
                    }
                }
            }
        } catch (e: Exception){
            _logInCredentialsUiState.value = LogInCredentialsUiState.Error
        }
    }

    private fun submitLogIn() {
        if (validationBlock()) {
            val currentState = _logInCredentialsUiState.value
            if(currentState is LogInCredentialsUiState.LogInParams){
                _logInCredentialsUiState.value = LogInCredentialsUiState.Loading
                viewModelScope.launch {
                    _logInCredentialsUiState.value = try {
                        val logInRequest = LogInRequest(currentState.email, currentState.password)
                        val userLogin = userRepository.login(logInRequest)
                        userPreferencesRepository.saveUserId(userLogin.id)
                        LogInCredentialsUiState.Success
                    } catch (e: Exception) {
                        Log.e("Error", "Error: ${e.message}")
                        LogInCredentialsUiState.Error
                    }
                }
            }
        }
    }

    private fun validationBlock() : Boolean {
        val currentState = _logInCredentialsUiState.value
        if(currentState is LogInCredentialsUiState.LogInParams){
            try {
                val emailValidation = validateLogInForms.validateEmail(currentState.email)
                val passwordValidation = validateLogInForms.validatePassword(currentState.password)

                val hasError = listOf(
                    emailValidation,
                    passwordValidation,
                ).any { !it.positiveResult }

                if (hasError) {
                    _logInCredentialsUiState.value = currentState.copy(
                        emailErrorMsg = emailValidation.errorMessage,
                        passwordErrorMsg = passwordValidation.errorMessage,
                    )
                    return false
                }
                viewModelScope.launch {
                    validationEventChannel.send(ValidationState.Success)
                }
                return true
            } catch (e: Exception){
                _logInCredentialsUiState.value = LogInCredentialsUiState.Error
                return false
            }
        } else{
            return false
        }
    }
}