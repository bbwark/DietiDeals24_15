package com.CioffiDeVivo.dietideals.Views.Navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.CioffiDeVivo.dietideals.Components.BottomNavBar
import com.CioffiDeVivo.dietideals.DietiDealsViewModel
import com.CioffiDeVivo.dietideals.Views.FavouritesView
import com.CioffiDeVivo.dietideals.Views.HomeView
import com.CioffiDeVivo.dietideals.Views.LoginView
import com.CioffiDeVivo.dietideals.Views.RegisterView

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(navController: NavHostController, viewModel: DietiDealsViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            Scaffold(bottomBar = {
                BottomNavBar(
                    selectedNavBarItem = viewModel.selectedNavBarItem,
                    navController = navController
                )
            }) {
                Box(modifier = Modifier.padding(it)) {
                    HomeView()
                }
            }
        }
        composable(
            route = Screen.Login.route
        ) {
            LoginView()
        }
        composable(
            route = Screen.Register.route
        ) {
            RegisterView()
        }
        composable(
            route = Screen.Favourites.route
        ) {
            Scaffold(bottomBar = {
                BottomNavBar(
                    selectedNavBarItem = viewModel.selectedNavBarItem,
                    navController = navController
                )
            }) {
                Box(modifier = Modifier.padding(it)) {
                    FavouritesView(viewModel, navController)
                }
            }
        }
    }
}