package com.CioffiDeVivo.dietideals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.presentation.theme.DietiDealsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val application = application as DietiDealsApplication
        val appViewModelFactory = application.appViewModelFactory

        setContent {
            DietiDealsTheme {
                val navController = rememberNavController()
                MainActivityContent(navController = navController, appViewModelFactory = appViewModelFactory)
            }
        }
    }
}