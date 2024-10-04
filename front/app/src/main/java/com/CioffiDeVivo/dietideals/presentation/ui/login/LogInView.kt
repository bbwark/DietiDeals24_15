package com.CioffiDeVivo.dietideals.presentation.ui.login

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.ViewTitle
import com.CioffiDeVivo.dietideals.animations.pulsateClick
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.GoogleButton
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.presentation.ui.register.ExternalButtons

@Composable
fun LoginView(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)

    ) {
        ViewTitle(title = stringResource(id = R.string.logInAccount))
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { navController.navigate(Screen.LogInCredentials.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .pulsateClick(),
            content = {
                Text(stringResource(R.string.continuewithEmail), fontSize = 20.sp)
            }
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            "or",
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        GoogleButton(navController = navController)
        Spacer(modifier = Modifier.height(5.dp))
        TextButton(onClick = { /*TODO*/ }) {
            Text(
                "Create an Account",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LogInPreview(){
    LoginView(navController = rememberNavController())
}