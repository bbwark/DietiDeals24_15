package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.Components.DescriptionTextfield
import com.CioffiDeVivo.dietideals.Components.DetailsViewTopBar
import com.CioffiDeVivo.dietideals.Components.InputTextField
import com.CioffiDeVivo.dietideals.Components.PersonalInfoOnEditProfile
import com.CioffiDeVivo.dietideals.Components.pulsateClick
import com.CioffiDeVivo.dietideals.Events.EditProfileEvent
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.viewmodel.EditProfileViewModel

@Composable
fun EditProfile(viewModel: EditProfileViewModel, navController: NavHostController){

    val userEditState by viewModel.userEditProfileState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PersonalInfoOnEditProfile(
            userRegistrationState = userEditState,
            onEmailChange = { viewModel.editProfileAction(EditProfileEvent.EmailChanged(it)) },
            onNameChange = { viewModel.editProfileAction(EditProfileEvent.NameChanged(it)) },
            onSurnameChange = { viewModel.editProfileAction(EditProfileEvent.SurnameChanged(it)) },
            onPasswordChange = { viewModel.editProfileAction(EditProfileEvent.PasswordChanged(it)) },
            onNewPasswordChange = { viewModel.editProfileAction(EditProfileEvent.NewPasswordChanged(it)) },
            onDeleteEmail = { viewModel.editProfileAction(EditProfileEvent.EmailDeleted(it)) },
            onDeleteName = { viewModel.editProfileAction(EditProfileEvent.NameDeleted(it)) },
            onDeleteSurname = { viewModel.editProfileAction(EditProfileEvent.SurnameDeleted(it)) }
        )
        Spacer(modifier = Modifier.height(40.dp))
        DescriptionTextfield(
            description = userEditState.description,
            onDescriptionChange = { viewModel.editProfileAction(EditProfileEvent.DescriptionChanged(it)) },
            maxDescriptionCharacters = 100,
            onDeleteDescription = { viewModel.editProfileAction(EditProfileEvent.DescriptionDeleted(it)) }
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                      viewModel.editProfileAction(EditProfileEvent.Submit)
            },
            modifier = Modifier.pulsateClick()
        ) {
            Text(text = stringResource(id = R.string.saveChanges))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EditProfilePreview(){
    EditProfile(viewModel = EditProfileViewModel(), navController = rememberNavController())
}