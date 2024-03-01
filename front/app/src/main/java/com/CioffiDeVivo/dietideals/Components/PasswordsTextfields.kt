package com.CioffiDeVivo.dietideals.Components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.CioffiDeVivo.dietideals.DataModels.UserTest
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.Views.modifierStandard

@Composable
fun PasswordsTextfields(
    user: UserTest,
    onPasswordChange: (String) -> Unit,
    label: String,
    supportingText: String
){
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    InputTextField(
        value = user.password,
        onValueChanged = { onPasswordChange(it) },
        label = label,
        visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = if(passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
        supportingText = supportingText,
        onDelete = { passwordVisible = !passwordVisible },
        modifier = modifierStandard
    )

}