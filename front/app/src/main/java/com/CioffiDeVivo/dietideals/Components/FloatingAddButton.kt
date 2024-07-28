package com.CioffiDeVivo.dietideals.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.CioffiDeVivo.dietideals.viewmodel.MainViewModel

@Composable
fun FloatingAddButton(viewModel: ViewModel, onClick: () -> Unit) {
    Column {
        Spacer(modifier = Modifier.weight(1f))
        Row {
            Spacer(modifier = Modifier.weight(1f))
            FloatingActionButton(
                onClick = { onClick() },
                modifier = Modifier.padding(24.dp)
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        }
    }
}