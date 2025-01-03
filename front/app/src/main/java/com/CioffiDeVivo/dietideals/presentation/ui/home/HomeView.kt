package com.CioffiDeVivo.dietideals.presentation.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.HomeViewAuctionsList
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.ViewTitle
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.data.models.Auction
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.presentation.ui.loading.LoadingView
import com.CioffiDeVivo.dietideals.presentation.ui.retry.RetryView
import com.CioffiDeVivo.dietideals.utils.trackScreen

@Composable
fun HomeView(viewModel: HomeViewModel, navController: NavController){
    val homeUiState by viewModel.homeUiState.collectAsState()

    LaunchedEffect(Unit){
        viewModel.fetchHomeAuctions()
    }
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        ViewTitle(title = stringResource(id = R.string.dietideals))
        Spacer(modifier = Modifier.height(15.dp))
        ElevatedButton(
            onClick = {
                if (navController.currentBackStackEntry?.destination?.route != Screen.Search.route) {
                    navController.navigate(Screen.Search.route)
                }
            },
            modifier = Modifier.size(width = 330.dp, height = 50.dp),
            content = {
                Icon(Icons.Rounded.Search, contentDescription = null)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    "Search on DietiDeals",
                    fontSize = 15.sp,
                    color = Color.Gray,
                )
                Spacer(modifier = Modifier.width(110.dp))
            }
        )
        Spacer(modifier = Modifier.height(50.dp))

        when(homeUiState){
            is HomeUiState.Loading -> LoadingView()
            is HomeUiState.Success -> {
                RandomAuctions((homeUiState as HomeUiState.Success).randomAuction, navController)
                Spacer(modifier = Modifier.height(35.dp))
                EndingAuctions((homeUiState as HomeUiState.Success).endingAuction, navController)
                Spacer(modifier = Modifier.height(35.dp))
                ParticipatedAuctions((homeUiState as HomeUiState.Success).participatedAuction, navController)
            }
            is HomeUiState.Error -> RetryView(
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.Home.route)
                }
            )
        }
    }
}

@Composable
fun RandomAuctions(
    randomAuctions: Array<Auction>,
    navController: NavController,
){
    if(randomAuctions.isNotEmpty()){
        Text(
            "Random Auctions",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.height(10.dp))
        HomeViewAuctionsList(
            auctions = randomAuctions,
            navController = navController
        )
    }
}

@Composable
fun EndingAuctions(
    endingAuctions: Array<Auction>,
    navController: NavController,
) {
    if(endingAuctions.isNotEmpty()){
        Text(
            "Ending Auctions",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.height(10.dp))
        HomeViewAuctionsList(
            auctions = endingAuctions,
            navController = navController
        )
    }
}

@Composable
fun ParticipatedAuctions(
    participatedAuctions: Array<Auction>,
    navController: NavController,
){
    if(participatedAuctions.isNotEmpty()){
        Text(
            "Participated Auctions",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.height(10.dp))
        HomeViewAuctionsList(
            auctions = participatedAuctions,
            navController = navController
        )
    }
}