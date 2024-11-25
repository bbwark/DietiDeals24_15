package com.CioffiDeVivo.dietideals.presentation.ui.account

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.presentation.ui.loading.LoadingView
import com.CioffiDeVivo.dietideals.presentation.ui.retry.RetryView
import com.example.compose.inversePrimaryLight
import com.example.compose.inversePrimaryLightMediumContrast
import com.example.compose.primaryContainerLight
import com.example.compose.surfaceDimLight

@Composable
fun AccountView(viewModel: AccountViewModel, navController: NavController) {

    val accountUiState by viewModel.accountUiState.collectAsState()

    LaunchedEffect(Unit){
        viewModel.setUserState()
    }

    when(accountUiState){
        is AccountUiState.Loading -> LoadingView()
        is AccountUiState.Error -> RetryView(
            onClick = {
                navController.popBackStack()
                navController.navigate(Screen.Account.route)
            }
        )
        is AccountUiState.SuccessLogout -> {
            if(navController.currentBackStackEntry?.destination?.route != Screen.Login.route){
                navController.navigate(Screen.Login.route){
                    popUpTo(0){ inclusive = true }
                }
            }
        }
        is AccountUiState.Success -> {
            val accountState = accountUiState as AccountUiState.Success
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                AccountViewTopBar(accountState.name, accountState.email)
                Spacer(modifier = Modifier.size(24.dp))
                AccountViewButton(
                    caption = "Edit Profile",
                    icon = Icons.Default.Settings,
                    onClick = {
                        if (navController.currentBackStackEntry?.destination?.route != Screen.EditProfile.route) {
                            navController.navigate(Screen.EditProfile.route) {
                                launchSingleTop
                            }
                        }
                    }
                )
                AccountViewButton(
                    caption = "Change Contact Informations",
                    icon = Icons.Default.Mail,
                    onClick = {
                        if (navController.currentBackStackEntry?.destination?.route != Screen.EditContactInfo.route) {
                            navController.navigate(Screen.EditContactInfo.route) {
                                launchSingleTop
                            }
                        }
                    }
                )
                AccountViewButton(
                    caption = "Manage Cards",
                    icon = Icons.Default.CreditCard,
                    onClick = {
                        if (navController.currentBackStackEntry?.destination?.route != Screen.ManageCards.route) {
                            navController.navigate(Screen.ManageCards.route) {
                                launchSingleTop
                            }
                        }
                    }
                )
                AccountViewButton(
                    caption = "Favourite Auctions",
                    icon = Icons.Default.Bookmark,
                    onClick = {
                        if (navController.currentBackStackEntry?.destination?.route != Screen.Favourites.route) {
                            navController.navigate(Screen.Favourites.route) {
                                launchSingleTop
                            }
                        }
                    }
                )
                if(accountState.isSeller){
                    AccountViewButton(
                        caption = "Your Auctions",
                        icon = Icons.Default.Sell,
                        onClick = {
                            if (navController.currentBackStackEntry?.destination?.route != Screen.Sell.route) {
                                navController.navigate(Screen.Sell.route) {
                                    launchSingleTop
                                }
                            }
                        }
                    )
                }
                AccountViewButton(
                    caption = "Log Out",
                    icon = Icons.AutoMirrored.Filled.ExitToApp,
                    showChevron = false,
                    destructiveAction = true,
                    onClick = {
                        viewModel.logOut()
                        if (navController.currentBackStackEntry?.destination?.route != Screen.Login.route) {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0){ inclusive = true }
                            }
                        }
                    }
                )
            }
        }
    }

}

@Composable
fun AccountViewTopBar(userName: String, userEmail: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .size(0.dp, 100.dp)
            .padding(horizontal = 12.dp)
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.size(6.dp))
        Column {
            Text(text = "Welcome $userName", fontSize = 20.sp, fontWeight = FontWeight(600))
            Text(text = userEmail, fontSize = 12.sp, fontWeight = FontWeight(400))
        }
    }
}

@Composable
fun AccountViewButton(
    caption: String,
    icon: ImageVector,
    showChevron: Boolean = true,
    destructiveAction: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 14.dp)
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))
        if(!destructiveAction) {
            Text(text = caption, fontSize = 18.sp, fontWeight = FontWeight(600))
        } else {
            Text(text = caption, color = Color(0xFFB60202), fontSize = 18.sp, fontWeight = FontWeight(600))
        }
        if (showChevron) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null
            )
        }
    }
}