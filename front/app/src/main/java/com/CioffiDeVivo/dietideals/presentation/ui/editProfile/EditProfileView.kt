package com.CioffiDeVivo.dietideals.presentation.ui.editProfile

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.DescriptionTextfield
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.PersonalInfoOnEditProfile
import com.CioffiDeVivo.dietideals.animations.pulsateClick
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.data.validations.ValidationState
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.presentation.ui.loading.LoadingView
import com.CioffiDeVivo.dietideals.presentation.ui.retry.RetryView

@Composable
fun EditProfile(viewModel: EditProfileViewModel, navController: NavController){

    val editProfileUiState by viewModel.editProfileUiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit){
        viewModel.getUserInfo()
    }
    LaunchedEffect(Unit){
        viewModel.validationEditProfileEvent.collect { event ->
            when(event){
                is ValidationState.Success -> {
                }
                else -> { Toast.makeText(context, "Invalid Field", Toast.LENGTH_SHORT).show() }
            }
        }
    }

    when(editProfileUiState){
        is EditProfileUiState.Loading -> LoadingView()
        is EditProfileUiState.Error -> RetryView(
            onClick = {
                navController.popBackStack()
                navController.navigate(Screen.EditProfile.route)
            }
        )
        is EditProfileUiState.Success -> {
            if (navController.currentBackStackEntry?.destination?.route != Screen.Account.route) {
                navController.popBackStack()
            }
        }
        is EditProfileUiState.EditProfileParams -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                PersonalInfoOnEditProfile(
                    userState = editProfileUiState,
                    viewModel = viewModel
                )
                DescriptionTextfield(
                    description = (editProfileUiState as EditProfileUiState.EditProfileParams).user.bio,
                    onDescriptionChange = { viewModel.editProfileAction(EditProfileEvent.DescriptionChanged(it)) },
                    maxDescriptionCharacters = 300,
                    onDeleteDescription = { viewModel.editProfileAction(EditProfileEvent.DescriptionDeleted) }
                )
                Button(
                    onClick = {
                        viewModel.editProfileAction(EditProfileEvent.Submit)
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
    }
}