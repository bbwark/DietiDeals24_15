package com.CioffiDeVivo.dietideals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.use_case.ValidateLogInForm
import com.CioffiDeVivo.dietideals.domain.use_case.ValidationState
import com.CioffiDeVivo.dietideals.viewmodel.state.LogInState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LogInCredentialsViewModel( private val validateLogInForms: ValidateLogInForm = ValidateLogInForm() ) : ViewModel() {

    private val _userLogInState = MutableStateFlow(LogInState())
    val userLogInState: StateFlow<LogInState> = _userLogInState.asStateFlow()
    private val validationEventChannel = Channel<ValidationState>()
    val validationLogInEvent = validationEventChannel.receiveAsFlow()

    fun updateEmail(email: String){
        _userLogInState.value = _userLogInState.value.copy(
            email = email
        )
    }

    fun deleteEmail(){
        _userLogInState.value = _userLogInState.value.copy(
            email = ""
        )
    }

    fun updatePassword(password: String){
        _userLogInState.value = _userLogInState.value.copy(
            password = password
        )
    }

    private fun submitLogIn(){
        val emailValidation = validateLogInForms.validateEmail(userLogInState.value.email)
        val passwordValidation = validateLogInForms.validatePassword(userLogInState.value.password)

        val hasError = listOf(
            emailValidation,
            passwordValidation,
        ).any { !it.positiveResult }

        if(hasError){
            _userLogInState.value = _userLogInState.value.copy(
                emailErrorMsg = emailValidation.errorMessage,
                passwordErrorMsg = passwordValidation.errorMessage,
            )
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationState.Success)
        }
    }
}