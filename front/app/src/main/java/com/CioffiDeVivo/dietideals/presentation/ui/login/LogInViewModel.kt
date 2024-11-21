package com.CioffiDeVivo.dietideals.presentation.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.services.ApiService
import com.CioffiDeVivo.dietideals.services.TokenService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LogInViewModel(): ViewModel() {

    private val _isUserAuthenticated = MutableStateFlow<Boolean?>(null)
    val isUserAuthenticated: StateFlow<Boolean?> = _isUserAuthenticated.asStateFlow()
    private val _logInUiState = MutableStateFlow<LogInUiState>(LogInUiState.Success)
    val logInUiState: StateFlow<LogInUiState> = _logInUiState.asStateFlow()

    /*init {
        checkUserAuthentication()
    }*/

    /*private fun checkUserAuthentication(){
        viewModelScope.launch {
            setLoadingState()
            try {
                val token = TokenService.getToken(getApplication())
                _isUserAuthenticated.value = token != null
                if (token != null) {
                    ApiService.initialize(token, getApplication())
                }
                setSuccessState()
            } catch (e: Exception){
                setErrorState()
            }
        }
    }*/

    private fun setLoadingState(){
        _logInUiState.value = LogInUiState.Loading
    }

    private fun setErrorState(){
        _logInUiState.value = LogInUiState.Error
    }

    private fun setSuccessState(){
        _logInUiState.value = LogInUiState.Success
    }

}