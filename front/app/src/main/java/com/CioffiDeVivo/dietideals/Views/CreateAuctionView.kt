package com.CioffiDeVivo.dietideals.Views

import android.content.Context
import android.content.UriMatcher
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Euro
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.CioffiDeVivo.dietideals.Components.DescriptionTextfield
import com.CioffiDeVivo.dietideals.Components.InputTextField
import com.CioffiDeVivo.dietideals.Components.CustomDatePickerDialog
import com.CioffiDeVivo.dietideals.Components.pulsateClick
import com.CioffiDeVivo.dietideals.domain.DataModels.Auction
import com.CioffiDeVivo.dietideals.domain.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.viewmodel.MainViewModel
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.utils.BidInputVisualTransformation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CreateAuction(viewModel: MainViewModel, navController: NavHostController){

    var minAccepted by remember { mutableStateOf("") }
    var minStep by remember { mutableStateOf("") }
    val showDatePicker = remember { mutableStateOf(false) }
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
            onTrailingIconClick = { viewModel.deleteItemName() },
            modifier = modifierStandard
        )
        Spacer(modifier = Modifier.size(15.dp))
        Row {
            ElevatedButton(
                onClick = { viewModel.updateAuctionTypeToSilent() },
                colors = if (createAuctionState.auctionType == AuctionType.Silent){
                    ButtonDefaults.buttonColors()
                }else{
                     ButtonDefaults.elevatedButtonColors()
                     },
                modifier = Modifier
                    .width(100.dp)
                    .pulsateClick()

            ) {
                Text("Silent")
            }
            Spacer(modifier = Modifier.size(10.dp))
            ElevatedButton(
                onClick = { viewModel.updateAuctionTypeToEnglish() },
                colors = if (createAuctionState.auctionType == AuctionType.English){
                    ButtonDefaults.buttonColors()
                }else{
                    ButtonDefaults.elevatedButtonColors()
                },
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
                    onBidChange = {
                        minAccepted = if (it.startsWith("0")) {
                            ""
                        } else {
                            it
                        }
                        viewModel.updateMinAccepted(minAccepted)
                    },
                    onDescriptionChange = { viewModel.updateDescriptionAuction(it) },
                    onDeleteDescription = { viewModel.deleteDescriptionAuction() },
                    onCalendarClick = { showDatePicker.value = true }
                )
            }
            AuctionType.English -> {
                EnglishAuction(
                    auction = createAuctionState,
                    onBidChange = {
                        minStep = if (it.startsWith("0")) {
                            ""
                        } else {
                            it
                        }
                        viewModel.updateMinStep(minStep)
                    },
                    onIntervalChange = { viewModel.updateInterval(it) },
                    onDescriptionChange = { viewModel.updateDescriptionAuction(it) },
                    onDeleteInterval = { viewModel.deleteInterval() },
                    onDeleteDescription = { viewModel.deleteDescriptionAuction() },
                    onCalendarClick = { showDatePicker.value = true }
                )
            }
            else -> {

            }
        }
        if(showDatePicker.value){
            CustomDatePickerDialog(
                onAccept = {
                    showDatePicker.value = false
                    if (it != null){
                        viewModel.updateEndingDate(it)
                    }
                },
                onCancel = { showDatePicker.value = false }
            )
        }
        Button(
            onClick = {/*ADD AUCTION WITH A SPECIFIC METHOD FOR DIFFERENCES BETWEEN ENGLISH AND SILENT*/ },
            modifier = Modifier
                .size(width = 330.dp, height = 50.dp)
                .pulsateClick(),
            content = {
                Text(stringResource(R.string.createAuction), fontSize = 20.sp)
            }
        )

    }
}

@Preview(showBackground = true)
@Composable
fun CreateAuctionPreview(){
    CreateAuction(viewModel = MainViewModel(), navController = rememberNavController())
}


