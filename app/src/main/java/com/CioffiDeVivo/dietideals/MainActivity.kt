package com.CioffiDeVivo.dietideals

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.CioffiDeVivo.dietideals.Views.RegisterCredentialsView
import com.CioffiDeVivo.dietideals.ui.theme.DietiDealsTheme


class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DietiDealsTheme {
                RegisterCredentialsView(viewModel = DietiDealsViewModel())
            }
        }
    }
}

