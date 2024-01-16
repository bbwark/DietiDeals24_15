package com.CioffiDeVivo.dietideals

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.Views.Navigation.SetupNavGraph
import com.CioffiDeVivo.dietideals.ui.theme.DietiDealsTheme


class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DietiDealsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    var dietiDealsViewModel = DietiDealsViewModel()
                    SetupNavGraph(navController = navController, viewModel = dietiDealsViewModel)
                }
            }
        }
    }
}