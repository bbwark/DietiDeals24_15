package com.CioffiDeVivo.dietideals.utils

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CustomFirebaseMessagingService: FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("FCM", "FROM: ${remoteMessage.from}")
        super.onMessageReceived(remoteMessage)
        remoteMessage.notification?.let {
            val messageBody = it.body ?: "New Notification"
            Log.d("FCM", "NOTIFICATION MESSAGE BODY: $messageBody")
            NotificationEventBus.postNotification(messageBody)
        }
    }
}