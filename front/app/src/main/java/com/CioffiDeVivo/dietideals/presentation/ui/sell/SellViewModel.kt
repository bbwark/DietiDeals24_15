package com.CioffiDeVivo.dietideals.presentation.ui.sell

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.mappers.toDataModel
import com.CioffiDeVivo.dietideals.services.ApiService
import com.google.gson.Gson
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SellViewModel(application: Application): AndroidViewModel(application) {

    private val _sellUiState = MutableStateFlow<SellUiState>(SellUiState.Loading)
    val sellUiState: StateFlow<SellUiState> = _sellUiState.asStateFlow()

    private val sharedPreferences by lazy {
        application.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    }

    fun fetchAuctions() {
        viewModelScope.launch {
            setLoadingState()
            _sellUiState.value = try {
                val userId = sharedPreferences.getString("userId", null)
                if (userId != null) {
                    val getUserResponse = ApiService.getUser(userId)
                    if (getUserResponse.status.isSuccess()) {
                        val user = Gson().fromJson(getUserResponse.bodyAsText(), com.CioffiDeVivo.dietideals.domain.requestModels.User::class.java).toDataModel()
                        SellUiState.Success(user.ownedAuctions.toCollection(ArrayList()))
                    } else{
                        SellUiState.Error
                    }
                } else{
                    SellUiState.Error
                }
            } catch(e: Exception){
                SellUiState.Error
            }
        }
    }

    private fun setLoadingState(){
        _sellUiState.value = SellUiState.Loading
    }

}