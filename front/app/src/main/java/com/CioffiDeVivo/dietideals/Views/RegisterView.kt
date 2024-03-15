 package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.Components.GoogleButton
import com.CioffiDeVivo.dietideals.Components.ViewTitle
import com.CioffiDeVivo.dietideals.Components.pulsateClick
import com.CioffiDeVivo.dietideals.DietiDealsViewModel
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.Views.Navigation.Screen

 @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
 @Composable
fun RegisterView(viewModel: DietiDealsViewModel, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(
            modifier = Modifier.height(40.dp))
        ViewTitle(title = stringResource(id = R.string.createAccount))
        Spacer(modifier = Modifier.height(450.dp))

        Button(
            onClick = {
                      navController.navigate(Screen.RegisterCredentials.route)
            },
            modifier = Modifier
                .size(width = 330.dp, height = 50.dp)
                .pulsateClick(),
            content = {
                Text(stringResource(R.string.continuewithEmail), fontSize = 20.sp)
            }
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text("or",
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(5.dp))
        GoogleButton(navController)
    }
}

@Composable
fun ExternalButtons(){
    OutlinedButton(
        onClick = {},
        modifier = Modifier
            .size(width = 330.dp, height = 50.dp)
            .pulsateClick(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        content = {
            Image(
                painter = painterResource(id = R.drawable.logogoogle),
                contentDescription = null,
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                stringResource(R.string.continuewithGoogle),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

    )
    Spacer(
        modifier = Modifier.height(5.dp)
    )
    Spacer(
        modifier = Modifier.height(5.dp)
    )
    OutlinedButton(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .size(width = 330.dp, height = 50.dp)
            .pulsateClick(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        content = {
            Image(
                painter = painterResource(id = R.drawable.logofacebook),
                contentDescription = null,
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                stringResource(R.string.continuewithFacebook),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

        }
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun RegisterPreview(){
    RegisterView(viewModel = DietiDealsViewModel(), navController = rememberNavController())
}