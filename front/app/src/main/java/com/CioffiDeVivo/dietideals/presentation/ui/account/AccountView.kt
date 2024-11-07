package com.CioffiDeVivo.dietideals.presentation.ui.account

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.presentation.common.sharedViewmodels.LocalUserState
import com.CioffiDeVivo.dietideals.utils.EncryptedPreferencesManager

@Composable
fun AccountView(viewModel: AccountViewModel, navController: NavHostController) {

    val userState by viewModel.userState.collectAsState()
    val encryptedSharedPreferences = EncryptedPreferencesManager.getEncryptedPreferences()
    val name = encryptedSharedPreferences.getString("name", null)
    val email = encryptedSharedPreferences.getString("email", null)


    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if (name != null && email != null) {
            AccountViewTopBar(name, email)
        }
        Spacer(modifier = Modifier.size(24.dp))
        AccountViewButton(
            navController = navController,
            destinationRoute = Screen.EditProfile.route,
            caption = "Edit Profile",
            icon = Icons.Default.Settings
        )
        AccountViewButton(
            navController = navController,
            destinationRoute = Screen.EditContactInfo.route,
            caption = "Change Contact Informations",
            icon = Icons.Default.Mail
        )
        AccountViewButton(
            navController = navController,
            destinationRoute = Screen.ManageCards.route,
            caption = "Manage Cards",
            icon = Icons.Default.CreditCard
        )
        AccountViewButton(
            navController = navController,
            destinationRoute = Screen.Favourites.route,
            caption = "Favourite Auctions",
            icon = Icons.Default.Bookmark
        )
        AccountViewButton(
            navController = navController,
            destinationRoute = Screen.Sell.route,
            caption = "Your Auctions",
            icon = Icons.Default.Sell,
            onClick = { viewModel.selectedNavBarItem.value = 1 }
        )
        AccountViewButton(
            navController = navController,
            destinationRoute = Screen.Home.route,
            caption = "Sign Out",
            icon = Icons.AutoMirrored.Filled.ExitToApp,
            showChevron = false,
            destructiveAction = true
        )
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
    navController: NavHostController,
    destinationRoute: String,
    caption: String,
    icon: ImageVector,
    showChevron: Boolean = true,
    destructiveAction: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 14.dp)
            .clickable { navController.navigate(destinationRoute) }
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

@Preview (showBackground = true)
@Composable
fun AccountViewPreview() {
    AccountView(viewModel = AccountViewModel(), navController = rememberNavController())
}