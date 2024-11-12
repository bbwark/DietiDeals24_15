package com.CioffiDeVivo.dietideals.presentation.common.sharedViewmodels

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
import com.CioffiDeVivo.dietideals.domain.mappers.toRequestModel
import com.CioffiDeVivo.dietideals.presentation.ui.auction.AuctionUiState
import com.CioffiDeVivo.dietideals.presentation.ui.bidHistory.BidHistoryUiState
import com.CioffiDeVivo.dietideals.presentation.ui.makeBid.MakeABidUiState
import com.CioffiDeVivo.dietideals.services.ApiService
import com.google.gson.Gson
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private val _auctionState = MutableStateFlow(Auction())
    val auctionState: StateFlow<Auction> = _auctionState.asStateFlow()
    private val _bidState = MutableStateFlow(Bid())
    val bidState: StateFlow<Bid> = _bidState.asStateFlow()

    private val _auctionUiState = MutableStateFlow<AuctionUiState>(AuctionUiState.Loading)
    val auctionUiState: StateFlow<AuctionUiState> = _auctionUiState.asStateFlow()
    private val _bidHistoryUiState = MutableStateFlow<BidHistoryUiState>(BidHistoryUiState.Loading)
    val bidHistoryUiState: StateFlow<BidHistoryUiState> = _bidHistoryUiState.asStateFlow()
    private val _makeABidUiState = MutableStateFlow<MakeABidUiState>(MakeABidUiState.Loading)
    val makeABidUiState: StateFlow<MakeABidUiState> = _makeABidUiState.asStateFlow()

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
            endingDate = LocalDateTime.now(),
            expired = false,
            type = AuctionType.English
        )
    )

    fun fetchAuctionBidders() {
        viewModelScope.launch {
            _bidHistoryUiState.value = try {
                val bidders = getBiddersFromServer(_auctionState.value.id)
                BidHistoryUiState.Success(bidders)
            } catch (e: Exception){
                BidHistoryUiState.Error
            }
        }
    }


        private suspend fun getBiddersFromServer(auctionId: String): List<User> {
        val result = emptyList<User>().toMutableList()
        val requestUsers = ApiService.getAuctionBidders(auctionId)
        for (user in requestUsers) {
            result += user.toDataModel()
        }
        return result
    }

    fun fetchAuctionUiState(auctionId: String){
        viewModelScope.launch {
            _auctionUiState.value = try {
                AuctionUiState.Success(fetchAuctionState(auctionId), fetchInsertionist(), isOwner())
            } catch (e: Exception){
                AuctionUiState.Error
            }
        }
    }


    private fun isOwner(): Boolean {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)
        return if(userId != null) {
            _auctionState.value.ownerId == userId
        } else{
            false
        }
    }

    private fun fetchAuctionState(auctionId: String): Auction {
        var auctionResponse = Auction()
        viewModelScope.launch {
            val getAuctionResponse = ApiService.getAuction(auctionId)
            if (getAuctionResponse.status.isSuccess()) {
                auctionResponse = Gson().fromJson(getAuctionResponse.bodyAsText(), com.CioffiDeVivo.dietideals.domain.requestModels.Auction::class.java).toDataModel()
                _auctionState.value = auctionResponse
            }
        }
        return auctionResponse
    }

    private fun fetchInsertionist(): User {
        var ownerResponse = User()
        viewModelScope.launch {
            val getUserInfoResponse = ApiService.getUserInfo(_auctionState.value.ownerId)
            if (getUserInfoResponse.status.isSuccess()) {
                ownerResponse = Gson().fromJson(getUserInfoResponse.bodyAsText(), com.CioffiDeVivo.dietideals.domain.requestModels.User::class.java).toDataModel()
            }
        }
        return ownerResponse
    }

    fun updateBidValue(value: String){
        if(value.isEmpty()){
            _bidState.value = _bidState.value.copy(
                value = 0F
            )
        } else{
            _bidState.value = _bidState.value.copy(
                value = value.toFloat()
            )
        }
    }

    fun submitBid() : Boolean {
        var createBidCallSuccessful  = false
        var validationSuccessful = false
        when(_auctionState.value.type){
            AuctionType.English -> {
                validationSuccessful = validateBidEnglish(_bidState.value, _auctionState.value)
            }
            AuctionType.Silent -> {
                validationSuccessful = validateBidSilent(_bidState.value, _auctionState.value)
            }
            else -> {}
        }
        if(validationSuccessful){
            val bid = _bidState.value.toRequestModel()
            val sharedPreferences = getApplication<Application>().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            bid.userId = sharedPreferences.getString("userId", null)
            bid.auctionId = _auctionState.value.id

            if (bid.userId != null) {
                viewModelScope.launch {
                    val createBidResponse = ApiService.createBid(bid)
                    createBidCallSuccessful = createBidResponse.status.isSuccess()
                }
            }
        }
        return createBidCallSuccessful && validationSuccessful
    }

    private fun validateBidEnglish(bid: Bid, auction: Auction) : Boolean {
        return bid.value > (auction.bids.last().value + auction.minStep.toFloat())
    }

    private fun validateBidSilent(bid: Bid, auction: Auction) : Boolean {
        return bid.value > auction.minAccepted.toFloat()
    }
}