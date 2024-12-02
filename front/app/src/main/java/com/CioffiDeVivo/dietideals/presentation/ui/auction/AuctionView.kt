package com.CioffiDeVivo.dietideals.presentation.ui.auction

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.UserInfoBottomSheet
import com.CioffiDeVivo.dietideals.data.models.Auction
import com.CioffiDeVivo.dietideals.data.models.AuctionType
import com.CioffiDeVivo.dietideals.data.models.Bid
import com.CioffiDeVivo.dietideals.data.models.User
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.presentation.ui.loading.LoadingView
import com.CioffiDeVivo.dietideals.presentation.ui.retry.RetryView
import java.time.LocalDateTime

@Composable
fun AuctionView(
    auctionId: String,
    viewModel: AuctionViewModel,
    navController: NavHostController
) {
    val auctionUiState by viewModel.auctionUiState.collectAsState()

    LaunchedEffect(key1 = auctionId) {
        viewModel.fetchAuctionUiState(auctionId)
    }

    when(auctionUiState){
        is AuctionUiState.Loading -> LoadingView()
        is AuctionUiState.Success -> {
            AuctionViewLayout(
                auction = (auctionUiState as AuctionUiState.Success).auction,
                owner = (auctionUiState as AuctionUiState.Success).owner,
                isOwner = (auctionUiState as AuctionUiState.Success).isOwner,
                navController = navController
            )
        }
        is AuctionUiState.Error -> RetryView(
            onClick = {
                navController.popBackStack()
                navController.navigate(Screen.Auction.route + "/${auctionId}")
            }
        )
    }

}

@Composable
fun AuctionViewLayout(
    auction: Auction,
    owner: User,
    isOwner: Boolean,
    navController: NavController
){
    var userInfo by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState { auction.item.imageUrl.size }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
        ) { page ->
            val imageUrl = auction.item.imageUrl[page]
            Image(
                painter = rememberAsyncImagePainter(
                    model = imageUrl,
                    placeholder = rememberVectorPainter(image = Icons.Default.Image),
                    error = rememberVectorPainter(image = Icons.Default.Image)
                ),
                contentDescription = "Auction Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
            )
        }
        AuctionHeader(
            itemName = auction.item.name,
            ownerName = owner.name,
            type = auction.type,
            onUserInfo = { userInfo = true }
        )

        Row(
            Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            when (auction.type) {
                AuctionType.English -> EnglishAuctionBody(lastBid = auction.bids.lastOrNull())
                AuctionType.Silent -> auction.endingDate.let { SilentAuctionBody(endingDate = it) }
                else ->{

                }
            }
        }
        if(isOwner){
            Spacer(modifier = Modifier.size(12.dp))
            Button(onClick = {
                if (navController.currentBackStackEntry?.destination?.route != Screen.BidHistory.route + "/${auction.id}") {
                    navController.navigate(Screen.BidHistory.route + "/${auction.id}"){
                        launchSingleTop = true
                    }
                }
            }) {
                Text(text = "Bid History", fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.size(12.dp))
        } else{
            Spacer(modifier = Modifier.size(12.dp))
            Button(onClick = {
                if (navController.currentBackStackEntry?.destination?.route != Screen.MakeABid.route + "/${auction.id}") {
                    navController.navigate(Screen.MakeABid.route + "/${auction.id}") {
                        launchSingleTop = true
                    }
                }
            }) {
                Text(text = "Make a Bid", fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.size(12.dp))
        }
        DescriptionAuctionItem(description = auction.description)
        if(userInfo) {
            UserInfoBottomSheet(
                user = owner,
                onDismissRequest = { userInfo = false }
            )
        }
    }
}

@Composable
fun AuctionHeader(
    itemName: String,
    ownerName: String,
    type: AuctionType,
    onUserInfo: () -> Unit
) {
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
                Text(text = ownerName, fontSize = 12.sp)
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
            ) //TODO a function that calculates how much time has passed since the last bid, checks the auction interval, and put the remaining time
        }
    }
}

@Composable
fun SilentAuctionBody(endingDate: LocalDateTime) {
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
            ) //TODO a function that calculates how much time has passed since the last bid, checks the auction interval, and put the remaining time
        }
    }
}

@Composable
fun DescriptionAuctionItem(description: String) {

    Text(text = "About this item", fontSize = 16.sp, fontWeight = FontWeight(600))
    Spacer(modifier = Modifier.size(3.dp))
    Text(text = description, fontSize = 12.sp)

}