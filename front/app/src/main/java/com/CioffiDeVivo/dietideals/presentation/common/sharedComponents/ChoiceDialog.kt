package com.CioffiDeVivo.dietideals.presentation.common.sharedComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun ChoiceDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
){
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            ) {
                Text(text = "Confirm")
            }              
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "Cancel", color = Color(0xFFB60202))
            }
        },
        title = { Text(text = title) },
        text = {
            Column(horizontalAlignment = Alignment.Start) {
                Text(text = message)
            }
        },
    )
}
