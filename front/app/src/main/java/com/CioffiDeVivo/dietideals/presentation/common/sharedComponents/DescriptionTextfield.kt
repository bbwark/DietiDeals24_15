package com.CioffiDeVivo.dietideals.presentation.common.sharedComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun DescriptionTextfield(
    description: String,
    onDescriptionChange: (String) -> Unit,
    maxDescriptionCharacters: Int,
    onDeleteDescription: (String) -> Unit
){

    OutlinedTextField(
        value = description,
        onValueChange = {
            if(it.length <= maxDescriptionCharacters){
                onDescriptionChange(it)
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
                modifier = Modifier.clickable{ onDeleteDescription(description) }
            )
        },
        modifier = Modifier.size(330.dp,200.dp),
        label = { Text("Description") },
    )
}