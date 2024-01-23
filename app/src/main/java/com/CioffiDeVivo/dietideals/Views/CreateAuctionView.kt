package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.Components.DescriptionTextfield
import com.CioffiDeVivo.dietideals.Components.ViewTitle
import com.CioffiDeVivo.dietideals.Components.pulsateClick
import com.CioffiDeVivo.dietideals.DietiDealsViewModel
import com.CioffiDeVivo.dietideals.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateAuction(viewModel: DietiDealsViewModel, navController: NavController){

    var item: String by remember { mutableStateOf("") }
    var isTypeAuctionButton by remember { mutableIntStateOf(0) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row (
            modifier = Modifier.width(300.dp),

        ){

            AddingImagesOnCreateAuction()
        }
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = item,
            onValueChange = { item = it },
            singleLine = true,
            trailingIcon = {
                Icon(Icons.Rounded.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable{item = ""}
                )},
            modifier = Modifier.width(320.dp),
            label = { Text("Item Name") },
        )
        Spacer(modifier = Modifier.size(15.dp))
        Row {
            ElevatedButton(
                onClick = {
                    isTypeAuctionButton = 1
                          },
                modifier = Modifier
                    .width(100.dp)
                    .pulsateClick()
            ) {
                Text("Silent")
            }
            Spacer(modifier = Modifier.size(10.dp))
            ElevatedButton(
                onClick = {
                    isTypeAuctionButton = 2
                          },
                modifier = Modifier
                    .width(100.dp)
                    .pulsateClick()

            ) {
                Text("English")
            }
        }
        Spacer(modifier = Modifier.size(15.dp))
        if (isTypeAuctionButton == 1){
            SilentAuction()
        }
        else if (isTypeAuctionButton == 2){
            EnglishAuction()
        }

        Button(
            onClick = {  },
            modifier = Modifier
                .size(width = 330.dp, height = 50.dp)
                .pulsateClick(),
            content = {
                Text(stringResource(R.string.createAuction), fontSize = 20.sp)
            }
        )

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun CreateAuctionPreview(){
    CreateAuction(viewModel = DietiDealsViewModel(), navController = rememberNavController())
}

@Composable
fun SilentAuction(){

    var minAccepted: String by remember { mutableStateOf("Input") }
    var endingDate: String by remember { mutableStateOf("Input") }

    Row {
        OutlinedTextField(
            value = minAccepted,
            onValueChange = { minAccepted = it },
            singleLine = true,
            trailingIcon = {
                Icon(Icons.Rounded.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable{minAccepted = ""}
                )},
            label = { Text("Min. Accepted") },
            modifier = Modifier.width(150.dp)
        )
        Spacer(modifier = Modifier.size(20.dp))
        OutlinedTextField(
            value = endingDate,
            onValueChange = { endingDate = it },
            singleLine = true,
            trailingIcon = {
                Icon(Icons.Rounded.DateRange,
                    contentDescription = null,
                    modifier = Modifier.clickable{endingDate = ""}
                )},
            label = { Text("Ending Date") },
            modifier = Modifier.width(150.dp)

        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.size(width = 200.dp, height = 50.dp),
        ) {
            Text(
                "Add Auction",
                fontSize = 17.sp
            )
        }
    }
    Spacer(modifier = Modifier.size(15.dp))
    //DescriptionTextfield(maxDescriptionCharacters = 200)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnglishAuction(){

    var minStep: String by remember { mutableStateOf("Input") }
    var endingDate: String by remember { mutableStateOf("Input") }
    var interval: String by remember { mutableStateOf("Input") }

    Row {
        OutlinedTextField(
            value = minStep,
            onValueChange = { minStep = it },
            singleLine = true,
            trailingIcon = {
                Icon(Icons.Rounded.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable{minStep = ""}
                )},
            label = { Text("Min. Step") },
            modifier = Modifier.width(150.dp)
        )
        Spacer(modifier = Modifier.size(20.dp))
        OutlinedTextField(
            value = interval,
            onValueChange = { interval = it },
            singleLine = true,
            trailingIcon = {
                Icon(Icons.Rounded.DateRange,
                    contentDescription = null,
                    modifier = Modifier.clickable{interval = ""}
                )},
            label = { Text("Interval") },
            modifier = Modifier.width(150.dp)

        )
    }
    Spacer(modifier = Modifier.size(15.dp))
    OutlinedTextField(
        value = endingDate,
        onValueChange = { endingDate = it },
        singleLine = true,
        trailingIcon = {
            Icon(Icons.Rounded.Clear,
                contentDescription = null,
                modifier = Modifier.clickable{endingDate = ""}
            )},
        modifier = Modifier.width(320.dp),
        label = { Text("Ending Date") },
    )
    Spacer(modifier = Modifier.size(15.dp))
    //DescriptionTextfield(maxDescriptionCharacters = 200)
    Spacer(modifier = Modifier.height(30.dp))
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier.size(width = 200.dp, height = 50.dp),
    ) {
        Text(
            "Add Auction",
            fontSize = 17.sp
        )
    }

}

@Composable
fun ImagesOnCreateAuction(){
    Box(modifier = Modifier, contentAlignment = Alignment.TopEnd) {
        Image(
            painter = painterResource(id = R.drawable.placeholder),
            contentDescription = null,
            modifier = Modifier.size(width = 80.dp, height = 80.dp),
            alpha = 0.5F
        )
        IconButton(
            onClick = { /* On Click delete the image */ },
        ) {
            Icon(Icons.Default.Clear, contentDescription = null)
        }
    }
}

@Composable
fun AddingImagesOnCreateAuction(){
    Box(modifier = Modifier, contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.placeholder),
            contentDescription = null,
            modifier = Modifier.size(width = 80.dp, height = 80.dp),
            alpha = 0.5F
        )
        IconButton(
            onClick = { /* On Click add an image and another composable placeholder shows app for a
                maximum of 3 images */ },
        ) {
            Icon(Icons.Default.AddCircle, contentDescription = null)
        }
    }
}



