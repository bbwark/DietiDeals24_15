package com.CioffiDeVivo.dietideals.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun DescriptionTextfield(
    maxDescriptionCharacters: Int
){
    var description by remember{ mutableStateOf("") }
    OutlinedTextField(
        value = description,
        onValueChange = {
            if(it.length <= maxDescriptionCharacters){
                description = it
            }
        },
        supportingText = {
            Text(
                text = "${description.length} / $maxDescriptionCharacters",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        },
        singleLine = false,
        maxLines = 7,
        trailingIcon = {
            Icon(
                Icons.Rounded.Clear,
                contentDescription = null,
                modifier = Modifier.clickable{description = ""}
            )
        },
        modifier = Modifier.size(320.dp,200.dp),
        label = { Text("Description") },
    )
}