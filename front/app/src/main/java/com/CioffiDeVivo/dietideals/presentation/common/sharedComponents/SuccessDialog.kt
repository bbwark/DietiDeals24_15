package com.CioffiDeVivo.dietideals.presentation.common.sharedComponents

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SuccessDialog(
    title: String,
    text: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
        },
        text = {
            Text(text = text, style = MaterialTheme.typography.bodyMedium)
        },
        onDismissRequest = onDismiss,
        confirmButton = {  }
    )
}