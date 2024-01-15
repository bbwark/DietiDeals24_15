package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CioffiDeVivo.dietideals.Components.ViewTitle
import com.CioffiDeVivo.dietideals.DietiDealsViewModel
import com.CioffiDeVivo.dietideals.R

@Composable
fun LoginView(viewModel: DietiDealsViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()

    ) {
        Spacer(
            modifier = Modifier.height(40.dp))
        ViewTitle(title = stringResource(id = R.string.logInAccount))
        Spacer(modifier = Modifier.height(450.dp))

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.size(width = 330.dp, height = 50.dp),
            content = {
                Text(stringResource(R.string.continuewithEmail), fontSize = 20.sp)
            }
        )
        Spacer(
            modifier = Modifier.height(5.dp)
        )
        Text(
            "or",
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        )
        Spacer(
            modifier = Modifier.height(5.dp)
        )
        ExternalButtons()
        Spacer(
            modifier = Modifier.height(5.dp)
        )
        TextButton(onClick = { /*TODO*/ }) {
            Text(
                "Create an Account",
            )
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun LogInPreview(){
    LoginView(viewModel = DietiDealsViewModel())
}