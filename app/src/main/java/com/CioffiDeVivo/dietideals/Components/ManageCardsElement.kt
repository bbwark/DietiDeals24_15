package com.CioffiDeVivo.dietideals.Components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.R

@Composable
fun ManageCardsElement(modifier: Modifier = Modifier, cardNumber: String, clickOnMore: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        val circuit = CardCircuit(cardNumber)
        Box(Modifier.size(48.dp)) {
            if (circuit != -1) {
                Icon(
                    painter = painterResource(id = circuit),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.Unspecified
                )
            } else {
                Icon(
                    imageVector = Icons.Default.CreditCard,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = "x".repeat(cardNumber.length - 4).plus(cardNumber.takeLast(4)))
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { clickOnMore() }) {
            Icon(
                imageVector = Icons.Default.MoreHoriz,
                contentDescription = null
            )
        }
    }
}

fun CardCircuit(cardNumber: String): Int {
    if (cardNumber.first() == '4') {
        return R.drawable.visa
    }
    if (cardNumber.first() == '5') {
        return R.drawable.mastercard
    }
    return -1
}

@Preview(showBackground = true)
@Composable
fun ManageCardsElementPreview() {
    ManageCardsElement(cardNumber = "5234567891234567", clickOnMore = {})
}