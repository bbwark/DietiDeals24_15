package com.CioffiDeVivo.dietideals.presentation.common.sharedComponents

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsViewTopBar(
    caption: String,
    navController: NavController
) {
    CenterAlignedTopAppBar(
        title = { Text(text = caption, fontSize = 30.sp, fontWeight = FontWeight(500)) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    )
}

fun shouldShowTopBar(route: String?): Boolean{
    return when{
        route == null -> false
        route.startsWith(Screen.Login.route) -> false
        route.startsWith(Screen.Register.route) -> false
        route.startsWith(Screen.Home.route) -> false
        route.startsWith(Screen.Account.route) -> false
        route.startsWith(Screen.Auction.route) -> false
        route.startsWith(Screen.Search.route) -> false
        route.startsWith(Screen.Favourites.route) -> false
        route.startsWith(Screen.Search.route) -> false
        route.startsWith(Screen.MakeABid.route) -> false
        route.startsWith(Screen.Sell.route) -> false
        else -> true
    }
}