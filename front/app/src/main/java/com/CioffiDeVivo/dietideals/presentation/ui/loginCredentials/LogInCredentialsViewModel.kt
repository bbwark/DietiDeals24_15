package com.CioffiDeVivo.dietideals.presentation.ui.loginCredentials

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.mappers.toDataModel
import com.CioffiDeVivo.dietideals.domain.models.User
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
    application: Application,
    private val validateLogInForms: ValidateLogInForm = ValidateLogInForm()
) : AndroidViewModel(application) {

    private val _logInCredentialsUiState = MutableStateFlow<LogInCredentialsUiState>(LogInCredentialsUiState.LogInParams())
    val logInCredentialsUiState: StateFlow<LogInCredentialsUiState> = _logInCredentialsUiState.asStateFlow()

    private val validationEventChannel = Channel<ValidationState>()
    val validationLogInEvent = validationEventChannel.receiveAsFlow()

    private val sharedPreferences by lazy {
        application.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    }

    fun loginOnAction(loginEvent: LoginEvent) {
        when (loginEvent) {
            is LoginEvent.EmailChanged -> {
                updateEmail(loginEvent.email)
            }

            is LoginEvent.EmailDeleted -> {
                deleteEmail()
            }

            is LoginEvent.PasswordChanged -> {
                updatePassword(loginEvent.password)
            }

            is LoginEvent.Submit -> {
                submitLogIn()
            }
        }
    }

    private fun updateEmail(email: String) {
        try {
            val currentState = _logInCredentialsUiState.value
            if(currentState is LogInCredentialsUiState.LogInParams){
                _logInCredentialsUiState.value = currentState.copy(
                    email = email
                )
            }
        } catch (e: Exception){
            _logInCredentialsUiState.value = LogInCredentialsUiState.Error
        }
    }

    private fun deleteEmail() {
       updateEmail("")
    }

    private fun updatePassword(password: String) {
        try {
            val currentState = _logInCredentialsUiState.value
            if(currentState is LogInCredentialsUiState.LogInParams){
                _logInCredentialsUiState.value = currentState.copy(
                    password = password
                )
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
                        val loginResponse = AuthService.loginUser(
                            currentState.email,
                            currentState.password
                        )
                        if (loginResponse.status.isSuccess()) {
                            val jsonObject =
                                Gson().fromJson(loginResponse.bodyAsText(), JsonObject::class.java)
                            val token = jsonObject.get("jwt").asString
                            if (token.isNotEmpty()) {
                                val userId = jsonObject.getAsJsonObject("user").get("id").asString
                                sharedPreferences.edit().apply {
                                    putString("token", token)
                                    putString("userId", userId)
                                    apply()
                                }
                                ApiService.initialize(
                                    token,
                                    getApplication<Application>().applicationContext
                                )

                            }
                            LogInCredentialsUiState.Success
                        } else{
                            Log.e("Error", "Error: $loginResponse")
                            LogInCredentialsUiState.Error
                        }
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