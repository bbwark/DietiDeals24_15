package com.CioffiDeVivo.dietideals.Views

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.Components.DescriptionTextfield
import com.CioffiDeVivo.dietideals.Components.DetailsViewTopBar
import com.CioffiDeVivo.dietideals.Components.InputTextField
import com.CioffiDeVivo.dietideals.Components.PasswordsTextfields
import com.CioffiDeVivo.dietideals.Components.ViewTitle
import com.CioffiDeVivo.dietideals.DataModels.EditProfileEvent
import com.CioffiDeVivo.dietideals.DataModels.User
import com.CioffiDeVivo.dietideals.DietiDealsViewModel
import com.CioffiDeVivo.dietideals.R
import java.util.UUID

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
            onValueChanged = { viewModel.editProfileAction(EditProfileEvent.NameChanged(it)) },
            label = stringResource(R.string.name),
            trailingIcon = Icons.Filled.Clear,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        DescriptionTextfield(
            description = userEditState.bio,
            descriptionOnChange = { viewModel.editProfileAction(EditProfileEvent.DescriptionChanged(it)) },
            maxDescriptionCharacters = 100
        )
        Spacer(modifier = Modifier.height(40.dp))
        PasswordsTextfields(
            password = userEditState.password,
            passwordOnChange = { viewModel.editProfileAction(EditProfileEvent.PasswordChanged(it)) },
            isToChangePassword = true
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(onClick = { /*TODO*/ }) {
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