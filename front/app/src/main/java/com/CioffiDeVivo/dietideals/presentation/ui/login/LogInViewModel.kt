package com.CioffiDeVivo.dietideals.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LogInViewModel(private val userPreferencesRepository: UserPreferencesRepository): ViewModel() {

    private val _isUserAuthenticated = MutableStateFlow<Boolean?>(null)
    val isUserAuthenticated: StateFlow<Boolean?> = _isUserAuthenticated.asStateFlow()
    private val _logInUiState = MutableStateFlow<LogInUiState>(LogInUiState.Success)
    val logInUiState: StateFlow<LogInUiState> = _logInUiState.asStateFlow()

    /*init {
        checkUserAuthentication()
    }*/

    private fun checkUserAuthentication(){
        _logInUiState.value = LogInUiState.Loading
        viewModelScope.launch {
            try {
                if(userPreferencesRepository.getTokenPreference().isNotEmpty()){
                    _isUserAuthenticated.value = true
                }
                _logInUiState.value = LogInUiState.Success
            } catch (e: Exception){
                _logInUiState.value = LogInUiState.Error
            }
        }
    }

}