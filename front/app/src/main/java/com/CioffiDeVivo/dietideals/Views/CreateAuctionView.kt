package com.CioffiDeVivo.dietideals.Views

import android.content.Context
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.CioffiDeVivo.dietideals.Components.DescriptionTextfield
import com.CioffiDeVivo.dietideals.Components.InputTextField
import com.CioffiDeVivo.dietideals.Components.pulsateClick
import com.CioffiDeVivo.dietideals.DataModels.Auction
import com.CioffiDeVivo.dietideals.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.DietiDealsViewModel
import com.CioffiDeVivo.dietideals.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.time.LocalDate

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateAuction(viewModel: DietiDealsViewModel, navController: NavHostController){

    val createAuctionState by viewModel.auctionState.collectAsState()
    val itemAuctionState by viewModel.itemState.collectAsState()
    val permissionState = rememberPermissionState(
        permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
    )
    SideEffect {
        permissionState.launchPermissionRequest()
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        AddingImagesOnCreateAuction(viewModel = viewModel)

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
                onClick = { viewModel.updateAuctionTypeToSilent() },
                modifier = Modifier
                    .width(100.dp)
                    .pulsateClick()
            ) {
                Text("Silent")
            }
            Spacer(modifier = Modifier.size(10.dp))
            ElevatedButton(
                onClick = { viewModel.updateAuctionTypeToEnglish() },
                modifier = Modifier
                    .width(100.dp)
                    .pulsateClick()

            ) {
                Text("English")
            }
        }
        Spacer(modifier = Modifier.size(15.dp))
        when (createAuctionState.auctionType) {
            AuctionType.Silent -> {
                SilentAuction(
                    auction = createAuctionState,
                    onMinAcceptedChange = { viewModel.updateMinAccepted(it) },
                    onEndingDateChange = { viewModel.updateEndingDate(LocalDate.parse(it)) },
                    onDescriptionChange = { viewModel.updateDescriptionAuction(it) },
                    onDeleteDescription = { viewModel.deleteDescriptionAuction() },
                    onDeleteMinAccepted = { viewModel.deleteMinAccepted() }
                )
            }
            AuctionType.English -> {
                EnglishAuction(
                    auction = createAuctionState,
                    onDescriptionChange = { viewModel.updateDescriptionAuction(it) },
                    onMinStepChange = { viewModel.updateMinStep(it) },
                    onIntervalChange = { viewModel.updateInterval(it) },
                    onEndingDateChange = { viewModel.updateEndingDate(LocalDate.parse(it)) },
                    onDeleteDescription = { viewModel.deleteDescriptionAuction() },
                    onDeleteInterval = { viewModel.deleteInterval() },
                    onDeleteMinStep = { viewModel.deleteMinStep() }
                )
            }
            else -> {

            }
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
    auction: Auction,
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
            value = auction.endingDate.toString(),
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
    auction: Auction,
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
        value = auction.endingDate.toString(),
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
fun ImageItem(
    uri: Uri?,
    onClick: () -> Unit,
    context: Context
){
    Box {
        Image(
            painter = rememberAsyncImagePainter(uri),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(width = 80.dp, height = 80.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable { },

            contentDescription = null
        )
        IconButton(
            onClick = {
                      onClick()
                Toast.makeText(context, "Image Removed", Toast.LENGTH_SHORT).show()
            },
        ) {
            Icon(Icons.Filled.BrokenImage, contentDescription = null, tint = Color.White)
        }
    }
    Spacer(modifier = Modifier.width(5.dp))
}

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddingImagesOnCreateAuction(viewModel: DietiDealsViewModel) {

    val itemAuctionState by viewModel.itemState.collectAsState()
    val context = LocalContext.current
    val multiPhotosPickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            viewModel.updateImagesUri(it)
        }
    val permissionState = rememberPermissionState(
        permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
    )
    SideEffect {
        permissionState.launchPermissionRequest()
    }

    LazyRow {
        itemsIndexed(itemAuctionState.imagesUri) { index, uri ->
            Spacer(modifier = Modifier.width(10.dp))
            ImageItem(
                uri = uri,
                onClick = { viewModel.deleteImageUri(index) },
                context = context
            )
        }
    }
    Spacer(modifier = Modifier.height(30.dp))
    Button(
        onClick = {
                  if (permissionState.status.isGranted){
                      multiPhotosPickerLauncher.launch("image/*")
                      Toast.makeText(context, "Choose Images", Toast.LENGTH_SHORT).show()
                  } else {
                      permissionState.launchPermissionRequest()
                  }
        },
        enabled = itemAuctionState.imagesUri.size < 5
    ) {
        Icon(imageVector = Icons.Default.ImageSearch, contentDescription = "Gallery Icon")
        Text(text = "Add Image")
    }
}





