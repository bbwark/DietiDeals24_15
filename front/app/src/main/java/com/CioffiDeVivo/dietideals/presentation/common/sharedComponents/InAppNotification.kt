package com.CioffiDeVivo.dietideals.presentation.common.sharedComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun InAppNotification(
    notificationMessage: String?,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(16.dp)
            .zIndex(1f),
        contentAlignment = Alignment.TopCenter
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (notificationMessage != null) {
                Text(
                    text = notificationMessage,
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
            }
            IconButton(onClick = onDismiss) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
        }
    }
}