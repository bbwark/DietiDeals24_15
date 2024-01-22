package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.Components.BidHistoryElement
import com.CioffiDeVivo.dietideals.DietiDealsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BidHistoryView(viewModel: DietiDealsViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        content = {
            itemsIndexed(viewModel.selectedAuction.bids) { index, item ->
                if (index == 0) {
                    Spacer(modifier = Modifier.height(10.dp))
                }
                viewModel.selectedAuctionBidders.find { it.id == item.userId }?.let {
                    BidHistoryElement(
                        bidderName = it.name,
                        bidValue = item.value,
                        onShowDetails = { /* opens a dialog where user can see bid data */ },
                        onAcceptOffer = { /* opens a dialog where user can see bid data and accept the offer */ },
                        onUserInfo = { /* opens a sheet where user can read bidder's info */ }
                    )
                }
                HorizontalDivider()
            }
    })
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun BidHistoryViewPreview() {
    BidHistoryView(viewModel = DietiDealsViewModel())
}