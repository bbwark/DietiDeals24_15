package com.CioffiDeVivo.dietideals.Components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.R

@Composable
fun ManageCardsElement(modifier: Modifier = Modifier, cardNumber: String, clickOnDelete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val circuit = CardCircuit(cardNumber)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
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
        Text(text = "•".repeat(cardNumber.length - 4).plus(cardNumber.takeLast(4)))
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
                DropdownMenuItem(
                    text = { Text(text = "Delete") },
                    onClick = { clickOnDelete() },
                    leadingIcon = { Icon(Icons.Default.Delete, contentDescription = null) }
                )
            }
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
    ManageCardsElement(cardNumber = "5234567891234567", clickOnDelete = {})
}