@Composable
fun SilentAuction(
    auction: Auction,
    onBidChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDeleteDescription: (String) -> Unit,
    onCalendarClick: () -> Unit
){
    Row {
        OutlinedTextField(
            value = auction.minAccepted,
            onValueChange = { onBidChange(it) },
            singleLine = true,
            trailingIcon = {
                Icon(
                    Icons.Filled.Euro,
                    contentDescription = null,
                )
            },
            visualTransformation = BidInputVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier.width(150.dp),
            label = { Text(stringResource(R.string.minimumBid)) },
        )
        Spacer(modifier = Modifier.width(30.dp))
        InputTextField(
            value = auction.endingDate!!.format(DateTimeFormatter.ISO_LOCAL_DATE),
            onValueChanged = { },
            label = stringResource(R.string.endingDate),
            onTrailingIconClick = { onCalendarClick() },
            modifier = Modifier.width(150.dp),
            readOnly = true,
            trailingIcon = Icons.Filled.CalendarMonth,
        )
    }
    DescriptionTextfield(
        description = auction.description,
        onDescriptionChange = { onDescriptionChange(it) },
        maxDescriptionCharacters = 200,
        onDeleteDescription = { onDeleteDescription(it) }
    )

}

@Composable
fun EnglishAuction(
    auction: Auction,
    onBidChange: (String) -> Unit,
    onIntervalChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDeleteInterval: (String) -> Unit,
    onDeleteDescription: (String) -> Unit,
    onCalendarClick: () -> Unit

){
    Row {
        OutlinedTextField(
            value = auction.minStep,
            onValueChange = { onBidChange(it) },
            singleLine = true,
            trailingIcon = {
                Icon(
                    Icons.Filled.Euro,
                    contentDescription = null,
                )
            },
            visualTransformation = BidInputVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier.width(150.dp),
            label = { Text(stringResource(R.string.minStep)) },
        )
        Spacer(modifier = Modifier.width(30.dp))
        InputTextField(
            value = auction.interval,
            onValueChanged = { onIntervalChange(it) },
            label = stringResource(R.string.interval),
            onTrailingIconClick = { onDeleteInterval(it) },
            modifier = Modifier.width(150.dp)
        )
    }
    InputTextField(
        value = auction.endingDate!!.format(DateTimeFormatter.ISO_LOCAL_DATE),
        onValueChanged = {  },
        label = stringResource(R.string.endingDate),
        trailingIcon = Icons.Filled.CalendarMonth,
        onTrailingIconClick = { onCalendarClick() },
        readOnly = true,
        modifier = Modifier.width(325.dp)
    )
    DescriptionTextfield(
        description = auction.description,
        onDescriptionChange = { onDescriptionChange(it) },
        maxDescriptionCharacters = 200,
        onDeleteDescription = { onDeleteDescription(it) }
    )
}

@Composable
fun ImageItem(
    uri: Uri?,
    onClick: () -> Unit,
    context: Context,
    iconButton: ImageVector = Icons.Filled.BrokenImage,
    modifier: Modifier
){
    val showDialog = remember { mutableStateOf(false) }
    Box {
        Image(
            painter = rememberAsyncImagePainter(uri),
            contentScale = ContentScale.FillBounds,
            modifier = modifier
                .size(width = 80.dp, height = 80.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable { showDialog.value = true },

            contentDescription = null
        )
        IconButton(
            onClick = {
                onClick()
            },
        ) {
            Icon(iconButton, contentDescription = null, tint = Color.White)
        }
    }
    Spacer(modifier = Modifier.width(5.dp))
    if(showDialog.value){
        DialogImage(
            uri = uri,
            onDismissRequest = { showDialog.value = false }
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddingImagesOnCreateAuction(viewModel: MainViewModel) {

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
                onClick = {
                    viewModel.deleteImageUri(index)
                    Toast.makeText(context, "Image Removed", Toast.LENGTH_SHORT).show()
                          },
                context = context,
                modifier = Modifier
                    .size(width = 80.dp, height = 80.dp)
                    .clip(RoundedCornerShape(10.dp))
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

@Composable
fun DialogImage(
    uri: Uri?,
    onDismissRequest: () -> Unit
){
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        ImageItem(
            uri = uri,
            onClick = { onDismissRequest() },
            context = LocalContext.current,
            modifier = Modifier
                .size(width = 200.dp, height = 200.dp)
                .clip(RoundedCornerShape(10.dp)),
        )
    }
}





