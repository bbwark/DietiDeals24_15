package com.CioffiDeVivo.dietideals.presentation.ui.account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.data.models.User
import com.CioffiDeVivo.dietideals.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userRepository: UserRepository,
): ViewModel() {

    private val _accountUiState = MutableStateFlow<AccountUiState>(AccountUiState.Loading)
    val accountUiState: StateFlow<AccountUiState> = _accountUiState.asStateFlow()

    fun setUserState(){
        _accountUiState.value = AccountUiState.Loading
        viewModelScope.launch {
            _accountUiState.value = try {
                val name = userPreferencesRepository.getNamePreference()
                val email = userPreferencesRepository.getEmailPreference()
                val isSeller = userPreferencesRepository.getIsSellerPreference()
                AccountUiState.Success(name, email, isSeller)
            } catch (e: Exception){
                AccountUiState.Error
            }
        }
    }

    fun logOut(){
        _accountUiState.value = AccountUiState.Loading
        viewModelScope.launch {
            _accountUiState.value = try {
                val userId = userPreferencesRepository.getUserIdPreference()
                val deviceToken = userPreferencesRepository.getDeviceToken()
                val user = userRepository.getUser(userId)
                if(user.deviceTokens.contains(deviceToken)){
                    val mutableList = user.deviceTokens.toMutableList()
                    mutableList.remove(deviceToken)
                    val updatedArray = mutableList.toTypedArray()
                    val updatedUser = user.copy(
                        password = "",
                        deviceTokens = updatedArray
                    )
                    userRepository.updateUser(userId, updatedUser)
                }
                userPreferencesRepository.clearPreferences()
                AccountUiState.SuccessLogout
            } catch (e: Exception){
                Log.e("ERROR", "ERROR: ${e.message}")
                AccountUiState.Error
            }
        }
    }

}