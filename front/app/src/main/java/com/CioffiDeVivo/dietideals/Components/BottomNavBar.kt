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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.CioffiDeVivo.dietideals.Views.Navigation.Screen


data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)

@Composable
fun BottomNavBar(modifier: Modifier = Modifier, selectedNavBarItem: MutableState<Int>, navController: NavController) {
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
                selected = selectedNavBarItem.value == index,
                onClick = {
                    selectedNavBarItem.value = index
                    when(index) {
                        0 -> navController.navigate(Screen.Home.route)
                        1 -> navController.navigate(Screen.Sell.route)
                        2 -> navController.navigate(Screen.Account.route)
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (index == selectedNavBarItem.value) {
                            item.selectedIcon
                        } else item.unselectedIcon,
                        contentDescription = item.title
                    )
                })
        }
    }
}