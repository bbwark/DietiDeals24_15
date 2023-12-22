package com.CioffiDeVivo.dietideals.Views.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.CioffiDeVivo.dietideals.DietiDealsViewModel
import com.CioffiDeVivo.dietideals.Views.FavouritesView
import com.CioffiDeVivo.dietideals.Views.HomeView
import com.CioffiDeVivo.dietideals.Views.LoginView
import com.CioffiDeVivo.dietideals.Views.RegisterView

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
            HomeView()
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
            FavouritesView(user = viewModel.user)
        }
    }
}