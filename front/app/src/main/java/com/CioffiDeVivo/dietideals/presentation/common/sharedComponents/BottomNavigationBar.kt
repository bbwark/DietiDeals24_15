package com.CioffiDeVivo.dietideals.presentation.common.sharedComponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen


sealed class BottomNavBarItem(val name: String, val icon: ImageVector, val route: String){
    object Home: BottomNavBarItem("Home", Icons.Default.Home, Screen.Home.route)
    object Sell: BottomNavBarItem("Sell", Icons.Default.Sell, Screen.Sell.route)
    object Profile: BottomNavBarItem("Profile", Icons.Default.Person, Screen.Account.route)

}

@Composable
fun BottomNavigationBar(navController: NavController){
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val items = listOf(BottomNavBarItem.Home, BottomNavBarItem.Sell, BottomNavBarItem.Profile)

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route){
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = { Icon(item.icon, null) },
                label = { Text(item.name) }
            )
        }

    }
}