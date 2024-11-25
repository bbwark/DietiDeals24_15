package com.CioffiDeVivo.dietideals.presentation.ui.favourites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.AuctionsListFavoured
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.presentation.ui.loading.LoadingView
import com.CioffiDeVivo.dietideals.presentation.ui.retry.RetryView

@Composable
fun FavouritesView(viewModel: FavouritesViewModel, navController: NavController) {

    var tabIndex: Int by remember { mutableIntStateOf(0) }
    val favouritesUiState by viewModel.favouritesUiState.collectAsState()

    LaunchedEffect(Unit){
        viewModel.fetchUserFavouriteAuction()
    }

    when(favouritesUiState){
        is FavouritesUiState.Loading -> LoadingView()
        is FavouritesUiState.Error -> RetryView(
            onClick = {
                navController.popBackStack()
                navController.navigate(Screen.Favourites.route)
            }
        )
        is FavouritesUiState.Success -> {
            Column(Modifier.fillMaxSize()) {
                FavouriteTabRow(
                    selectedTabIndex = tabIndex,
                    tabs = listOf("Active", "Finished"),
                    onTabChange = {
                        tabIndex = it
                    }
                )
                when (tabIndex) {
                    0 -> AuctionsListFavoured(
                        auctions = (favouritesUiState as FavouritesUiState.Success).favouritesAuctions.filter { !it.expired }.toTypedArray(),
                        navController = navController
                    )
                    1 -> AuctionsListFavoured(
                        auctions = (favouritesUiState as FavouritesUiState.Success).favouritesAuctions.filter { it.expired }.toTypedArray(),
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun FavouriteTabRow(selectedTabIndex: Int, tabs: List<String>, onTabChange: (Int) -> Unit) {
    TabRow(selectedTabIndex = selectedTabIndex) {
        tabs.forEachIndexed { index, title ->
            Tab(selected = selectedTabIndex == index, onClick = { onTabChange(index)}) {
                Text(modifier = Modifier.padding(vertical = 10.dp), text = title)
            }
        }
    }
}