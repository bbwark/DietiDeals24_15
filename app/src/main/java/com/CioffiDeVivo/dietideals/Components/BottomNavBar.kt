package com.CioffiDeVivo.dietideals.Components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Sell
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.CioffiDeVivo.dietideals.Views.Navigation.Screen


data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)

@Composable
fun BottomNavBar(selectedNavBarItem: Int, onTabChange: (Int) -> Unit) {
    val items = listOf<BottomNavItem>(
        BottomNavItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = Screen.Home.route
        ),
        BottomNavItem(
            title = "Sell",
            selectedIcon = Icons.Filled.Sell,
            unselectedIcon = Icons.Outlined.Sell,
            route = Screen.Sell.route
        ),
        BottomNavItem(
            title = "Account",
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle,
            route = Screen.Account.route
        )
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedNavBarItem == index,
                onClick = { onTabChange(index) }, //Where the bar is used it has to be specified the behaviour (where to go and update the selected item)
                icon = {
                    Icon(
                        imageVector = if (index == selectedNavBarItem) {
                            item.selectedIcon
                        } else item.unselectedIcon,
                        contentDescription = item.title
                    )
                })
        }
    }
}