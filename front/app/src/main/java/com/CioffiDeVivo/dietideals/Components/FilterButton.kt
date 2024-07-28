package com.CioffiDeVivo.dietideals.Components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.DataModels.AuctionCategory

@Composable
fun FilterButton(selectedOptions: MutableState<MutableSet<String>>) {
    var expanded by remember { mutableStateOf(false) }
    val options = AuctionCategory.values().map { it.name }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(imageVector = Icons.Default.FilterList, contentDescription = "")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                val isChecked = remember { mutableStateOf(selectedOptions.value.contains(option)) }
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = !isChecked.value,
                                onCheckedChange = null
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(text = option)
                        }
                    }, onClick = {
                        if (isChecked.value) {
                            selectedOptions.value = selectedOptions.value.toMutableSet().apply { remove(option) }
                            isChecked.value = false
                        } else {
                            selectedOptions.value = selectedOptions.value.toMutableSet().apply { add(option) }
                            isChecked.value = true
                        }
                    })
            }
        }
    }
}
