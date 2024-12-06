package com.CioffiDeVivo.dietideals.presentation.common.sharedComponents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.CioffiDeVivo.dietideals.utils.NotificationEventBus

@Composable
fun GlobalNotificationObserver() {
    val notificationMessage by NotificationEventBus.notificationFlow.collectAsState()
    if(notificationMessage != null){
        InAppNotification(
            notificationMessage = notificationMessage!!,
            onDismiss = { NotificationEventBus.clearNotification() }
        )
    }
}