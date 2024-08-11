package com.CioffiDeVivo.dietideals.Components

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
import com.CioffiDeVivo.dietideals.Views.modifierStandard
import com.CioffiDeVivo.dietideals.viewmodel.state.EditProfileState
import com.CioffiDeVivo.dietideals.viewmodel.state.RegistrationState

@Composable
fun PersonalInfoOnRegisterCredentials(
    userRegistrationState: RegistrationState,
    onEmailChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onSurnameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onDeleteEmail: (String) -> Unit,
    onDeleteName: (String) -> Unit,
    onDeleteSurname: (String) -> Unit,
){

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var newPasswordVisible by rememberSaveable { mutableStateOf(false) }

    InputTextField(
        value = userRegistrationState.user.email,
        onValueChanged = { onEmailChange(it) },
        label = stringResource(R.string.email),
        placeholder = stringResource(R.string.emailExample),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        isError = userRegistrationState.emailErrorMsg != null,
        onTrailingIconClick = { onDeleteEmail(it) },
        supportingText = userRegistrationState.emailErrorMsg,
        modifier = modifierStandard
    )
    InputTextField(
        value = userRegistrationState.user.name,
        onValueChanged = { onNameChange(it) },
        label = stringResource(R.string.name),
        isError = userRegistrationState.nameErrorMsg != null,
        onTrailingIconClick = { onDeleteName(it) },
        supportingText = userRegistrationState.nameErrorMsg,
        modifier = modifierStandard
    )
    InputTextField(
        value = userRegistrationState.user.surname,
        onValueChanged = { onSurnameChange(it) },
        label = stringResource(R.string.surname),
        isError = userRegistrationState.surnameErrorMsg != null,
        onTrailingIconClick = { onDeleteSurname(it) },
        supportingText = userRegistrationState.surnameErrorMsg,
        modifier = modifierStandard
    )
    InputTextField(
        value = userRegistrationState.user.password,
        onValueChanged = { onPasswordChange(it) },
        label = stringResource(R.string.password),
        isError = userRegistrationState.passwordErrorMsg != null,
        onTrailingIconClick = { passwordVisible = !passwordVisible },
        supportingText = userRegistrationState.passwordErrorMsg,
        visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = if(passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
        modifier = modifierStandard
    )
    InputTextField(
        value = userRegistrationState.retypePassword,
        onValueChanged = { onNewPasswordChange(it) },
        label = stringResource(R.string.rewritepassword),
        isError = userRegistrationState.retypePasswordErrorMsg != null,
        onTrailingIconClick = { newPasswordVisible = !newPasswordVisible },
        supportingText = userRegistrationState.retypePasswordErrorMsg,
        visualTransformation = if(newPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = if(newPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
        modifier = modifierStandard
    )
}

@Composable
fun PersonalInfoOnEditProfile(
    userRegistrationState: EditProfileState,
    onEmailChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onSurnameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onDeleteEmail: (String) -> Unit,
    onDeleteName: (String) -> Unit,
    onDeleteSurname: (String) -> Unit,
){

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var newPasswordVisible by rememberSaveable { mutableStateOf(false) }

    InputTextField(
        value = userRegistrationState.email,
        onValueChanged = { onEmailChange(it) },
        label = stringResource(R.string.email),
        placeholder = stringResource(R.string.emailExample),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        isError = userRegistrationState.emailErrorMsg != null,
        onTrailingIconClick = { onDeleteEmail(it) },
        supportingText = userRegistrationState.emailErrorMsg,
        modifier = modifierStandard
    )
    InputTextField(
        value = userRegistrationState.name,
        onValueChanged = { onNameChange(it) },
        label = stringResource(R.string.name),
        isError = userRegistrationState.nameErrorMsg != null,
        onTrailingIconClick = { onDeleteName(it) },
        supportingText = userRegistrationState.nameErrorMsg,
        modifier = modifierStandard
    )
    InputTextField(
        value = userRegistrationState.surname,
        onValueChanged = { onSurnameChange(it) },
        label = stringResource(R.string.surname),
        isError = userRegistrationState.surnameErrorMsg != null,
        onTrailingIconClick = { onDeleteSurname(it) },
        supportingText = userRegistrationState.surnameErrorMsg,
        modifier = modifierStandard
    )
    InputTextField(
        value = userRegistrationState.password,
        onValueChanged = { onPasswordChange(it) },
        label = stringResource(R.string.password),
        isError = userRegistrationState.passwordErrorMsg != null,
        onTrailingIconClick = { passwordVisible = !passwordVisible },
        supportingText = userRegistrationState.passwordErrorMsg,
        visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = if(passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
        modifier = modifierStandard
    )
    InputTextField(
        value = userRegistrationState.newPassword,
        onValueChanged = { onNewPasswordChange(it) },
        label = stringResource(R.string.rewritepassword),
        isError = userRegistrationState.newPasswordErrorMsg != null,
        onTrailingIconClick = { newPasswordVisible = !newPasswordVisible },
        supportingText = userRegistrationState.newPasswordErrorMsg,
        visualTransformation = if(newPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = if(newPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
        modifier = modifierStandard
    )
}
