package com.CioffiDeVivo.dietideals.Views.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.DietiDealsViewModel
import com.CioffiDeVivo.dietideals.ui.theme.md_theme_light_secondary
import com.CioffiDeVivo.dietideals.ui.theme.md_theme_light_secondaryContainer

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AccountView(viewModel: DietiDealsViewModel, navController: NavHostController) {
    Column {
        AccountViewTopBar(viewModel.user.name, viewModel.user.email)
    }
}

@Composable
fun AccountViewTopBar(userName: String, userEmail: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .size(0.dp, 100.dp)
            .background(md_theme_light_secondaryContainer)
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


@RequiresApi(Build.VERSION_CODES.O)
@Preview (showBackground = true)
@Composable
fun AccountViewPreview() {
    AccountView(viewModel = DietiDealsViewModel(), navController = rememberNavController())
}