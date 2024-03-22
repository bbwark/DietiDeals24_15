package com.CioffiDeVivo.dietideals.Components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    onAccept: (Long?) -> Unit,
    onCancel: () -> Unit
) {
    val state = rememberDatePickerState(selectableDates = object: SelectableDates{
        override fun isSelectableDate(utcTimeMillis: Long): Boolean{
            return utcTimeMillis >= System.currentTimeMillis()
        }
    })

    DatePickerDialog(
        onDismissRequest = {  },
        confirmButton = {
            TextButton(onClick = { onAccept(state.selectedDateMillis) }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = state)
    }
}