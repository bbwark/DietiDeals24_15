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
import com.CioffiDeVivo.dietideals.Components.DescriptionTextfield
import com.CioffiDeVivo.dietideals.Components.DetailsViewTopBar
import com.CioffiDeVivo.dietideals.Components.InputTextField
import com.CioffiDeVivo.dietideals.Components.PasswordsTextfields
import com.CioffiDeVivo.dietideals.Components.pulsateClick
import com.CioffiDeVivo.dietideals.DietiDealsViewModel
import com.CioffiDeVivo.dietideals.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditProfile(viewModel: DietiDealsViewModel, navController: NavHostController){

    val userEditState by viewModel.userState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DetailsViewTopBar(
            caption = stringResource(R.string.editProfile),
            destinationRoute = "",
            navController = navController
        )
        InputTextField(
            value = userEditState.name,
            onValueChanged = { viewModel.updateName(it) },
            label = stringResource(R.string.name),
            trailingIcon = Icons.Filled.Clear,
            onDelete = { viewModel.deleteName() },
            modifier = modifierStandard
        )
        InputTextField(
            value = userEditState.surname,
            onValueChanged = { viewModel.updateSurname(it) },
            label = stringResource(R.string.surname),
            trailingIcon = Icons.Filled.Clear,
            onDelete = { viewModel.deleteSurname() },
            modifier = modifierStandard
        )
        Spacer(modifier = Modifier.height(40.dp))
        DescriptionTextfield(
            description = userEditState.bio,
            descriptionOnChange = { viewModel.updateBio(it) },
            maxDescriptionCharacters = 100,
            onDeleteDescription = { viewModel.deleteBio() }
        )
        Spacer(modifier = Modifier.height(40.dp))
        PasswordsTextfields(
            user = userEditState,
            onPasswordChange = { viewModel.updatePassword(it) },
            label = stringResource(R.string.password),
            supportingText = stringResource(R.string.passcharacters)
        )
        PasswordsTextfields(
            user = userEditState,
            onPasswordChange = { viewModel.updateNewPassword(it) },
            label = stringResource(R.string.rewritepassword),
            supportingText = ""
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.pulsateClick()
        ) {
            Text(text = stringResource(id = R.string.saveChanges))
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EditProfilePreview(){
    EditProfile(viewModel = DietiDealsViewModel(), navController = rememberNavController())
}