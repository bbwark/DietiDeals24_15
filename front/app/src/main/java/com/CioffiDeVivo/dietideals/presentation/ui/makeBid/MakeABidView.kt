package com.CioffiDeVivo.dietideals.presentation.ui.makeBid

import android.app.Application
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
import androidx.compose.material.icons.filled.Euro
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.ViewTitle
import com.CioffiDeVivo.dietideals.animations.pulsateClick
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.domain.models.Auction
import com.CioffiDeVivo.dietideals.domain.models.AuctionType
import com.CioffiDeVivo.dietideals.domain.models.Bid
import com.CioffiDeVivo.dietideals.utils.BidInputVisualTransformation
import com.CioffiDeVivo.dietideals.presentation.common.sharedViewmodels.SharedViewModel

@Composable
fun MakeABid(
    sharedState: Auction,
    viewModel: SharedViewModel,
    navController: NavHostController,
    onMakeABid: () -> Unit
){

    var bid by rememberSaveable { mutableStateOf("") }
    val userBidState by viewModel.bidState.collectAsState()
    val auctionState by viewModel.auctionState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        ViewTitle(title = "State: ${sharedState.type.name}")
        ViewTitle(title = stringResource(R.string.minStep))
        Spacer(modifier = Modifier.height(7.dp))
        Row {
            Text(
                if (auctionState.type == AuctionType.English)
                        (auctionState.bids.last().value + auctionState.minStep.toFloat()).toString()
                        else auctionState.minAccepted,
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
            onValueChange = {
                bid = if (it.startsWith("0")) {
                    ""
                } else {
                    it
                }
                viewModel.updateBidValue(bid)
            },
            singleLine = true,
            trailingIcon = {
                Icon(
                    Icons.Filled.Euro,
                    contentDescription = null,
                )
            },
            visualTransformation = BidInputVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier.width(250.dp),
            label = { Text("Your Bid") },
        )
        Spacer(modifier = Modifier.size(50.dp))
        Button(
            onClick =  /*TODO If submitBid is true then navigate viewModel.submitBid()*/ onMakeABid ,
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

@Preview(showBackground = true)
@Composable
fun MakeABidSilentPreview(){
    val viewModel = SharedViewModel(Application())
    val bid1 = Bid(value = 10f)
    val bid2 = Bid(value = 20f)
    val auction = Auction(bids = arrayOf(bid1, bid2), type = AuctionType.English, minAccepted = "10", minStep = "1")
    viewModel.setAuction(auction)

    MakeABid(sharedState = auction, viewModel = viewModel, navController = rememberNavController(), onMakeABid = {})
}