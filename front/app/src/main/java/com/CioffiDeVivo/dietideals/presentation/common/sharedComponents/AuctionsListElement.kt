package com.CioffiDeVivo.dietideals.presentation.common.sharedComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.CioffiDeVivo.dietideals.animations.pulsateClick
import com.CioffiDeVivo.dietideals.data.models.Auction
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.data.models.Item
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen

@Composable
fun AuctionsListElement(auction: Auction, navController: NavController) {
    Row(
        modifier = Modifier
            .height(80.dp)
            .clickable {
                if (navController.currentBackStackEntry?.destination?.route != Screen.Auction.route + "/${auction.id}") {
                    navController.navigate(Screen.Auction.route + "/${auction.id}")
                }
            },
        verticalAlignment = Alignment.CenterVertically)
    {
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .size(60.dp)
        ){
            Image(
                painter = rememberAsyncImagePainter(
                    model = auction.item.imageUrl[0],
                    placeholder = rememberVectorPainter(image = Icons.Default.Image),
                    error = rememberVectorPainter(image = Icons.Default.Image)
                ),
                contentDescription = "Auction Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = auction.item.name)
    }
}

@Composable
fun HomeViewAuctionListElement(auction: Auction, navController: NavController){
    ElevatedCard(
        modifier = Modifier
            .pulsateClick()
            .clickable {
                if (navController.currentBackStackEntry?.destination?.route != Screen.Auction.route + "/${auction.id}") {
                    navController.navigate(Screen.Auction.route + "/${auction.id}")
                }
                navController.navigate(Screen.Auction.route + "/${auction.id}")
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = auction.item.name,
                modifier = Modifier.padding(5.dp)
            )
            Image(
                painter = rememberAsyncImagePainter(
                    model = auction.item.imageUrl[0],
                    placeholder = rememberVectorPainter(image = Icons.Default.Image),
                    error = rememberVectorPainter(image = Icons.Default.Image)
                ),
                contentDescription = "Auction Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewAuctionListElementPreview(){
    HomeViewAuctionListElement(
        auction = Auction("", "", Item("", "TEST NAME")),
        navController = rememberNavController())
}