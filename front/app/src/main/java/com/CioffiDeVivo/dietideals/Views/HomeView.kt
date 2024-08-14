package com.CioffiDeVivo.dietideals.Views

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.Components.HomeViewAuctionsList
import com.CioffiDeVivo.dietideals.Components.ViewTitle
import com.CioffiDeVivo.dietideals.Components.pulsateClick
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.domain.DataModels.Auction
import com.CioffiDeVivo.dietideals.viewmodel.HomeViewModel

@Composable
fun HomeView(viewModel: HomeViewModel, navController: NavHostController){
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(18.dp))
        IconButton(
            onClick = { /*TODO navigate to favourite view*/ },
            modifier = Modifier.pulsateClick()
            ) {
            Icon(Icons.Rounded.Favorite, contentDescription = null)
        }
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
            onClick = { /*TODO navigate to search view*/ },
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
        LatestAuctions(viewModel.getLatestAuctions(), navController)
        Spacer(modifier = Modifier.height(35.dp))
        EndingAuctions(viewModel.getEndingAuctions(), navController)
        Spacer(modifier = Modifier.height(35.dp))
        ParticipatedAuctions(viewModel.getParticipatedAuctions(), navController)
    }

}

@Composable
fun LatestAuctions(
    latestAuctions: Array<Auction>,
    navController: NavHostController,
){
    Text(
        "Latest Auctions",
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
    )
    Spacer(modifier = Modifier.height(10.dp))
    HomeViewAuctionsList(
        auctions = latestAuctions,
        navController = navController
    )
}

@Composable
fun EndingAuctions(
    endingAuctions: Array<Auction>,
    navController: NavHostController,
) {
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

@Composable
fun ParticipatedAuctions(
    participatedAuctions: Array<Auction>,
    navController: NavHostController,
){
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

@Preview(showBackground = true)
@Composable
fun HomeViewPreview(){
    HomeView(viewModel = HomeViewModel(Application()), navController = rememberNavController())
}