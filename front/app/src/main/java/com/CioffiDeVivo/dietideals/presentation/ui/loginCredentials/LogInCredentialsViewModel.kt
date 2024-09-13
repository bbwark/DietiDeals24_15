package com.CioffiDeVivo.dietideals.presentation.ui.loginCredentials

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.utils.ApiService
import com.CioffiDeVivo.dietideals.utils.AuthService
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

    private val _userLogInState = MutableStateFlow(LogInState())
    val userLogInState: StateFlow<LogInState> = _userLogInState.asStateFlow()
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
        _userLogInState.value = _userLogInState.value.copy(
            email = email
        )
    }

    private fun deleteEmail() {
        _userLogInState.value = _userLogInState.value.copy(
            email = ""
        )
    }

    private fun updatePassword(password: String) {
        _userLogInState.value = _userLogInState.value.copy(
            password = password
        )
    }

    private fun submitLogIn() {
        if (validationBlock()) {
            viewModelScope.launch {
                try {
                    val loginResponse = AuthService.loginUser(
                        _userLogInState.value.email,
                        _userLogInState.value.password
                    )
                    if (loginResponse.status.isSuccess()) {
                        val jsonObject =
                            Gson().fromJson(loginResponse.bodyAsText(), JsonObject::class.java)
                        val token = jsonObject.get("jwt").asString
                        if (token.isNotEmpty()) {
                            val userId = jsonObject.getAsJsonObject("user").get("id").asString

                            sharedPreferences.edit().apply {
                                putString("userId", userId)
                                apply()
                            }

                            val encryptedSharedPreferences =
                                EncryptedPreferencesManager.getEncryptedPreferences()
                            encryptedSharedPreferences.edit().apply {
                                putString("email", _userLogInState.value.email)
                                putString("password", _userLogInState.value.password)
                                apply()
                            }

                            ApiService.initialize(
                                token,
                                getApplication<Application>().applicationContext
                            )

                        }
                    }
                } catch (e: Exception) {
                    //TODO error handling
                }
            }
        }
    }

    private fun validationBlock() : Boolean {
        val emailValidation = validateLogInForms.validateEmail(userLogInState.value.email)
        val passwordValidation = validateLogInForms.validatePassword(userLogInState.value.password)

        val hasError = listOf(
            emailValidation,
            passwordValidation,
        ).any { !it.positiveResult }

        if (hasError) {
            _userLogInState.value = _userLogInState.value.copy(
                emailErrorMsg = emailValidation.errorMessage,
                passwordErrorMsg = passwordValidation.errorMessage,
            )
            return false
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationState.Success)
        }
        return true
    }
}