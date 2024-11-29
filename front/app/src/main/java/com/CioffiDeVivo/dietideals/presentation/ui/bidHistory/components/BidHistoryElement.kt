package com.CioffiDeVivo.dietideals.presentation.ui.bidHistory.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.data.models.AuctionType

@Composable
fun BidHistoryElement(
    auctionType: AuctionType,
    auctionExpired: Boolean,
    bidderName: String,
    bidValue: Float,
    onShowDetails: () -> Unit,
    onAcceptOffer: () -> Unit,
    onUserInfo: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = bidderName)
        Text(
            text = bidValue.toString(),
            modifier = Modifier.padding(horizontal = 6.dp),
            fontWeight = FontWeight(600)
        )
        Spacer(modifier = Modifier.weight(1f))
        Box {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreHoriz,
                    contentDescription = null
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(text = { Text(text = "Show Details") }, onClick = {
                    expanded = false
                    onShowDetails()
                })
                if (auctionExpired && auctionType != AuctionType.English) {
                    DropdownMenuItem(text = { Text(text = "Accept This Offer") }, onClick = {
                        expanded = false
                        onAcceptOffer()
                    })
                }
                DropdownMenuItem(text = { Text(text = "User Info") }, onClick = {
                    expanded = false
                    onUserInfo()
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BidHistoryElementPreview() {

}