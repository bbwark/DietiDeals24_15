package com.CioffiDeVivo.dietideals.presentation.ui.sell.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.domain.models.Auction
import com.CioffiDeVivo.dietideals.domain.models.Item
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen

@Composable
fun SellGridElement(auction: Auction, modifier: Modifier = Modifier, navController: NavController) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .size(width = 160.dp, height = 170.dp)
            .clickable { navController.navigate(Screen.Auction.route + "/${auction.id}") }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardHeader(
                auction = auction,
                navController = navController
            )
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun CardHeader(auction: Auction, navController: NavController) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = auction.item.name,
            modifier = Modifier
                .weight(1f)
                .clickable { navController.navigate(Screen.Auction.route + "/${auction.id}") },
            fontSize = 14.sp,
            fontWeight = FontWeight(500),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        IconButton(onClick = { /*restapi to make delete of the auction*/ }, modifier = Modifier.size(20.dp)) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SellGridElementPreview() {
    SellGridElement(
        auction = Auction(item = Item(name = "Auction Test")),
        navController = rememberNavController()
    )
}