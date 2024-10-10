package com.CioffiDeVivo.dietideals.presentation.ui.auction

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.domain.models.Auction
import com.CioffiDeVivo.dietideals.domain.models.AuctionType
import com.CioffiDeVivo.dietideals.domain.models.Bid
import com.CioffiDeVivo.dietideals.domain.models.CreditCard
import com.CioffiDeVivo.dietideals.domain.models.Item
import com.CioffiDeVivo.dietideals.domain.models.User
import com.CioffiDeVivo.dietideals.domain.mappers.toDataModel
import com.CioffiDeVivo.dietideals.utils.ApiService
import com.google.gson.Gson
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZonedDateTime

class AuctionViewModel(application: Application) : AndroidViewModel(application){
    private val _auctionState = MutableStateFlow(Auction())
    val auctionState: StateFlow<Auction> = _auctionState.asStateFlow()
    private val _isOwnerState = MutableStateFlow(false)
    val isOwnerState: StateFlow<Boolean> = _isOwnerState.asStateFlow()
    private val _insertionistState = MutableStateFlow(User())
    val insertionsState = _insertionistState.asStateFlow()

    private val _auctionUiState = MutableStateFlow<AuctionUiState>(AuctionUiState.Loading)
    val auctionUiState: StateFlow<AuctionUiState> = _auctionUiState.asStateFlow()

    var user by mutableStateOf(
        User(
            "",
            "Nametest Surnametest",
            "",
            "passwordtest",
            creditCards = arrayOf(
                CreditCard("556666666666", LocalDate.now().plusYears(1),"222"),
                CreditCard("456666666666", LocalDate.now().plusYears(2), "222"),
                CreditCard("356666666666", LocalDate.now().plusYears(2), "222")
            )
        )
    )

    var selectedAuction by mutableStateOf(
        Auction(
            "",
            "",
            Item(id = "", name = ""),
            bids = arrayOf(
                Bid(
                    "",
                    11f,
                    "",
                    ZonedDateTime.now().minusDays(5)
                )
            ),
            endingDate = LocalDate.now(),
            expired = false,
            type = AuctionType.English
        )
    )

    fun setAuction(auction: Auction) {
        _auctionState.value = auction
    }

    fun fetchIsOwnerState() {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)
        if(userId != null) {
            _isOwnerState.value = _auctionState.value.ownerId == userId
        }
    }

    fun fetchAuctionState() {
        viewModelScope.launch {
            val getAuctionResponse = ApiService.getAuction(_auctionState.value.id)
            if(getAuctionResponse.status.isSuccess()) {
                val auctionResponse = Gson().fromJson(getAuctionResponse.bodyAsText(), com.CioffiDeVivo.dietideals.domain.requestModels.Auction::class.java)
                setAuction(auctionResponse.toDataModel())
            }
        }
    }

    fun fetchInsertionist() {
        viewModelScope.launch {
            val getUserInfoResponse = ApiService.getUserInfo(_auctionState.value.ownerId)
            if (getUserInfoResponse.status.isSuccess()) {
                _insertionistState.value = Gson().fromJson(getUserInfoResponse.bodyAsText(), com.CioffiDeVivo.dietideals.domain.requestModels.User::class.java).toDataModel()
            }
        }
    }
}