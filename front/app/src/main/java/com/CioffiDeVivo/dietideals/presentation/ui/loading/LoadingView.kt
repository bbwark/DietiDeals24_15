package com.CioffiDeVivo.dietideals.presentation.ui.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.modifierStandard

@Composable
fun LoadingView(){
    Column(
        modifier = modifierStandard,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Loading...")
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingPreview(){
    LoadingView()
}