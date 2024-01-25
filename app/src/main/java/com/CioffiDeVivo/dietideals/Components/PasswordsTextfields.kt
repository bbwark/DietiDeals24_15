package com.CioffiDeVivo.dietideals.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.DataModels.UserTest
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.Views.modifierStandard

@Composable
fun PasswordsTextfields(
    user: UserTest,
    onPasswordChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit
){
    InputTextField(
        value = user.password,
        onValueChanged = { onPasswordChange(it) },
        label = stringResource(R.string.password),
        supportingText = stringResource(R.string.passcharacters),
        visualTransformation = PasswordVisualTransformation(),
        modifier = modifierStandard
    )
    InputTextField(
        value = user.newPassword,
        onValueChanged = { onNewPasswordChange(it) },
        label = stringResource(R.string.rewritepassword),
        visualTransformation = PasswordVisualTransformation(),
        modifier = modifierStandard
    )
}