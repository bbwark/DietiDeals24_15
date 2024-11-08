package com.CioffiDeVivo.dietideals.presentation.ui.createAuction

import android.app.Application
import android.content.Context
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
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
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.DescriptionTextfield
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.InputTextField
import com.CioffiDeVivo.dietideals.presentation.ui.createAuction.components.CustomDatePickerDialog
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.DropDownMenuField
import com.CioffiDeVivo.dietideals.animations.pulsateClick
import com.CioffiDeVivo.dietideals.domain.models.AuctionType
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.domain.models.AuctionCategory
import com.CioffiDeVivo.dietideals.domain.validations.ValidationState
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.DialogAlert
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.DialogInfo
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.presentation.ui.loading.LoadingView
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.modifierStandard
import com.CioffiDeVivo.dietideals.presentation.ui.retry.RetryView
import com.CioffiDeVivo.dietideals.utils.IntervalTransformation
import com.CioffiDeVivo.dietideals.utils.rememberCurrencyVisualTransformation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CreateAuction(viewModel: CreateAuctionViewModel, navController: NavHostController){

    val showDatePicker = remember { mutableStateOf(false) }
    val showMaxBidInfo = remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(AuctionCategory.Other) }
    val createAuctionUiState by viewModel.createAuctionUiState.collectAsState()
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val permissionState = rememberPermissionState(
        permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
    )
    LaunchedEffect(key1 = context){
        viewModel.validationCreateAuctionEvent.collect { event ->
            when(event){
                is ValidationState.Success -> {
                }
                else -> {
                    Toast.makeText(context, "Invalid Field", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    SideEffect {
        permissionState.launchPermissionRequest()
    }

    when(createAuctionUiState){
        is CreateAuctionUiState.Error -> RetryView(onClick = {})
        is CreateAuctionUiState.Loading -> LoadingView()
        is CreateAuctionUiState.Success -> {
            navController.navigate(Screen.Home.route)
        }
        is CreateAuctionUiState.CreateAuctionParams -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                AddingImagesOnCreateAuction(
                    imagesList = (createAuctionUiState as CreateAuctionUiState.CreateAuctionParams).auction.item.imagesUri,
                    onImageChange = { viewModel.createAuctionOnAction(CreateAuctionEvents.ImagesChanged(it)) },
                    onDeleteImage = { viewModel.createAuctionOnAction(CreateAuctionEvents.ImagesDeleted(it)) }
                )
                Spacer(modifier = Modifier.height(30.dp))

                InputTextField(
                    value = (createAuctionUiState as CreateAuctionUiState.CreateAuctionParams).auction.item.name,
                    onValueChanged = { viewModel.createAuctionOnAction(CreateAuctionEvents.ItemNameChanged(it)) },
                    label = stringResource(R.string.itemName),
                    onTrailingIconClick = { viewModel.createAuctionOnAction(CreateAuctionEvents.ItemNameDeleted(it)) },
                    isError = (createAuctionUiState as CreateAuctionUiState.CreateAuctionParams).itemNameErrorMsg != null,
                    supportingText = (createAuctionUiState as CreateAuctionUiState.CreateAuctionParams).itemNameErrorMsg,
                    modifier = modifierStandard
                )
                DropDownMenuField(
                    selectedValue = selectedCategory,
                    menuList = AuctionCategory.values(),
                    label = stringResource(id = R.string.category),
                    onValueSelected = {
                            newSelection -> selectedCategory = newSelection
                        viewModel.createAuctionOnAction(CreateAuctionEvents.AuctionCategoryChanged(selectedCategory))
                    },
                    modifier = Modifier.padding(start = 100.dp, end = 100.dp, bottom = 8.dp)
                )
                Row {
                    ElevatedButton(
                        onClick = {
                            viewModel.removeErrorMsgAuctionType()
                            viewModel.createAuctionOnAction(CreateAuctionEvents.AuctionTypeChanged(AuctionType.Silent))
                        },
                        colors = if ((createAuctionUiState as CreateAuctionUiState.CreateAuctionParams).auction.type == AuctionType.Silent){
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
                        onClick = {
                            viewModel.removeErrorMsgAuctionType()
                            viewModel.createAuctionOnAction(CreateAuctionEvents.AuctionTypeChanged(AuctionType.English))
                        },
                        colors = if ((createAuctionUiState as CreateAuctionUiState.CreateAuctionParams).auction.type == AuctionType.English){
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
                when ((createAuctionUiState as CreateAuctionUiState.CreateAuctionParams).auction.type) {
                    AuctionType.Silent -> {
                        SilentAuction(
                            auctionState = createAuctionUiState,
                            onMinBidChange = { viewModel.createAuctionOnAction(CreateAuctionEvents.MinAcceptedChanged(it)) },
                            onMaxBidChange = { viewModel.createAuctionOnAction(CreateAuctionEvents.MaxBidChanged(it)) },
                            onInfoClick = { showMaxBidInfo.value = true },
                            onDescriptionChange = { viewModel.updateDescriptionAuction(it) },
                            onDeleteDescription = { viewModel.deleteDescriptionAuction() },
                            onCalendarClick = { showDatePicker.value = true }
                        )
                    }
                    AuctionType.English -> {
                        EnglishAuction(
                            auctionState = createAuctionUiState,
                            onBidChange = { viewModel.createAuctionOnAction(CreateAuctionEvents.MinStepChanged(it)) },
                            onIntervalChange = { viewModel.createAuctionOnAction(CreateAuctionEvents.IntervalChanged(it)) },
                            onDescriptionChange = { viewModel.createAuctionOnAction(CreateAuctionEvents.DescriptionChanged(it)) },
                            onDeleteInterval = { viewModel.createAuctionOnAction(CreateAuctionEvents.IntervalDeleted(it)) },
                            onDeleteDescription = { viewModel.createAuctionOnAction(CreateAuctionEvents.DescriptionDeleted(it)) },
                            onCalendarClick = { showDatePicker.value = true }
                        )
                    }
                    else -> {

                    }
                }

                if(showMaxBidInfo.value){
                    DialogInfo(
                        showDialog = showMaxBidInfo,
                        dialogText = stringResource(R.string.maxBidInfo)
                    )
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

                if(showDialog.value && (createAuctionUiState as CreateAuctionUiState.CreateAuctionParams).auctionTypeErrorMsg != null){
                    DialogAlert(
                        showDialog = showDialog,
                        dialogText = (createAuctionUiState as CreateAuctionUiState.CreateAuctionParams).auctionTypeErrorMsg!!
                    )
                }

                Button(
                    onClick = {
                        showDialog.value = true
                        viewModel.createAuctionOnAction(CreateAuctionEvents.Submit())

                        /*ADD AUCTION WITH A SPECIFIC METHOD FOR DIFFERENCES BETWEEN ENGLISH AND SILENT*/
                    },
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(bottom = 8.dp)
                        .pulsateClick(),
                    content = {
                        Text(stringResource(R.string.createAuction), fontSize = 20.sp)
                    }
                )

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CreateAuctionPreview(){
    CreateAuction(viewModel = CreateAuctionViewModel(application = Application()), navController = rememberNavController())
}


@Composable
fun SilentAuction(
    auctionState: CreateAuctionUiState,
    onMinBidChange: (String) -> Unit,
    onMaxBidChange: (String) -> Unit,
    onInfoClick: () -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDeleteDescription: (String) -> Unit,
    onCalendarClick: () -> Unit
){
    var minAccepted by rememberSaveable { mutableStateOf("") }
    var maxBid by rememberSaveable { mutableStateOf("") }
    val currencyVisualTransformation = rememberCurrencyVisualTransformation(currency = "EUR")
    (auctionState as CreateAuctionUiState.CreateAuctionParams)
    Row (
        modifier = modifierStandard
    ){
        OutlinedTextField(
            value = minAccepted,
            onValueChange = { newBid ->
                val trimmed = newBid.trimStart('0').trim { it.isDigit().not() }
                if(trimmed.isEmpty() || trimmed.toInt() < 10000) {
                    minAccepted = trimmed
                }
                onMinBidChange(minAccepted)
            },
            singleLine = true,
            trailingIcon = {
                Icon(
                    Icons.Filled.Euro,
                    contentDescription = null,
                )
            },
            visualTransformation = currencyVisualTransformation,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = {
                Text(
                    stringResource(R.string.minimumBid),
                    fontSize = 14.sp
                )
            },
            isError = auctionState.minAcceptedErrorMsg != null,
            supportingText = {
                if(auctionState.minAcceptedErrorMsg != null){
                    Text(text = auctionState.minAcceptedErrorMsg, color = Color.Red)
                }
            },
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
        )
        Spacer(modifier = Modifier.width(30.dp))
        OutlinedTextField(
            value = maxBid,
            onValueChange = { newBid ->
                val trimmed = newBid.trimStart('0').trim { it.isDigit().not() }
                if(trimmed.isEmpty() || trimmed.toInt() < 10000) {
                    maxBid = trimmed
                }
                onMaxBidChange(maxBid)
            },
            singleLine = true,
            trailingIcon = {
                Icon(
                    Icons.Filled.Info,
                    contentDescription = null,
                    modifier = Modifier.clickable { onInfoClick() }
                )
            },
            visualTransformation = currencyVisualTransformation,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = {
                Text(
                    stringResource(R.string.maxBid),
                    fontSize = 14.sp
                )
            },
            isError = auctionState.maxBidErrorMsg != null,
            supportingText = {
                if(auctionState.maxBidErrorMsg != null){
                    Text(text = auctionState.maxBidErrorMsg, color = Color.Red)
                }
            },
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
        )
    }
    InputTextField(
        value = auctionState.auction.endingDate!!.format(DateTimeFormatter.ISO_LOCAL_DATE),
        onValueChanged = { },
        label = stringResource(R.string.endingDate),
        onTrailingIconClick = { onCalendarClick() },
        readOnly = true,
        trailingIcon = Icons.Filled.CalendarMonth,
        modifier = modifierStandard
    )
    DescriptionTextfield(
        description = auctionState.auction.description,
        onDescriptionChange = { onDescriptionChange(it) },
        maxDescriptionCharacters = 200,
        onDeleteDescription = { onDeleteDescription(it) }
    )

}

@Composable
fun EnglishAuction(
    auctionState: CreateAuctionUiState,
    onBidChange: (String) -> Unit,
    onIntervalChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDeleteInterval: (String) -> Unit,
    onDeleteDescription: (String) -> Unit,
    onCalendarClick: () -> Unit
){
    var minStep by rememberSaveable { mutableStateOf("") }
    val currencyVisualTransformation = rememberCurrencyVisualTransformation(currency = "EUR")
    (auctionState as CreateAuctionUiState.CreateAuctionParams)
    Row(
        modifier = modifierStandard
    ) {
        OutlinedTextField(
            value = auctionState.auction.minStep,
            onValueChange = { newBid ->
                val trimmed = newBid.trimStart('0').trim { it.isDigit().not() }
                if(trimmed.isEmpty() || trimmed.toInt() < 10000) {
                    minStep = trimmed
                }
                onBidChange(minStep)
            },
            singleLine = true,
            trailingIcon = {
                Icon(
                    Icons.Filled.Euro,
                    contentDescription = null,
                )
            },
            visualTransformation = currencyVisualTransformation,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = {
                Text(
                    stringResource(R.string.minStep),
                    fontSize = 13.sp
                )
            },
            isError = auctionState.minStepErrorMsg != null,
            supportingText = {
                if(auctionState.minStepErrorMsg != null){
                    Text(text = auctionState.minStepErrorMsg, color = Color.Red)
                }
            },
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
        )
        Spacer(modifier = Modifier.width(30.dp))
        InputTextField(
            value = auctionState.auction.interval,
            onValueChanged = { onIntervalChange(it) },
            label = stringResource(R.string.interval),
            placeholder = stringResource(id = R.string.intervalPlaceholder),
            onTrailingIconClick = { onDeleteInterval(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = IntervalTransformation(),
            isError = auctionState.intervalErrorMsg != null,
            supportingText = auctionState.intervalErrorMsg,
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
        )
    }
    InputTextField(
        value = auctionState.auction.endingDate!!.format(DateTimeFormatter.ISO_LOCAL_DATE),
        onValueChanged = {  },
        label = stringResource(R.string.endingDate),
        trailingIcon = Icons.Filled.CalendarMonth,
        onTrailingIconClick = { onCalendarClick() },
        readOnly = true,
        modifier = modifierStandard
    )
    DescriptionTextfield(
        description = auctionState.auction.description,
        onDescriptionChange = { onDescriptionChange(it) },
        maxDescriptionCharacters = 200,
        onDeleteDescription = { onDeleteDescription(it) }
    )
}

@Composable
fun ImageItem(
    uri: String,
    onClick: () -> Unit,
    context: Context,
    iconButton: ImageVector = Icons.Filled.BrokenImage,
    modifier: Modifier
){
    val showDialog = remember { mutableStateOf(false) }
    Box {
        Image(
            painter = rememberAsyncImagePainter(
                model = uri,
                error = rememberVectorPainter(image = Icons.Default.BrokenImage),  // Icona di errore
                placeholder = rememberVectorPainter(image = Icons.Default.Image)   // Icona di caricamento
            ),
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
fun AddingImagesOnCreateAuction(
    imagesList: List<String>,
    onImageChange: (String) -> Unit,
    onDeleteImage: (Int) -> Unit
) {

    val context = LocalContext.current
    val multiPhotosPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            onImageChange(selectedUri.toString())
        }
    }
    val permissionState = rememberPermissionState(
        permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
    )
    SideEffect {
        permissionState.launchPermissionRequest()
    }

    LazyRow {
        itemsIndexed(imagesList) { index, uri ->
            Spacer(modifier = Modifier.width(10.dp))
            ImageItem(
                uri = uri,
                onClick = {
                    onDeleteImage(index)
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
        enabled = imagesList.size < 5
    ) {
        Icon(imageVector = Icons.Default.ImageSearch, contentDescription = "Gallery Icon")
        Text(text = "Add Image")
    }
}

@Composable
fun DialogImage(
    uri: String,
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





