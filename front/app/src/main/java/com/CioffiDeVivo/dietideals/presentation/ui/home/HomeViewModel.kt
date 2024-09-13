package com.CioffiDeVivo.dietideals.presentation.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.models.Auction
import com.CioffiDeVivo.dietideals.domain.mappers.toDataModel
import com.CioffiDeVivo.dietideals.utils.ApiService
import kotlinx.coroutines.launch

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