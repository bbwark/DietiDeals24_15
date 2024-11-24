package com.CioffiDeVivo.dietideals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.presentation.navigation.SetupNavGraph
import com.CioffiDeVivo.dietideals.presentation.theme.DietiDealsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val application = application as DietiDealsApplication
        val appViewModelFactory = application.appViewModelFactory

        setContent {
            DietiDealsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetupNavGraph(navController = rememberNavController(), appViewModelFactory = appViewModelFactory)
                }
            }
        }
    }
}