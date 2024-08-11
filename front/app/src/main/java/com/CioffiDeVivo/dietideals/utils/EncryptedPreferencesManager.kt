package com.CioffiDeVivo.dietideals.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object EncryptedPreferencesManager {

    private lateinit var encryptedPreferences: SharedPreferences

    fun initialize(context: Context) {
        if (!::encryptedPreferences.isInitialized) {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            encryptedPreferences = EncryptedSharedPreferences.create(
                context,
                "EncryptedAppPreferences",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
    }

    fun getEncryptedPreferences(): SharedPreferences {
        if (!::encryptedPreferences.isInitialized) {
            throw IllegalStateException("EncryptedPreferencesManager not initialized")
        }
        return encryptedPreferences
    }
}
