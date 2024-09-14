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

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private val _auctionState = MutableStateFlow(Auction())
    val auctionState: StateFlow<Auction> = _auctionState.asStateFlow()
    private val _isOwnerState = MutableStateFlow(false)
    val isOwnerState: StateFlow<Boolean> = _isOwnerState.asStateFlow()
    private val _insertionistState = MutableStateFlow(User())
    val insertionsState = _insertionistState.asStateFlow()
    private val _bidState = MutableStateFlow(Bid())
    val bidState: StateFlow<Bid> = _bidState.asStateFlow()

    //Test Shared
    private val _sharedState = MutableStateFlow(Auction())
    val sharedState = _sharedState.asStateFlow()
    fun changeAuctionType(auctionType: AuctionType){
        _sharedState.value = _sharedState.value.copy(
            type = auctionType
        )
    }

    private val _auctionBidders = MutableStateFlow<List<User>>(emptyList())
    val auctionBidders: StateFlow<List<User>> = _auctionBidders.asStateFlow()

    fun fetchAuctionBidders() {
        viewModelScope.launch {
            val getAuctionResponse = ApiService.getAuction(_auctionState.value.id)
            if (getAuctionResponse.status.isSuccess()) {
                _auctionState.value = Gson().fromJson(getAuctionResponse.bodyAsText(), com.CioffiDeVivo.dietideals.domain.requestModels.Auction::class.java).toDataModel()
                val bidders = getBiddersFromServer(_auctionState.value.id)
                _auctionBidders.value = bidders
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

    fun fetchAuctionState(auctionId: String) {
        viewModelScope.launch {
            val getAuctionResponse = ApiService.getAuction(auctionId)
            //Bisogna caricare l'auctionState grazie alla REST che effettua il fetch dell Auction
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

    fun deleteBidValue(){
        _bidState.value = _bidState.value.copy(
            value = 0.0f
        )
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