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
import com.CioffiDeVivo.dietideals.presentation.ui.editProfile.EditProfileEvent
import com.CioffiDeVivo.dietideals.presentation.ui.editProfile.EditProfileUiState
import com.CioffiDeVivo.dietideals.presentation.ui.editProfile.EditProfileViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.modifierStandard
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.RegisterCredentialsUiState
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.RegisterCredentialsViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.RegistrationEvents

@Composable
fun PersonalInfoOnRegisterCredentials(
    userState: RegisterCredentialsUiState,
    viewModel: RegisterCredentialsViewModel
){

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var newPasswordVisible by rememberSaveable { mutableStateOf(false) }

    InputTextField(
        value = (userState as RegisterCredentialsUiState.RegisterParams).user.email,
        onValueChanged = { viewModel.registrationAction(RegistrationEvents.EmailChanged(it)) },
        label = stringResource(R.string.email),
        placeholder = stringResource(R.string.emailExample),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        isError = userState.emailErrorMsg != null,
        onTrailingIconClick = { viewModel.registrationAction(RegistrationEvents.EmailDeleted) },
        supportingText = userState.emailErrorMsg,
        modifier = modifierStandard
    )
    InputTextField(
        value = userState.user.name,
        onValueChanged = { viewModel.registrationAction(RegistrationEvents.NameChanged(it)) },
        label = stringResource(R.string.name),
        isError = userState.nameErrorMsg != null,
        onTrailingIconClick = { viewModel.registrationAction(RegistrationEvents.NameDeleted) },
        supportingText = userState.nameErrorMsg,
        modifier = modifierStandard
    )
    InputTextField(
        value = userState.user.password,
        onValueChanged = { viewModel.registrationAction(RegistrationEvents.PasswordChanged(it)) },
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
        onValueChanged = { viewModel.registrationAction(RegistrationEvents.RetypePasswordChanged(it)) },
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
    viewModel: EditProfileViewModel
){

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var newPasswordVisible by rememberSaveable { mutableStateOf(false) }

    InputTextField(
        value = (userState as EditProfileUiState.EditProfileParams).user.email,
        onValueChanged = { viewModel.editProfileAction(EditProfileEvent.EmailChanged(it)) },
        label = stringResource(R.string.email),
        placeholder = stringResource(R.string.emailExample),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        isError = userState.emailErrorMsg != null,
        onTrailingIconClick = { viewModel.editProfileAction(EditProfileEvent.EmailDeleted) },
        supportingText = userState.emailErrorMsg,
        modifier = modifierStandard
    )
    InputTextField(
        value = userState.user.name,
        onValueChanged = { viewModel.editProfileAction(EditProfileEvent.NameChanged(it)) },
        label = stringResource(R.string.name),
        isError = userState.nameErrorMsg != null,
        onTrailingIconClick = { viewModel.editProfileAction(EditProfileEvent.NameDeleted) },
        supportingText = userState.nameErrorMsg,
        modifier = modifierStandard
    )
    InputTextField(
        value = userState.user.password,
        onValueChanged = { viewModel.editProfileAction(EditProfileEvent.PasswordChanged(it)) },
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
        onValueChanged = { viewModel.editProfileAction(EditProfileEvent.NewPasswordChanged(it)) },
        label = stringResource(R.string.rewritepassword),
        isError = userState.retypePasswordErrorMsg != null,
        onTrailingIconClick = { newPasswordVisible = !newPasswordVisible },
        supportingText = userState.retypePasswordErrorMsg,
        visualTransformation = if(newPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = if(newPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
        modifier = modifierStandard
    )
}
