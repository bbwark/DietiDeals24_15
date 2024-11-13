package com.CioffiDeVivo.dietideals.presentation.ui.makeBid

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.ViewTitle
import com.CioffiDeVivo.dietideals.animations.pulsateClick
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.domain.models.Auction
import com.CioffiDeVivo.dietideals.domain.models.AuctionType
import com.CioffiDeVivo.dietideals.domain.models.Bid
import com.CioffiDeVivo.dietideals.utils.CurrencyVisualTransformation
import com.CioffiDeVivo.dietideals.presentation.common.sharedViewmodels.SharedViewModel
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.presentation.ui.loading.LoadingView
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.modifierStandard
import com.CioffiDeVivo.dietideals.presentation.ui.retry.RetryView
import com.CioffiDeVivo.dietideals.utils.rememberCurrencyVisualTransformation

@Composable
fun MakeABid(
    auctionId: String,
    viewModel: MakeABidViewModel,
    navController: NavController,
){
    val makeABidUiState by viewModel.makeABidUiState.collectAsState()

    LaunchedEffect(Unit){
        viewModel.fetchAuction(auctionId)
    }

    when(makeABidUiState){
        is MakeABidUiState.Loading -> LoadingView()
        is MakeABidUiState.Success -> {
            navController.popBackStack()
        }
        is MakeABidUiState.Error -> RetryView(
            onClick = {
                navController.popBackStack()
            }
        )
        is MakeABidUiState.MakeABidParams -> {
            MakeABidLayout(
                auctionState = (makeABidUiState as MakeABidUiState.MakeABidParams).auction,
                onBidChange = { viewModel.updateBidValue(it) }
            )
        }
    }

}

@Composable
fun MakeABidLayout(
    auctionState: Auction,
    onBidChange: (String) -> Unit
){
    var bid by rememberSaveable { mutableStateOf("") }
    val currencyVisualTransformation = rememberCurrencyVisualTransformation(currency = "EUR")

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        ViewTitle(title = stringResource(R.string.minStep))
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                if (auctionState.type == AuctionType.English)
                    (auctionState.bids.last().value + auctionState.minStep.toFloat()).toString()
                else auctionState.minAccepted,
                fontSize = 28.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium
            )
            Text(
                "EUR",
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
            modifier = modifierStandard,
            label = { Text("Your Bid") },
        )
        Button(
            onClick =  /*TODO If submitBid is true then navigate viewModel.submitBid()*/{},
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

@Preview(showBackground = true)
@Composable
fun MakeABidSilentPreview(){
    MakeABid(auctionId = "1", viewModel = MakeABidViewModel(Application()), navController = rememberNavController())
}