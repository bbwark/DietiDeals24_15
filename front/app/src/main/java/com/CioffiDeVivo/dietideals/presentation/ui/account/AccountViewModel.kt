package com.CioffiDeVivo.dietideals.presentation.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountViewModel(private val userPreferencesRepository: UserPreferencesRepository) : ViewModel() {

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
                userPreferencesRepository.clearPreferences()
                AccountUiState.SuccessLogout
            } catch (e: Exception){
                AccountUiState.Error
            }
        }
    }



}