package com.CioffiDeVivo.dietideals.presentation.ui.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavouritesViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userRepository: UserRepository
): ViewModel(){

    private val _favouritesUiState = MutableStateFlow<FavouritesUiState>(FavouritesUiState.Loading)
    val favouritesUiState: StateFlow<FavouritesUiState> = _favouritesUiState.asStateFlow()

    fun fetchUserFavouriteAuction(){
        _favouritesUiState.value = FavouritesUiState.Loading
        viewModelScope.launch {
            _favouritesUiState.value = try {
                val userId = userPreferencesRepository.getUserIdPreference()
                val user = userRepository.getUser(userId)
                FavouritesUiState.Success(user.favouriteAuctions)
            } catch (e: Exception){
                FavouritesUiState.Error
            }
        }
    }

}