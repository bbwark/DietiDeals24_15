package com.CioffiDeVivo.dietideals.Views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CioffiDeVivo.dietideals.DataModels.Auction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeaBid(){
    var bid by remember { mutableStateOf("Input") }
    Row (
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            MakeaBidEnglish()
            Spacer(modifier = Modifier.height(7.dp))
            Row {
                Text(
                    "Placeholder",
                    fontSize = 28.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold
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
                modifier = Modifier.size(width = 200.dp, height = 60.dp)
            ) {
                Text("Make a Bid",
                    fontSize = 20.sp
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MakeaBidPreview(){
    MakeaBid()
}

@Composable
fun MakeaBidEnglish() {
    Text(
        "Last Bid:",
        fontSize = 30.sp,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium
    )
}

@Composable
fun MakeaBidSilent(){
    Text(
        "Minimum Bid:",
        fontSize = 30.sp,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium
    )
}