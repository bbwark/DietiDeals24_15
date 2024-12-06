package com.CioffiDeVivo.dietideals.utils

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

fun trackScreen(firebaseAnalytics: FirebaseAnalytics, screenName: String){
    val bundle = Bundle().apply {
        putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        putString(FirebaseAnalytics.Param.SCREEN_CLASS, "Compose")
    }
    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
}