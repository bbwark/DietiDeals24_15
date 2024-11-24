package com.CioffiDeVivo.dietideals

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.BottomNavigationBar
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.DetailsViewTopBar
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.shouldShowBottomBar
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.shouldShowTopBar
import com.CioffiDeVivo.dietideals.presentation.navigation.NavGraph
import com.CioffiDeVivo.dietideals.utils.AppViewModelFactory

@Composable
fun MainActivityContent(navController: NavHostController, appViewModelFactory: AppViewModelFactory){
    val currentRoute = remember { mutableStateOf<String?>(null) }
    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentRoute.value = destination.route
        }
    }

    Scaffold(
        topBar = {
            if (shouldShowTopBar(currentRoute.value)) {
                DetailsViewTopBar(caption = "TEST", navController = navController)
            }
        },
        bottomBar = {
            if (shouldShowBottomBar(currentRoute.value)) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            appViewModelFactory = appViewModelFactory,
            modifier = Modifier.padding(innerPadding)
        )
    }
}