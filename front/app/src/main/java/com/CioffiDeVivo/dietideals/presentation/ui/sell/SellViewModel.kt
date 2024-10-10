package com.CioffiDeVivo.dietideals.presentation.ui.sell

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.mappers.toDataModel
import com.CioffiDeVivo.dietideals.domain.models.User
import com.CioffiDeVivo.dietideals.domain.repository.UserRepository
import com.CioffiDeVivo.dietideals.utils.ApiService
import com.google.gson.Gson
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SellViewModel: ViewModel() {

    private val _sellUiState = MutableStateFlow<SellUiState>(SellUiState.Loading)
    val sellUiState: StateFlow<SellUiState> = _sellUiState.asStateFlow()
    val loggedUser: StateFlow<User?> = UserRepository.loggedUser

    fun fetchAuctions() {
        viewModelScope.launch {
            setLoadingState()
            _sellUiState.value = try {
                val userId = loggedUser.value?.id
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