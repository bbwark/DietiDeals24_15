package com.CioffiDeVivo.dietideals

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.Views.Navigation.SetupNavGraph
import com.CioffiDeVivo.dietideals.ui.theme.DietiDealsTheme
import com.CioffiDeVivo.dietideals.utils.ApiService
import com.CioffiDeVivo.dietideals.utils.AuthService
import com.CioffiDeVivo.dietideals.utils.EncryptedPreferencesManager
import com.CioffiDeVivo.dietideals.viewmodel.MainViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EncryptedPreferencesManager.initialize(this)
        AuthService.initialize(this)

        val encryptedSharedPreferences = EncryptedPreferencesManager.getEncryptedPreferences()

        // ONLY FOR TESTING PURPOSE WE WIPE OUT PREFERENCES EACH TIME
        val sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        encryptedSharedPreferences.edit().apply {
            clear()
            apply()
        }
        sharedPreferences.edit().apply {
            clear()
            apply()
        }


        val email = encryptedSharedPreferences.getString("email", null)
        val password = encryptedSharedPreferences.getString("password", null)
        if (email != null && password != null) {
            lifecycleScope.launch {//change to runBlocking if you want to block main thread to wait for login (safer)
                val loginResponse = AuthService.loginUser(email, password)
                val sessionToken = Gson().fromJson(loginResponse.bodyAsText(), JsonObject::class.java).get("jwt").asString
                ApiService.initialize(sessionToken, this@MainActivity)
            }
        }
        setContent {
            DietiDealsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetupNavGraph()
                }
            }
        }
    }
}