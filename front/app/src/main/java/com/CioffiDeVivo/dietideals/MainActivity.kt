package com.CioffiDeVivo.dietideals

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.CioffiDeVivo.dietideals.presentation.navigation.SetupNavGraph
import com.CioffiDeVivo.dietideals.presentation.theme.DietiDealsTheme
import com.CioffiDeVivo.dietideals.services.ApiService
import com.CioffiDeVivo.dietideals.services.AuthService
import com.CioffiDeVivo.dietideals.utils.EncryptedPreferencesManager
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EncryptedPreferencesManager.initialize(this)
        AuthService.initialize(this)

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