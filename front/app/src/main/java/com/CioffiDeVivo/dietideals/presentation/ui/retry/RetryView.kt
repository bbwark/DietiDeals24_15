package com.CioffiDeVivo.dietideals.presentation.ui.retry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.modifierStandard

@Composable
fun RetryView(){
    Column(
        modifier = modifierStandard,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "Retry")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RetryPreview(){

}