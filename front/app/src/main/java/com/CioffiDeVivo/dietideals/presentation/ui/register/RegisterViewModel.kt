package com.CioffiDeVivo.dietideals.presentation.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.data.models.Country
import com.CioffiDeVivo.dietideals.data.repositories.AuthRepository
import com.CioffiDeVivo.dietideals.data.repositories.UserRepository
import com.CioffiDeVivo.dietideals.presentation.ui.login.LogInUiState
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private val _registerUiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Success)
    val registerUiState: StateFlow<RegisterUiState> = _registerUiState.asStateFlow()

    fun loginWithGoogle(googleIdToken: String){
        _registerUiState.value = RegisterUiState.Loading
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
                _registerUiState.value = RegisterUiState.SuccessWithGoogle
            } catch (e: Exception){
                _registerUiState.value = RegisterUiState.Error
            }
        }
    }

}