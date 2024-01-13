package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.Components.AuctionsList
import com.CioffiDeVivo.dietideals.Components.BottomNavBar
import com.CioffiDeVivo.dietideals.DataModels.Auction
import com.CioffiDeVivo.dietideals.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.DataModels.Item
import com.CioffiDeVivo.dietideals.DataModels.User
import com.CioffiDeVivo.dietideals.DietiDealsViewModel
import com.CioffiDeVivo.dietideals.ui.theme.DietiDealsTheme
import java.time.LocalDate
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FavouritesView(viewModel: DietiDealsViewModel, navController: NavHostController) {
    var tabIndex: Int by remember { mutableStateOf(0) }

    Column(Modifier.fillMaxSize()) {
        FavouriteTabRow(
            selectedTabIndex = tabIndex,
            tabs = listOf<String>("Active", "Finished"),
            onTabChange = {
                tabIndex = it
            } //or onTabChange = {selectedTab -> tabIndex = selectedTab}
        )
        when (tabIndex) {
            0 -> AuctionsList(
                modifier = Modifier.fillMaxHeight(),
                auctions = viewModel.user.favouriteAuctions.filter { it.endingDate.isAfter(LocalDate.now()) }
                    .toTypedArray(),
                navController = navController,
                viewModel = viewModel
            ) //ActiveAuctions
            1 -> AuctionsList(
                modifier = Modifier.fillMaxHeight(),
                auctions = viewModel.user.favouriteAuctions.filter {
                    it.endingDate.isBefore(LocalDate.now().plusDays(1))
                }.toTypedArray(),
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
        FavouritesView(viewModel = DietiDealsViewModel(), navController = rememberNavController())
    }
}