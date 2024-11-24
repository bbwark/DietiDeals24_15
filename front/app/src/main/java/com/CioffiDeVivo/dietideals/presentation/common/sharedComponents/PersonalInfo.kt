package com.CioffiDeVivo.dietideals.presentation.common.sharedComponents

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.presentation.ui.editProfile.EditProfileUiState
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.modifierStandard
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.RegisterCredentialsUiState

@Composable
fun PersonalInfoOnRegisterCredentials(
    userState: RegisterCredentialsUiState,
    onEmailChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onDeleteEmail: (String) -> Unit,
    onDeleteName: (String) -> Unit,
){

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var newPasswordVisible by rememberSaveable { mutableStateOf(false) }

    InputTextField(
        value = (userState as RegisterCredentialsUiState.RegisterParams).user.email,
        onValueChanged = { onEmailChange(it) },
        label = stringResource(R.string.email),
        placeholder = stringResource(R.string.emailExample),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        isError = userState.emailErrorMsg != null,
        onTrailingIconClick = { onDeleteEmail(it) },
        supportingText = userState.emailErrorMsg,
        modifier = modifierStandard
    )
    InputTextField(
        value = userState.user.name,
        onValueChanged = { onNameChange(it) },
        label = stringResource(R.string.name),
        isError = userState.nameErrorMsg != null,
        onTrailingIconClick = { onDeleteName(it) },
        supportingText = userState.nameErrorMsg,
        modifier = modifierStandard
    )
    InputTextField(
        value = userState.user.password,
        onValueChanged = { onPasswordChange(it) },
        label = stringResource(R.string.password),
        isError = userState.passwordErrorMsg != null,
        onTrailingIconClick = { passwordVisible = !passwordVisible },
        supportingText = userState.passwordErrorMsg,
        visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = if(passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
        modifier = modifierStandard
    )
    InputTextField(
        value = userState.retypePassword,
        onValueChanged = { onNewPasswordChange(it) },
        label = stringResource(R.string.rewritepassword),
        isError = userState.retypePasswordErrorMsg != null,
        onTrailingIconClick = { newPasswordVisible = !newPasswordVisible },
        supportingText = userState.retypePasswordErrorMsg,
        visualTransformation = if(newPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = if(newPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
        modifier = modifierStandard
    )
}

@Composable
fun PersonalInfoOnEditProfile(
    userState: EditProfileUiState,
    onEmailChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onDeleteEmail: (String) -> Unit,
    onDeleteName: (String) -> Unit,
){

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var newPasswordVisible by rememberSaveable { mutableStateOf(false) }

    InputTextField(
        value = (userState as EditProfileUiState.EditProfileParams).user.email,
        onValueChanged = { onEmailChange(it) },
        label = stringResource(R.string.email),
        placeholder = stringResource(R.string.emailExample),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        isError = userState.emailErrorMsg != null,
        onTrailingIconClick = { onDeleteEmail(it) },
        supportingText = userState.emailErrorMsg,
        modifier = modifierStandard
    )
    InputTextField(
        value = userState.user.name,
        onValueChanged = { onNameChange(it) },
        label = stringResource(R.string.name),
        isError = userState.nameErrorMsg != null,
        onTrailingIconClick = { onDeleteName(it) },
        supportingText = userState.nameErrorMsg,
        modifier = modifierStandard
    )
    InputTextField(
        value = userState.user.password,
        onValueChanged = { onPasswordChange(it) },
        label = stringResource(R.string.password),
        isError = userState.passwordErrorMsg != null,
        onTrailingIconClick = { passwordVisible = !passwordVisible },
        supportingText = userState.passwordErrorMsg,
        visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = if(passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
        modifier = modifierStandard
    )
    InputTextField(
        value = userState.retypePassword,
        onValueChanged = { onNewPasswordChange(it) },
        label = stringResource(R.string.rewritepassword),
        isError = userState.retypePasswordErrorMsg != null,
        onTrailingIconClick = { newPasswordVisible = !newPasswordVisible },
        supportingText = userState.retypePasswordErrorMsg,
        visualTransformation = if(newPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = if(newPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
        modifier = modifierStandard
    )
}
