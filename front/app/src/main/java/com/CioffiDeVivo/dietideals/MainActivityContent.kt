package com.CioffiDeVivo.dietideals

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.BottomNavigationBar
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.DetailsViewTopBar
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.shouldShowBottomBar
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.shouldShowTopBar
import com.CioffiDeVivo.dietideals.presentation.navigation.NavGraph
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.utils.AppViewModelFactory

@Composable
fun MainActivityContent(navController: NavHostController, appViewModelFactory: AppViewModelFactory){
    val routeForTopBarMap = mapOf(
        Screen.EditProfile.route to stringResource(R.string.editProfile),
        Screen.EditContactInfo.route to stringResource(R.string.contactInfo),
        Screen.ManageCards.route to stringResource(R.string.manageCards),
        Screen.CreateAuction.route to stringResource(R.string.createAuction),
        Screen.LogInCredentials.route to stringResource(R.string.logIn),
        Screen.RegisterCredentials.route to stringResource(R.string.createAccount),
        Screen.BidHistory.route to stringResource(R.string.bidHistory),
        Screen.AddCard.route to stringResource(R.string.addCard),
        Screen.BecomeSeller.route to stringResource(R.string.becomeSeller)
    )
    val currentRoute = remember { mutableStateOf<String?>(null) }
    val currentCaption = routeForTopBarMap[currentRoute.value] ?: ""
    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentRoute.value = destination.route
        }
    }
    val shouldShowNavBar = shouldShowBottomBar(currentRoute.value)
    val shouldShowTopDetailsBar = shouldShowTopBar(currentRoute.value)
    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = shouldShowTopDetailsBar,
                enter = fadeIn(animationSpec = tween(durationMillis = 300)) + slideInVertically(initialOffsetY = { -it }),
                exit = fadeOut(animationSpec = tween(durationMillis = 300)) + slideOutVertically(targetOffsetY = { -it })
            ) {
                DetailsViewTopBar(caption = currentCaption, navController = navController)
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = shouldShowNavBar,
                enter = fadeIn(animationSpec = tween(durationMillis = 300)) + slideInVertically(initialOffsetY = { it }),
                exit = fadeOut(animationSpec = tween(durationMillis = 300)) + slideOutVertically(targetOffsetY = { it })
            ) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            NavGraph(
                navController = navController,
                appViewModelFactory = appViewModelFactory
            )
        }
    }
}