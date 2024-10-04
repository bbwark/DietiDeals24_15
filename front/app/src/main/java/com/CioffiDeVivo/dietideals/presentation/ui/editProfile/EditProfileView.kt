package com.CioffiDeVivo.dietideals.presentation.ui.editProfile

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.DescriptionTextfield
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.PersonalInfoOnEditProfile
import com.CioffiDeVivo.dietideals.animations.pulsateClick
import com.CioffiDeVivo.dietideals.R

@Composable
fun EditProfile(viewModel: EditProfileViewModel, navController: NavHostController){

    val userEditState by viewModel.userEditProfileState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
        DescriptionTextfield(
            description = userEditState.user.bio,
            onDescriptionChange = { viewModel.editProfileAction(EditProfileEvent.DescriptionChanged(it)) },
            maxDescriptionCharacters = 300,
            onDeleteDescription = { viewModel.editProfileAction(EditProfileEvent.DescriptionDeleted(it)) }
        )
        Button(
            onClick = {
                      viewModel.editProfileAction(EditProfileEvent.Submit())
            },
            modifier = Modifier
                .wrapContentWidth()
                .padding(bottom = 8.dp)
                .pulsateClick(),
        ) {
            Text(text = stringResource(id = R.string.saveChanges))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EditProfilePreview(){
    EditProfile(viewModel = EditProfileViewModel(Application()), navController = rememberNavController())
}