package com.CioffiDeVivo.dietideals.Components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsViewTopBar(
    caption: String,
    destinationRoute: String,
    navController: NavHostController
) {
    CenterAlignedTopAppBar(
        title = { Text(text = caption, fontSize = 30.sp, fontWeight = FontWeight(500)) },
        navigationIcon = {
            IconButton(onClick = { /*Navigate Back To DestinationRoute*/ }) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview (showBackground = true)
@Composable
fun AccountDetailsTopBarPreview() {
    DetailsViewTopBar(
        caption = "Manage Cards",
        destinationRoute = "Test",
        navController = rememberNavController()
    )
}