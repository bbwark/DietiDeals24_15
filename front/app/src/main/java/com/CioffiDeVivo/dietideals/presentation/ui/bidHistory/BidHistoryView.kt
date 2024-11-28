package com.CioffiDeVivo.dietideals.presentation.ui.bidHistory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.CioffiDeVivo.dietideals.presentation.ui.bidHistory.components.BidHistoryElement
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.UserInfoBottomSheet
import com.CioffiDeVivo.dietideals.data.models.Auction
import com.CioffiDeVivo.dietideals.data.models.Bid
import com.CioffiDeVivo.dietideals.data.models.User
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.presentation.ui.loading.LoadingView
import com.CioffiDeVivo.dietideals.presentation.ui.retry.RetryView
import java.time.ZonedDateTime

@Composable
fun BidHistoryView(
    auctionId: String,
    viewModel: BidHistoryViewModel,
    navController: NavController
) {

    val bidHistoryUiState by viewModel.bidHistoryUiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAuctionBidders(auctionId)
    }

    when(bidHistoryUiState){
        is BidHistoryUiState.Loading -> LoadingView()
        is BidHistoryUiState.Success -> {
            BidHistoryLayout(
                auctionState = (bidHistoryUiState as BidHistoryUiState.Success).auction,
                bidders = (bidHistoryUiState as BidHistoryUiState.Success).bidders,
                acceptBid = { viewModel.chooseWinningBid(it) }
            )
        }
        is BidHistoryUiState.Error -> RetryView(
            onClick = {
                navController.popBackStack()
                navController.navigate(Screen.BidHistory.route)
            }
        )
        is BidHistoryUiState.SuccessOnWinningBid -> {
            if (navController.currentBackStackEntry?.destination?.route != Screen.Auction.route) {
                navController.popBackStack()
            }
        }
    }


}

@Composable
fun BidHistoryLayout(
    auctionState: Auction,
    bidders: Array<User>,
    acceptBid: (Bid) -> Unit
){
    var showDetails by remember { mutableStateOf(false) }
    var acceptOffer by remember { mutableStateOf(false) }
    var userInfo by remember { mutableStateOf(false) }

    var bidderName by remember { mutableStateOf("") }
    var selectedBid by remember { mutableStateOf(Bid("", 0f, "", ZonedDateTime.now())) }
    var selectedUser by remember { mutableStateOf(User("", "")) }
    if(bidders.isEmpty()){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "No Bidders Yet!",
                color = Color.Gray,
                fontSize = 20.sp,
                fontWeight = FontWeight(600),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 25.dp, end = 25.dp)
            )
        }
    } else{
        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                content = {
                    itemsIndexed(auctionState.bids) { index, bid ->
                        if (index == 0) {
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                        bidders.find { it.id == bid.userId }?.let {
                            BidHistoryElement(
                                auctionType = auctionState.type,
                                auctionExpired = auctionState.expired,
                                bidderName = it.name,
                                bidValue = bid.value,
                                onShowDetails = {
                                    bidderName = it.name
                                    selectedBid = bid
                                    showDetails = true
                                },
                                onAcceptOffer = {
                                    bidderName = it.name
                                    selectedBid = bid
                                    acceptOffer = true
                                },
                                onUserInfo = {
                                    selectedUser = it
                                    userInfo = true
                                }
                            )
                        }
                        HorizontalDivider()
                    }
                })
            if (showDetails) {
                ShowDetailsDialog(
                    selectedBid = selectedBid,
                    bidderName = bidderName,
                    onDismissRequest = { showDetails = false })
            }
            if (acceptOffer) {
                AcceptOfferDialog(
                    selectedBid = selectedBid,
                    bidderName = bidderName,
                    onDismissRequest = { acceptOffer = false },
                    onAcceptOffer = {
                        acceptBid(selectedBid)
                        acceptOffer = false
                    }
                )
            }
            if (userInfo) {
                UserInfoBottomSheet(
                    user = selectedUser,
                    onDismissRequest = { userInfo = false }
                )
            }
        }
    }
}

@Composable
fun ShowDetailsDialog(
    selectedBid: Bid,
    bidderName: String,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = "Cancel")
            }
        },
        title = {
            Text(text = "$bidderName's Bid:", fontSize = 24.sp)
        },
        text = {
            Column(horizontalAlignment = Alignment.Start) {
                Row {
                    Text(text = "Bid: ")
                    Text(text = selectedBid.value.toString() + " EUR", fontWeight = FontWeight(500))
                }
                Text(text = "Date: ${selectedBid.date.dayOfMonth}/${selectedBid.date.monthValue}/${selectedBid.date.year}")
                Text(text = "Time: ${selectedBid.date.hour}:${selectedBid.date.minute}")
            }
        }
    )
}

@Composable
fun AcceptOfferDialog(
    selectedBid: Bid,
    bidderName: String,
    onDismissRequest: () -> Unit,
    onAcceptOffer: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = {
                onAcceptOffer()
                onDismissRequest()
            }) {
                Text(text = "Accept", color = Color(0xFFB60202))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = "Cancel")
            }
        },
        title = {
            Text(text = "$bidderName's Bid:", fontSize = 24.sp)
        },
        text = {
            Column(horizontalAlignment = Alignment.Start) {
                Row {
                    Text(text = "Bid: ")
                    Text(text = selectedBid.value.toString() + " EUR", fontWeight = FontWeight(500))
                }
                Text(text = "Date: ${selectedBid.date.dayOfMonth}/${selectedBid.date.monthValue}/${selectedBid.date.year}")
                Text(text = "Time: ${selectedBid.date.hour}:${selectedBid.date.minute}")
            }
        }
    )
}