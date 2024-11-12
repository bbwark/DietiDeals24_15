package com.CioffiDeVivo.dietideals.presentation.ui.manageCards

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.mappers.toDataModel
import com.CioffiDeVivo.dietideals.services.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.google.gson.Gson
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess

class ManageCardsViewModel(application: Application) : AndroidViewModel(application){

    private val _manageCardsUiState = MutableStateFlow<ManageCardsUiState>(ManageCardsUiState.Loading)
    val manageCardsUiState: StateFlow<ManageCardsUiState> = _manageCardsUiState.asStateFlow()

    private val sharedPreferences by lazy {
        application.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    }

    fun fetchCreditCards(){
        viewModelScope.launch {
            setLoadingState()
            _manageCardsUiState.value = try {
                val userId = sharedPreferences.getString("userId", null)
                if(userId != null){
                    val creditCardResponse = ApiService.getUser(userId)
                    if(creditCardResponse.status.isSuccess()){
                        val user = Gson().fromJson(creditCardResponse.bodyAsText(), com.CioffiDeVivo.dietideals.domain.requestModels.User::class.java).toDataModel()
                        ManageCardsUiState.Success(user.creditCards)
                    } else{
                        ManageCardsUiState.Error
                    }
                } else{
                    ManageCardsUiState.Error
                }
            } catch (e: Exception){
                ManageCardsUiState.Error
            }
        }
    }

    fun deleteCard(creditCardNumber: String) {
        viewModelScope.launch {
            setLoadingState()
            try {
                val response = ApiService.deleteCreditCard(creditCardNumber)
                if(response.status.isSuccess()){
                    fetchCreditCards()
                } else{
                    ManageCardsUiState.Error
                }
            } catch (e: Exception){
                ManageCardsUiState.Error
            }

        }
    }

    private fun setLoadingState(){
        _manageCardsUiState.value = ManageCardsUiState.Loading
    }
}