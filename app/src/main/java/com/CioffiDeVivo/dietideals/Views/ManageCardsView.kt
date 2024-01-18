package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.Components.ManageCardsElement
import com.CioffiDeVivo.dietideals.DietiDealsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ManageCardsView(viewModel: DietiDealsViewModel) {
    Box {
        AddCardButton(viewModel = viewModel)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            content = {
                itemsIndexed(viewModel.user.creditCards) { index, item ->
                    if (index == 0) {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    ManageCardsElement(
                        cardNumber = item.number,
                        clickOnMore = {/* a menu where you can delete the card has to open */ })
                    Divider()
                }
            })
    }
}


@Composable
fun AddCardButton(viewModel: DietiDealsViewModel) {
    Column {
        Spacer(modifier = Modifier.weight(1f))
        Row {
            Spacer(modifier = Modifier.weight(1f))
            FloatingActionButton(
                onClick = { /*Add new Card*/ },
                modifier = Modifier.padding(24.dp)
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview (showBackground = true)
@Composable
fun ManageCardsViewPreview() {
    ManageCardsView(viewModel = DietiDealsViewModel())
}