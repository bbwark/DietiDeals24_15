package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.Components.AuctionsListFavoured
import com.CioffiDeVivo.dietideals.ui.theme.DietiDealsTheme
import com.CioffiDeVivo.dietideals.viewmodel.FavoritesViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FavouritesView(viewModel: FavoritesViewModel, navController: NavHostController) {
    var tabIndex: Int by remember { mutableStateOf(0) }

    val userState by viewModel.userState.collectAsState()
    val favouredAuctionsState by viewModel.favouredAuctionState.collectAsState()

    Column(Modifier.fillMaxSize()) {
        FavouriteTabRow(
            selectedTabIndex = tabIndex,
            tabs = listOf<String>("Active", "Finished"),
            onTabChange = {
                tabIndex = it
            } //or onTabChange = {selectedTab -> tabIndex = selectedTab}
        )
        when (tabIndex) {
            //viewModel userState favoured auctions
            0 -> AuctionsListFavoured(
                auctions = viewModel.user.favouriteAuctions.filter { !it.expired }.toTypedArray(),
                navController = navController,
                viewModel = viewModel
            ) //ActiveAuctions
            1 -> AuctionsListFavoured(
                auctions = viewModel.user.favouriteAuctions.filter { it.expired }.toTypedArray(),
                navController = navController,
                viewModel = viewModel
            ) //FinishedAuctions
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


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun FavouritesViewPreview(){
    DietiDealsTheme {
        FavouritesView(viewModel = FavoritesViewModel(), navController = rememberNavController())
    }
}