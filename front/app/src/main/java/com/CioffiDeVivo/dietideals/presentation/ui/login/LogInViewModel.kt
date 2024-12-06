package com.CioffiDeVivo.dietideals.presentation.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.data.models.Country
import com.CioffiDeVivo.dietideals.data.repositories.AuthRepository
import com.CioffiDeVivo.dietideals.data.repositories.UserRepository
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LogInViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private val _isUserAuthenticated = MutableStateFlow<Boolean?>(null)
    val isUserAuthenticated: StateFlow<Boolean?> = _isUserAuthenticated.asStateFlow()
    private val _logInUiState = MutableStateFlow<LogInUiState>(LogInUiState.Loading)
    val logInUiState: StateFlow<LogInUiState> = _logInUiState.asStateFlow()

    init {
        _logInUiState.value = LogInUiState.Loading
        checkUserAuthentication()
    }

    private fun checkUserAuthentication(){
        _logInUiState.value = LogInUiState.Loading
        viewModelScope.launch {
            try {
                if(userPreferencesRepository.getTokenPreference().isNotEmpty()){
                    _isUserAuthenticated.value = true
                } else{
                    _logInUiState.value = LogInUiState.Success
                }
            } catch (e: Exception){
                _logInUiState.value = LogInUiState.Error
            }
        }
    }

    fun loginWithGoogle(googleIdToken: String){
        _logInUiState.value = LogInUiState.Loading
        viewModelScope.launch {
            try {

                val loginResponse = authRepository.loginWithGoogle(googleIdToken)
                userPreferencesRepository.saveUserId(loginResponse.user.id)
                userPreferencesRepository.saveToken(loginResponse.jwt)
                userPreferencesRepository.saveEmail(loginResponse.user.email)
                userPreferencesRepository.saveName(loginResponse.user.name)
                userPreferencesRepository.saveIsSeller(loginResponse.user.isSeller)
                val firebase = FirebaseMessaging.getInstance().token.await()
                val deviceTokenArray = loginResponse.user.deviceTokens.plus(firebase)
                userRepository.updateUser(loginResponse.user.id, loginResponse.user.copy(
                    password = "",
                    deviceTokens = deviceTokenArray
                ))
                userPreferencesRepository.saveDeviceToken(firebase)
                _logInUiState.value = LogInUiState.SuccessWithGoogle
            } catch (e: Exception){
                Log.d("ERROR", "${e.message}")
                _logInUiState.value = LogInUiState.Error
            }
        }
    }

}