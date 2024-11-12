package com.CioffiDeVivo.dietideals.services

import android.content.Context

object TokenService {

    fun getToken(context: Context): String?{
        val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("token", null)
    }

}