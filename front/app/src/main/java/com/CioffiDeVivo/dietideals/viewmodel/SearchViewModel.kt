package com.CioffiDeVivo.dietideals.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.CioffiDeVivo.dietideals.domain.DataModels.Auction
import com.CioffiDeVivo.dietideals.domain.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.domain.DataModels.Bid
import com.CioffiDeVivo.dietideals.domain.DataModels.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.UUID

class SearchViewModel: ViewModel() {

    private val _searchedAuctionState = MutableStateFlow<ArrayList<Auction>>(arrayListOf())
    val searchedAuctionState : StateFlow<ArrayList<Auction>> = _searchedAuctionState.asStateFlow()
    //Stato della parola nella Search. Soluzioni: Stato delle auctions e della parola nella seach, oppure altra variale di stato String.
    //RESTAPI per la ricerca tramite keyword.

    var auctionSearchResult: Array<Auction> = arrayOf()

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

}