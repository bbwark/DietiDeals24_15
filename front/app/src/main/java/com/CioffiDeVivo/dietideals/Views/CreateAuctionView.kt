package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CalendarMonth
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
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.Components.DescriptionTextfield
import com.CioffiDeVivo.dietideals.Components.DetailsViewTopBar
import com.CioffiDeVivo.dietideals.Components.InputTextField
import com.CioffiDeVivo.dietideals.Components.ViewTitle
import com.CioffiDeVivo.dietideals.Components.pulsateClick
import com.CioffiDeVivo.dietideals.DataModels.AuctionTest
import com.CioffiDeVivo.dietideals.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.DietiDealsViewModel
import com.CioffiDeVivo.dietideals.Events.CreateAuctionEvents
import com.CioffiDeVivo.dietideals.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateAuction(viewModel: DietiDealsViewModel, navController: NavHostController){

    val createAuctionState by viewModel.auctionState.collectAsState()
    val itemAuctionState by viewModel.itemState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        DetailsViewTopBar(
            caption = stringResource(R.string.createAuction),
            destinationRoute = "",
            navController = navController
        )
        Row (
            modifier = Modifier.width(300.dp),
            horizontalArrangement = Arrangement.Center
        ){
            AddingImagesOnCreateAuction()
        }
        Spacer(modifier = Modifier.height(30.dp))
        InputTextField(
            value = itemAuctionState.name,
            onValueChanged = { viewModel.updateItemName(it) },
            label = stringResource(R.string.itemName),
            onDelete = { viewModel.deleteItemName() },
            modifier = modifierStandard
        )
        Spacer(modifier = Modifier.size(15.dp))
        Row {
            ElevatedButton(
                onClick = { viewModel.updateAuctionTypeToSilent(createAuctionState.auctionType) },
                modifier = Modifier
                    .width(100.dp)
                    .pulsateClick()
            ) {
                Text("Silent")
            }
            Spacer(modifier = Modifier.size(10.dp))
            ElevatedButton(
                onClick = { viewModel.updateAuctionTypeToEnglish(createAuctionState.auctionType) },
                modifier = Modifier
                    .width(100.dp)
                    .pulsateClick()

            ) {
                Text("English")
            }
        }
        Spacer(modifier = Modifier.size(15.dp))
        EnglishAuction(
            auction = createAuctionState,
            onDescriptionChange = { viewModel.updateDescriptionAuction(it) },
            onMinStepChange = { viewModel.updateMinStep(it) },
            onIntervalChange = { viewModel.updateInterval(it) },
            onEndingDateChange = { viewModel.updateEndingDate(it) },
            onDeleteDescription = { viewModel.deleteDescriptionAuction() },
            onDeleteInterval = { viewModel.deleteInterval() },
            onDeleteMinStep = { viewModel.deleteMinStep() }
        )
        if(createAuctionState.auctionType == AuctionType.Silent){
            SilentAuction(
                auction = createAuctionState,
                onMinAcceptedChange = { viewModel.updateMinAccepted(it) },
                onEndingDateChange = { viewModel.updateEndingDate(it) },
                onDescriptionChange = { viewModel.updateDescriptionAuction(it) },
                onDeleteDescription = { viewModel.deleteDescriptionAuction() },
                onDeleteMinAccepted = { viewModel.deleteMinAccepted() }
            )
        } else if (createAuctionState.auctionType == AuctionType.English){
            EnglishAuction(
                auction = createAuctionState,
                onDescriptionChange = { viewModel.updateDescriptionAuction(it) },
                onMinStepChange = { viewModel.updateMinStep(it) },
                onIntervalChange = { viewModel.updateInterval(it) },
                onEndingDateChange = { viewModel.updateEndingDate(it) },
                onDeleteDescription = { viewModel.deleteDescriptionAuction() },
                onDeleteInterval = { viewModel.deleteInterval() },
                onDeleteMinStep = { viewModel.deleteMinStep() }
            )
        }
        else{

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
fun SilentAuction(
    auction: AuctionTest,
    onMinAcceptedChange: (String) -> Unit,
    onEndingDateChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDeleteDescription: (String) -> Unit,
    onDeleteMinAccepted: (String) -> Unit
){
    Row {
        InputTextField(
            value = auction.minAccepted,
            onValueChanged = { onMinAcceptedChange(it) },
            label = stringResource(R.string.minStep),
            onDelete = { onDeleteMinAccepted(it) },
            modifier = Modifier.width(150.dp)
        )
        Spacer(modifier = Modifier.width(30.dp))
        InputTextField(
            value = auction.endingDate,
            onValueChanged = { onEndingDateChange(it) },
            label = stringResource(R.string.endingDate),
            trailingIcon = Icons.Filled.CalendarMonth,
            onDelete = {  },
            modifier = Modifier.width(150.dp)
        )
    }
    DescriptionTextfield(
        description = auction.description,
        descriptionOnChange = { onDescriptionChange(it) },
        maxDescriptionCharacters = 200,
        onDeleteDescription = { onDeleteDescription(it) }
    )

}

@Composable
fun EnglishAuction(
    auction: AuctionTest,
    onDescriptionChange: (String) -> Unit,
    onMinStepChange: (String) -> Unit,
    onIntervalChange: (String) -> Unit,
    onEndingDateChange: (String) -> Unit,
    onDeleteDescription: (String) -> Unit,
    onDeleteMinStep: (String) -> Unit,
    onDeleteInterval: (String) -> Unit,
){
    Row {
        InputTextField(
            value = auction.minStep,
            onValueChanged = { onMinStepChange(it) },
            label = stringResource(R.string.minStep),
            onDelete = { onDeleteMinStep(it) },
            modifier = Modifier.width(150.dp)
        )
        Spacer(modifier = Modifier.width(30.dp))
        InputTextField(
            value = auction.interval,
            onValueChanged = { onIntervalChange(it) },
            label = stringResource(R.string.interval),
            onDelete = { onDeleteInterval(it) },
            modifier = Modifier.width(150.dp)
        )
    }
    InputTextField(
        value = auction.endingDate,
        onValueChanged = { onEndingDateChange(it) },
        label = stringResource(R.string.endingDate),
        trailingIcon = Icons.Filled.CalendarMonth,
        onDelete = {  },
        modifier = modifierStandard
    )
    DescriptionTextfield(
        description = auction.description,
        descriptionOnChange = { onDescriptionChange(it) },
        maxDescriptionCharacters = 200,
        onDeleteDescription = { onDeleteDescription(it) }
    )
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



