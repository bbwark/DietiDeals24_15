package com.CioffiDeVivo.dietideals.presentation.common.sharedComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.CioffiDeVivo.dietideals.animations.pulsateClick
import com.CioffiDeVivo.dietideals.data.models.Auction
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen

@Composable
fun AuctionsListElement(auction: Auction, navController: NavController) {
    Row(modifier = Modifier.padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically){
        IconPlaceholder()
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate(Screen.Auction.route + "/${auction.id}") },
            text = auction.item.name)
    }
}

//Waiting for image system to implement images
@Composable
fun IconPlaceholder() {
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(Color.Red),
        contentAlignment = Alignment.Center
    ) {

    }
}

@Composable
fun HomeViewAuctionListElement(auction: Auction, navController: NavController){
    ElevatedCard(
        modifier = Modifier
            .pulsateClick()
            .clickable { navController.navigate(Screen.Auction.route + "/${auction.id}") }
    ) {
        Text(text = auction.item.name)
        // We have to implement an image system so we can delete this placeholder for testing
        Image(
            painter = painterResource(id = R.drawable.placeholder),
            contentDescription = null
        )
    }
}