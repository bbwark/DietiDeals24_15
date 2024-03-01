package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CioffiDeVivo.dietideals.Components.ViewTitle
import com.CioffiDeVivo.dietideals.DietiDealsViewModel
import com.CioffiDeVivo.dietideals.Components.pulsateClick
import com.CioffiDeVivo.dietideals.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MakeABid(viewModel: DietiDealsViewModel){

    var bid by remember { mutableStateOf("Input") }
    val userBidState by viewModel.bidState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        MakeaBidEnglish()
        Spacer(modifier = Modifier.height(7.dp))
        Row {
            Text(
                "Placeholder",
                fontSize = 28.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                "EUR",
                fontSize = 28.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        OutlinedTextField(
            value = bid,
            onValueChange = { bid = it },
            singleLine = true,
            trailingIcon = {
                Icon(Icons.Rounded.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable{bid = ""}
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.width(250.dp),
            label = { Text("Your Bid") },
        )
        Spacer(modifier = Modifier.size(50.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .size(width = 200.dp, height = 60.dp)
                .pulsateClick()
        ) {
            Text("Make a Bid",
                fontSize = 20.sp
            )
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MakeaBidPreview(){
    MakeABid(viewModel = DietiDealsViewModel())
}

@Composable
fun MakeaBidEnglish() {
    ViewTitle(title = stringResource(id = R.string.lastBid))
}

@Composable
fun MakeaBidSilent(){
    ViewTitle(title = stringResource(id = R.string.minimumBid))
}