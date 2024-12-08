package com.CioffiDeVivo.dietideals.presentation.ui.makeBid

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Euro
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.ViewTitle
import com.CioffiDeVivo.dietideals.animations.pulsateClick
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.data.models.Auction
import com.CioffiDeVivo.dietideals.data.models.AuctionType
import com.CioffiDeVivo.dietideals.data.models.Bid
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.SuccessDialog
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.presentation.ui.loading.LoadingView
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.modifierStandard
import com.CioffiDeVivo.dietideals.presentation.ui.retry.RetryView
import com.CioffiDeVivo.dietideals.utils.rememberCurrencyVisualTransformation
import kotlinx.coroutines.delay

@Composable
fun MakeABid(
    auctionId: String,
    viewModel: MakeABidViewModel,
    navController: NavController,
){
    val makeABidUiState by viewModel.makeABidUiState.collectAsState()
    var showSuccessDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        viewModel.fetchAuction(auctionId)
    }

    when(makeABidUiState){
        is MakeABidUiState.Loading -> LoadingView()
        is MakeABidUiState.Success -> {
            showSuccessDialog = true
        }
        is MakeABidUiState.Error -> RetryView(
            onClick = {
                if (navController.currentBackStackEntry?.destination?.route != Screen.Auction.route + "/${auctionId}") {
                    navController.popBackStack()
                }
            }
        )
        is MakeABidUiState.MakeABidParams -> {
            MakeABidLayout(
                auction = (makeABidUiState as MakeABidUiState.MakeABidParams).auction,
                bidErrorMsg = (makeABidUiState as MakeABidUiState.MakeABidParams).bidErrorMsg,
                onBidChange = { viewModel.updateBidValue(it) },
                onSubmitBid = { viewModel.submitBid(auctionId) }
            )
        }
    }
    if(showSuccessDialog){
        LaunchedEffect(Unit){
            delay(1000)
            showSuccessDialog = false
            navController.navigate(Screen.Home.route){
                popUpTo(Screen.Home.route) { inclusive = false }
            }
        }
    }
        AnimatedVisibility(
            visible = showSuccessDialog,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            SuccessDialog(
                title = "Bid Successful",
                text = "Your bid has been placed successfully!",
                onDismiss = { showSuccessDialog = false }
            )
        }
}

@Composable
fun MakeABidLayout(
    auction: Auction,
    bidErrorMsg: String?,
    onBidChange: (String) -> Unit,
    onSubmitBid: (String) -> Unit
){
    var bid by rememberSaveable { mutableStateOf("") }
    val currencyVisualTransformation = rememberCurrencyVisualTransformation(currency = "EUR")

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        if(auction.type == AuctionType.English){
            ViewTitle(title = stringResource(R.string.minimumBid))
        } else{
            ViewTitle(title = stringResource(R.string.minimumBid))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                if (auction.type == AuctionType.English)
                        if(auction.bids.isEmpty()){
                            auction.startingPrice
                        } else{
                            (auction.bids.last().value + auction.minStep.toFloat()).toString()
                        }
                else auction.startingPrice,
                fontSize = 28.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium
            )
            Text(
                " EUR",
                fontSize = 28.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            )
        }
        OutlinedTextField(
            value = bid,
            onValueChange = { newBid ->
                val trimmed = newBid.trimStart('0').trim { it.isDigit().not() }
                if(trimmed.isEmpty() || trimmed.toInt() <= 10000) {
                    bid = trimmed
                }
                onBidChange(bid)
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
            isError = bidErrorMsg != null,
            supportingText = {
                if(bidErrorMsg != null){
                    Text(text = bidErrorMsg, color = Color.Red)
                }
            },
            modifier = modifierStandard,
            label = { Text("Your Bid") },
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onSubmitBid(auction.id) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp, bottom = 8.dp)
                .height(50.dp)
                .pulsateClick(),
        ) {
            Text("Make a Bid", fontSize = 20.sp)
        }
    }
}