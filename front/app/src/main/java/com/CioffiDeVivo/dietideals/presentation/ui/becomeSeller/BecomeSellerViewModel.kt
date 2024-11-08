package com.CioffiDeVivo.dietideals.presentation.ui.becomeSeller

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.mappers.toDataModel
import com.CioffiDeVivo.dietideals.domain.mappers.toRequestModel
import com.CioffiDeVivo.dietideals.domain.models.Country
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.RegisterCredentialsUiState
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.RegistrationEvents
import com.CioffiDeVivo.dietideals.presentation.ui.sell.SellUiState
import com.CioffiDeVivo.dietideals.services.ApiService
import com.google.gson.Gson
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BecomeSellerViewModel(application: Application): AndroidViewModel(application) {

    private val _becomeSellerUiState = MutableStateFlow<BecomeSellerUiState>(BecomeSellerUiState.BecomeSellerParams())
    val becomeSellerUiState: StateFlow<BecomeSellerUiState> = _becomeSellerUiState.asStateFlow()

    private val sharedPreferences by lazy {
        application.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    }

    fun getUserInfo(){
        val userId = sharedPreferences.getString("userId", null)
        if(userId != null){
            viewModelScope.launch {
                setLoadingState()
                _becomeSellerUiState.value = try {
                    val userInfoResponse = ApiService.getUser(userId)
                    if(userInfoResponse.status.isSuccess()){
                        val user = Gson().fromJson(userInfoResponse.bodyAsText(), com.CioffiDeVivo.dietideals.domain.requestModels.User::class.java).toDataModel()
                        BecomeSellerUiState.BecomeSellerParams(user = user)
                    } else{
                        Log.e("Error", "Error: Error on GET User!")
                        BecomeSellerUiState.Error
                    }
                } catch (e: Exception){
                    Log.e("Error", "Error: ${e.message}")
                    BecomeSellerUiState.Error
                }
            }
        }
    }

    private fun setLoadingState(){
        _becomeSellerUiState.value = BecomeSellerUiState.Loading
    }

    fun becomeSellerOnAction(becomeSellerEvents: BecomeSellerEvents){
        when(becomeSellerEvents){
            is BecomeSellerEvents.SellerChange -> {
                updateIsSeller(becomeSellerEvents.isSeller)
            }
            is BecomeSellerEvents.AddressChanged -> {
                updateAddress(becomeSellerEvents.address)
            }
            is BecomeSellerEvents.AddressDeleted -> {
                deleteAddress()
            }
            is BecomeSellerEvents.ZipCodeChanged -> {
                updateZipCode(becomeSellerEvents.zipCode)
            }
            is BecomeSellerEvents.ZipCodeDeleted -> {
                deleteZipCode()
            }
            is BecomeSellerEvents.CountryChanged -> {
                updateCountry(becomeSellerEvents.country)
            }
            is BecomeSellerEvents.PhoneNumberChanged -> {
                updatePhoneNumber(becomeSellerEvents.phoneNumber)
            }
            is BecomeSellerEvents.PhoneNumberDeleted -> {
                deletePhoneNumber()
            }
            is BecomeSellerEvents.CreditCardNumberChanged -> {
                updateCreditCardNumber(becomeSellerEvents.creditCardNumber)
            }
            is BecomeSellerEvents.CreditCardNumberDeleted -> {
                deleteCreditCardNumber()
            }
            is BecomeSellerEvents.ExpirationDateChanged -> {
                updateExpirationDate(becomeSellerEvents.expirationDate)
            }
            is BecomeSellerEvents.ExpirationDateDeleted -> {
                deleteExpirationDate()
            }
            is BecomeSellerEvents.CvvChanged -> {
                updateCvv(becomeSellerEvents.cvv)
            }
            is BecomeSellerEvents.CvvDeleted -> {
                deleteCvv()
            }
            is BecomeSellerEvents.IbanChanged -> {
                updateIban(becomeSellerEvents.iban)
            }
            is BecomeSellerEvents.IbanDeleted -> {
                deleteIban()
            }
            is BecomeSellerEvents.Submit -> {
                submitForm()
            }
        }
    }

    private fun submitForm(){
        if(validationBlock()){
            val currentState = _becomeSellerUiState.value
            if(currentState is BecomeSellerUiState.BecomeSellerParams){
                _becomeSellerUiState.value = BecomeSellerUiState.Loading
                viewModelScope.launch {
                    _becomeSellerUiState.value = try {
                        val updatedUserParams = currentState.copy(
                            user = currentState.user.copy(
                                isSeller = true,
                            )
                        )
                        val userRequest = updatedUserParams.user.toRequestModel()
                        val updateUserResponse = ApiService.updateUser(userRequest)
                        if(updateUserResponse.status.isSuccess()){
                            BecomeSellerUiState.Success
                        } else{
                            Log.e("Error", "Error: Error on UPDATE User")
                            BecomeSellerUiState.Error
                        }
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

    private fun updateIsSeller(isSeller: Boolean){
        try {
            val currentState = _becomeSellerUiState.value
            if(currentState is BecomeSellerUiState.BecomeSellerParams){
                _becomeSellerUiState.value = currentState.copy(
                    user = currentState.user.copy(
                        isSeller = isSeller
                    )
                )
            }
        } catch (e: Exception){
            _becomeSellerUiState.value = BecomeSellerUiState.Error
        }
    }

    private fun updateAddress(address: String){
        try {
            val currentState = _becomeSellerUiState.value
            if(currentState is BecomeSellerUiState.BecomeSellerParams){
                _becomeSellerUiState.value = currentState.copy(
                    user = currentState.user.copy(
                        address = address
                    )
                )
            }
        } catch (e: Exception){
            _becomeSellerUiState.value = BecomeSellerUiState.Error
        }
    }

    private fun deleteAddress(){
        updateAddress("")
    }

    private fun updateZipCode(zipCode: String){
        try {
            val currentState = _becomeSellerUiState.value
            if(currentState is BecomeSellerUiState.BecomeSellerParams){
                _becomeSellerUiState.value = currentState.copy(
                    user = currentState.user.copy(
                        zipCode = zipCode
                    )
                )
            }
        } catch (e: Exception){
            _becomeSellerUiState.value = BecomeSellerUiState.Error
        }
    }

    private fun deleteZipCode(){
        updateZipCode("")
    }

    private fun updateCountry(country: Country){
        try {
            val currentState = _becomeSellerUiState.value
            if(currentState is BecomeSellerUiState.BecomeSellerParams){
                _becomeSellerUiState.value = currentState.copy(
                    user = currentState.user.copy(
                        country = country
                    )
                )
            }
        } catch (e: Exception){
            _becomeSellerUiState.value = BecomeSellerUiState.Error
        }
    }

    private fun updatePhoneNumber(phoneNumber: String){
        try {
            val currentState = _becomeSellerUiState.value
            if(currentState is BecomeSellerUiState.BecomeSellerParams){
                _becomeSellerUiState.value = currentState.copy(
                    user = currentState.user.copy(
                        phoneNumber = phoneNumber
                    )
                )
            }
        } catch (e: Exception){
            _becomeSellerUiState.value = BecomeSellerUiState.Error
        }
    }

    private fun deletePhoneNumber(){
        updatePhoneNumber("")
    }

    private fun updateCreditCardNumber(creditCardNumber: String){
        try {
            val currentState = _becomeSellerUiState.value
            if(currentState is BecomeSellerUiState.BecomeSellerParams){
                _becomeSellerUiState.value = currentState.copy(
                    creditCard = currentState.creditCard.copy(
                        creditCardNumber = creditCardNumber
                    )
                )
            }
        } catch (e: Exception){
            _becomeSellerUiState.value = BecomeSellerUiState.Error
        }
    }

    private fun deleteCreditCardNumber(){
        updateCreditCardNumber("")
    }

    private fun updateExpirationDate(expirationDate: String){
        try {
            val currentState = _becomeSellerUiState.value
            if(currentState is BecomeSellerUiState.BecomeSellerParams){
                _becomeSellerUiState.value = currentState.copy(
                    expirationDate = expirationDate
                )
            }
        } catch (e: Exception){
            _becomeSellerUiState.value = BecomeSellerUiState.Error
        }
    }

    private fun deleteExpirationDate(){
        updateExpirationDate("")
    }

    private fun updateCvv(cvv: String){
        try {
            val currentState = _becomeSellerUiState.value
            if(currentState is BecomeSellerUiState.BecomeSellerParams){
                _becomeSellerUiState.value = currentState.copy(
                    creditCard = currentState.creditCard.copy(
                        cvv = cvv
                    )
                )
            }
        } catch (e: Exception){
            _becomeSellerUiState.value = BecomeSellerUiState.Error
        }
    }

    private fun deleteCvv(){
        updateCvv("")
    }

    private fun updateIban(iban: String){
        try {
            val currentState = _becomeSellerUiState.value
            if(currentState is BecomeSellerUiState.BecomeSellerParams){
                _becomeSellerUiState.value = currentState.copy(
                    creditCard = currentState.creditCard.copy(
                        iban = iban
                    )
                )
            }
        } catch (e: Exception){
            _becomeSellerUiState.value = BecomeSellerUiState.Error
        }
    }

    private fun deleteIban(){
        updateIban("")
    }

}