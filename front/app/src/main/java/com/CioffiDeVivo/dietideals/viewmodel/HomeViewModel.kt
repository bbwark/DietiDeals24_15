package com.CioffiDeVivo.dietideals.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.DataModels.Auction
import com.CioffiDeVivo.dietideals.domain.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.domain.DataModels.Bid
import com.CioffiDeVivo.dietideals.domain.DataModels.CreditCard
import com.CioffiDeVivo.dietideals.domain.DataModels.Item
import com.CioffiDeVivo.dietideals.domain.DataModels.User
import com.CioffiDeVivo.dietideals.domain.Mappers.toDataModel
import com.CioffiDeVivo.dietideals.utils.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZonedDateTime

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    fun getLatestAuctions(): Array<Auction> {
        var randomAuctionsData: Array<Auction> = arrayOf()
        viewModelScope.launch {

            val sharedPreferences = getApplication<Application>().getSharedPreferences(
                "AppPreferences",
                Context.MODE_PRIVATE
            )
            val userId = sharedPreferences.getString("userId", null)
            if (userId != null) {
                try {
                    val randomAuctionsRequest = ApiService.getRandomAuctions(userId)
                    randomAuctionsData = randomAuctionsRequest.map { it.toDataModel() }.toTypedArray()
                } catch (e: Exception) {
                    //TODO error handling
                }
            }
        }
        return randomAuctionsData
    }

    fun getEndingAuctions(): Array<Auction> {
        return arrayOf()
    }

    fun getParticipatedAuctions(): Array<Auction> {
        return arrayOf()
    }

    //Rest fo getting the correct Auctions for each category(latest, ending, participated...)
}