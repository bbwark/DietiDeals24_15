package com.CioffiDeVivo.dietideals.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.DataModels.Auction
import com.CioffiDeVivo.dietideals.domain.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.domain.DataModels.Item
import com.CioffiDeVivo.dietideals.domain.Mappers.toDataModel
import com.CioffiDeVivo.dietideals.utils.ApiService
import com.google.gson.Gson
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID

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
                    val user = Gson().fromJson(getUserResponse.bodyAsText(), com.CioffiDeVivo.dietideals.domain.RequestModels.User::class.java).toDataModel()
                    _userAuctionState.value = user.ownedAuctions.toCollection(ArrayList())
                }
            }
        }
    }

}