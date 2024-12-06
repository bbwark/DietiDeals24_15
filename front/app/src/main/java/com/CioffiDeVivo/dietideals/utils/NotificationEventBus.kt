package com.CioffiDeVivo.dietideals.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object NotificationEventBus {
    private val _notificationFlow = MutableStateFlow<String?>(null)
    val notificationFlow: StateFlow<String?> = _notificationFlow

    fun postNotification(message: String){
        _notificationFlow.value = message
    }

    fun clearNotification(){
        _notificationFlow.value = null
    }
}