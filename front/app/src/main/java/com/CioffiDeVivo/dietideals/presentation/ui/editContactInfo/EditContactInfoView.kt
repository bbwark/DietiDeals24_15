package com.CioffiDeVivo.dietideals.presentation.ui.editContactInfo

import android.app.Application
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.ContactInfo
import com.CioffiDeVivo.dietideals.animations.pulsateClick
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.data.validations.ValidationState
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.presentation.ui.loading.LoadingView
import com.CioffiDeVivo.dietideals.presentation.ui.retry.RetryView

@Composable
fun EditContactInfoView(viewModel: EditContactInfoViewModel, navController: NavController){
    
    val editContactInfoUiState by viewModel.editContactInfoUiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit){
        viewModel.getUserInfo()
        viewModel.validationEditContactInfoEvents.collect { event ->
            when(event){
                is ValidationState.Success -> {
                }
                else -> { Toast.makeText(context, "Invalid Field", Toast.LENGTH_SHORT).show() }
            }
        }
    }


    when(editContactInfoUiState){
        is EditContactInfoUiState.Error -> RetryView(onClick = {
            navController.popBackStack()
            navController.navigate(Screen.EditContactInfo.route)
        })
        is EditContactInfoUiState.Loading -> LoadingView()
        is EditContactInfoUiState.Success -> {
            if (navController.currentBackStackEntry?.destination?.route != Screen.Account.route) {
                navController.popBackStack()
            }
        }
        is EditContactInfoUiState.EditContactInfoParams -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                ContactInfo(
                    userState = editContactInfoUiState,
                    onAddressChange = { viewModel.editContactInfoAction(EditContactInfoEvents.AddressChanged(it)) },
                    onCountryChange = { viewModel.editContactInfoAction(EditContactInfoEvents.CountryChanged(it)) },
                    onZipCodeChange = { viewModel.editContactInfoAction(EditContactInfoEvents.ZipCodeChanged(it)) },
                    onPhoneNumberChange = { viewModel.editContactInfoAction(EditContactInfoEvents.PhoneNumberChanged(it)) },
                    onDeleteAddress = { viewModel.editContactInfoAction(EditContactInfoEvents.AddressChanged(it)) },
                    onDeleteZipCode = { viewModel.editContactInfoAction(EditContactInfoEvents.AddressChanged(it)) },
                    onDeletePhoneNumber = { viewModel.editContactInfoAction(EditContactInfoEvents.AddressChanged(it)) }
                )
                Button(
                    onClick = {
                        viewModel.editContactInfoAction(EditContactInfoEvents.Submit())
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