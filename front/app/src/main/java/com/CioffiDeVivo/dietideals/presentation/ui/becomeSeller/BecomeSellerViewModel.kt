package com.CioffiDeVivo.dietideals.presentation.ui.becomeSeller

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.data.models.CreditCard
import com.CioffiDeVivo.dietideals.data.repositories.AuthRepository
import com.CioffiDeVivo.dietideals.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BecomeSellerViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private val _becomeSellerUiState = MutableStateFlow<BecomeSellerUiState>(BecomeSellerUiState.BecomeSellerParams())
    val becomeSellerUiState: StateFlow<BecomeSellerUiState> = _becomeSellerUiState.asStateFlow()

    fun getUserInfo(){
        _becomeSellerUiState.value = BecomeSellerUiState.Loading
        viewModelScope.launch {
            _becomeSellerUiState.value = try {
                val userId = userPreferencesRepository.getUserIdPreference()
                val user = userRepository.getUser(userId)
                BecomeSellerUiState.BecomeSellerParams(user)
            } catch (e: Exception){
                Log.e("Error", "Error: ${e.message}")
                BecomeSellerUiState.Error
            }
        }

    }

    fun becomeSellerOnAction(becomeSellerEvents: BecomeSellerEvents){
        try {
            val currentState = _becomeSellerUiState.value
            if(currentState is BecomeSellerUiState.BecomeSellerParams){
                when(becomeSellerEvents){
                    is BecomeSellerEvents.AddressChanged -> {
                        _becomeSellerUiState.value = currentState.copy(user = currentState.user.copy(address = becomeSellerEvents.address))
                    }
                    is BecomeSellerEvents.AddressDeleted -> {
                        _becomeSellerUiState.value = currentState.copy(user = currentState.user.copy(address = becomeSellerEvents.address))
                    }
                    is BecomeSellerEvents.ZipCodeChanged -> {
                        _becomeSellerUiState.value = currentState.copy(user = currentState.user.copy(zipcode = becomeSellerEvents.zipCode))
                    }
                    is BecomeSellerEvents.ZipCodeDeleted -> {
                        _becomeSellerUiState.value = currentState.copy(user = currentState.user.copy(zipcode = ""))
                    }
                    is BecomeSellerEvents.CountryChanged -> {
                        _becomeSellerUiState.value = currentState.copy(user = currentState.user.copy(country = becomeSellerEvents.country))
                    }
                    is BecomeSellerEvents.PhoneNumberChanged -> {
                        _becomeSellerUiState.value = currentState.copy(user = currentState.user.copy(phoneNumber = becomeSellerEvents.phoneNumber))
                    }
                    is BecomeSellerEvents.PhoneNumberDeleted -> {
                        _becomeSellerUiState.value = currentState.copy(user = currentState.user.copy(phoneNumber = ""))
                    }
                    is BecomeSellerEvents.CreditCardNumberChanged -> {
                        _becomeSellerUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(creditCardNumber = becomeSellerEvents.creditCardNumber))
                    }
                    is BecomeSellerEvents.CreditCardNumberDeleted -> {
                        _becomeSellerUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(creditCardNumber = ""))
                    }
                    is BecomeSellerEvents.ExpirationDateChanged -> {
                        _becomeSellerUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(expirationDate = becomeSellerEvents.expirationDate))
                    }
                    is BecomeSellerEvents.ExpirationDateDeleted -> {
                        _becomeSellerUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(expirationDate = ""))
                    }
                    is BecomeSellerEvents.CvvChanged -> {
                        _becomeSellerUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(cvv = becomeSellerEvents.cvv))
                    }
                    is BecomeSellerEvents.CvvDeleted -> {
                        _becomeSellerUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(cvv = ""))
                    }
                    is BecomeSellerEvents.IbanChanged -> {
                        _becomeSellerUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(iban = becomeSellerEvents.iban))
                    }
                    is BecomeSellerEvents.IbanDeleted -> {
                        _becomeSellerUiState.value = currentState.copy(creditCard = currentState.creditCard.copy(iban = ""))
                    }
                    is BecomeSellerEvents.Submit -> {
                        submitForm()
                    }
                }
            }
        } catch (e: Exception){
            _becomeSellerUiState.value = BecomeSellerUiState.Error
        }
    }

    private fun submitForm(){
        if(validationBlock()){
            val currentState = _becomeSellerUiState.value
            if(currentState is BecomeSellerUiState.BecomeSellerParams){
                _becomeSellerUiState.value = BecomeSellerUiState.Loading
                viewModelScope.launch {
                    _becomeSellerUiState.value = try {
                        val userId = userPreferencesRepository.getUserIdPreference()
                        if(currentState.user.creditCards.isNotEmpty()){
                            val updatedUser = currentState.user.copy(
                                isSeller = true
                            )
                            userRepository.updateUser(userId, updatedUser)
                        } else{
                            val updatedUser = currentState.user.copy(
                                isSeller = true,
                                creditCards = currentState.user.creditCards + currentState.creditCard
                            )
                            userRepository.updateUser(userId, updatedUser)
                        }
                        userPreferencesRepository.saveIsSeller(true)
                        BecomeSellerUiState.Success
                    } catch(e: Exception){
                        Log.e("Error", "Error: ${e.message}")
                        BecomeSellerUiState.Error
                    }
                }
            }
        }
    }

    private fun validationBlock(): Boolean{
        return true
    }

}