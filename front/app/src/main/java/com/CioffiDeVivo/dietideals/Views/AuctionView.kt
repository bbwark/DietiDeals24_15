package com.CioffiDeVivo.dietideals.Views

import android.app.Application
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.Components.UserInfoBottomSheet
import com.CioffiDeVivo.dietideals.Components.ViewTitle
import com.CioffiDeVivo.dietideals.domain.DataModels.Auction
import com.CioffiDeVivo.dietideals.domain.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.domain.DataModels.Bid
import com.CioffiDeVivo.dietideals.domain.DataModels.Item
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.Views.Navigation.Screen
import com.CioffiDeVivo.dietideals.viewmodel.AuctionViewModel
import com.CioffiDeVivo.dietideals.viewmodel.SharedViewModel
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AuctionView(
    sharedState: Auction,
    viewModel: SharedViewModel,
    navController: NavHostController
) {
    val auctionState by viewModel.auctionState.collectAsState()
    val isOwner by viewModel.isOwnerState.collectAsState()
    val insertionist by viewModel.insertionsState.collectAsState()


    var userInfo by remember { mutableStateOf(false) }

    /*LaunchedEffect(Unit) {
        viewModel.fetchAuctionState()
        viewModel.fetchInsertionist()
        viewModel.fetchIsOwnerState()
    }*/

    val pagerState =
        rememberPagerState { 3 } //TODO To substitute it with the number of images in the array of images got from backend
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(state = pagerState) {
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp)
            )
        }

        AuctionHeader(
            itemName = auctionState.item.name,
            insertionistName = insertionist.name,
            type = auctionState.type,
            onUserInfo = { userInfo = true }
        )

        Row(
            Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            when (auctionState.type) {
                AuctionType.English -> EnglishAuctionBody(lastBid = auctionState.bids.lastOrNull())
                AuctionType.Silent -> auctionState.endingDate?.let { SilentAuctionBody(endingDate = it) }
                else ->{

                }
            }
            if (isOwner) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "View bid history",
                    Modifier.clickable { /* TODO navigate to Bid History view */ },
                    Color.Blue,
                    fontSize = 12.sp
                )
            }
        }

        if (!isOwner) {
            Spacer(modifier = Modifier.size(12.dp))
            Button(onClick = { /* TODO Navigates to Make A Bid View */
                viewModel.changeAuctionType(AuctionType.Silent)
                navController.navigate(Screen.MakeABid.route)
            }) {
                Text(text = "Make a Bid", fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.size(12.dp))
        }

        DescriptionAuctionItem(description = auctionState.description)
        if(userInfo) {
            UserInfoBottomSheet(
                user = insertionist,
                onDismissRequest = { userInfo = false }
            )
        }
        ViewTitle(title = "State: ${sharedState.type.name}")
    }
}

@Composable
fun AuctionHeader(modifier: Modifier = Modifier, itemName: String, insertionistName: String, type: AuctionType, onUserInfo: () -> Unit) {
    Row(
        Modifier.padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = itemName,
                fontSize = 24.sp,
                fontWeight = FontWeight(500)
            )
            Spacer(modifier = Modifier.size(3.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onUserInfo() }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
                Text(text = insertionistName, fontSize = 12.sp)
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = type.name,
            fontSize = 12.sp
        )
    }
}

@Composable
fun EnglishAuctionBody(lastBid: Bid?) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        if (lastBid != null) {
            Text(
                text = "${lastBid.value} EUR",
                fontSize = 32.sp,
                fontWeight = FontWeight(700)
            )
        } else {
            Text(
                text = "0 EUR",
                fontSize = 32.sp,
                fontWeight = FontWeight(700)
            )

        }
        Row(verticalAlignment = Alignment.Bottom) {
            Text(text = "Remaining Time: ", fontSize = 12.sp)
            Text(
                text = "50m",
                color = Color.Red,
                fontSize = 16.sp,
                fontWeight = FontWeight(500)
            ) //a function that calculates how much time has passed since the last bid, checks the auction interval, and put the remaining time
        }
    }
}

@Composable
fun SilentAuctionBody(endingDate: LocalDate) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Row(verticalAlignment = Alignment.Bottom){
            Text(text = "Ending Date: ", fontSize = 12.sp)
            Text(text = endingDate.toString(), fontWeight = FontWeight(500))
        }
        Row(verticalAlignment = Alignment.Bottom) {
            Text(text = "Remaining Time: ", fontSize = 12.sp)
            Text(
                text = "50m",
                color = Color.Red,
                fontSize = 16.sp,
                fontWeight = FontWeight(500)
            ) //a function that calculates how much time has passed since the last bid, checks the auction interval, and put the remaining time
        }
    }
}

@Composable
fun DescriptionAuctionItem(description: String) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(horizontal = 12.dp)) {
        Text(text = "About this item", fontSize = 16.sp, fontWeight = FontWeight(600))
        Spacer(modifier = Modifier.size(3.dp))
        Text(text = description, fontSize = 12.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun AuctionViewPreview() {
    val auction = Auction(
        "",
        "",
        Item(id = "", name = "Temporary Item"),
        endingDate = LocalDate.now().plusMonths(1),
        expired = false,
        type = AuctionType.English
    )
    val viewModel = SharedViewModel(Application())
    viewModel.setAuction(auction)
    AuctionView(sharedState = auction, viewModel = viewModel, navController = rememberNavController())
}