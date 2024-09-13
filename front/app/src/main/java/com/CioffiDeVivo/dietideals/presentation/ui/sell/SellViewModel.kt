package com.CioffiDeVivo.dietideals.presentation.ui.sell

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.models.Auction
import com.CioffiDeVivo.dietideals.domain.mappers.toDataModel
import com.CioffiDeVivo.dietideals.utils.ApiService
import com.google.gson.Gson
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SellViewModel(application: Application): AndroidViewModel(application) {

    private val _userAuctionState = MutableStateFlow<ArrayList<Auction>>(arrayListOf())
    val userAuctionState = _userAuctionState.asStateFlow()

    fun fetchAuctions() {
        viewModelScope.launch {
            val sharedPreferences = getApplication<Application>().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getString("userId", null)
            if (userId != null) {
                val getUserResponse = ApiService.getUser(userId)
                if (getUserResponse.status.isSuccess()) {
                    val user = Gson().fromJson(getUserResponse.bodyAsText(), com.CioffiDeVivo.dietideals.domain.requestModels.User::class.java).toDataModel()
                    _userAuctionState.value = user.ownedAuctions.toCollection(ArrayList())
                }
            }
        }
    }

}