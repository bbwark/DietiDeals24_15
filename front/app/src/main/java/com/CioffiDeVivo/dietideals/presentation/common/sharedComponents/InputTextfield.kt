package com.CioffiDeVivo.dietideals.presentation.common.sharedComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.modifierStandard

@Composable
fun InputTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    readOnly: Boolean = false,
    label: String,
    placeholder: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    trailingIcon: ImageVector = Icons.Filled.Clear,
    supportingText: String? = null,
    isError: Boolean = false,
    onTrailingIconClick: (String) -> Unit,
    modifier: Modifier = modifierStandard
){
    var text by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        label = { Text(label, fontSize = 15.sp) },
        placeholder = { Text(placeholder) },
        visualTransformation = visualTransformation,
        readOnly = readOnly,
        singleLine = true,
        supportingText = {
            if(isError){
                if (supportingText != null) {
                    Text(text = supportingText, color = Color.Red)
                }
            }
        },
        trailingIcon = {
            Icon(
                trailingIcon,
                contentDescription = null,
                modifier = Modifier.clickable { onTrailingIconClick(value) }
            )
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        isError = isError,
        modifier = modifier
    )
}