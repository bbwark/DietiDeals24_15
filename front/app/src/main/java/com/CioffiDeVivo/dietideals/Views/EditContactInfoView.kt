package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.CioffiDeVivo.dietideals.Components.ContactInfo
import com.CioffiDeVivo.dietideals.Components.DetailsViewTopBar
import com.CioffiDeVivo.dietideals.Components.pulsateClick
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.viewmodel.EditContactInfoViewModel
import com.CioffiDeVivo.dietideals.viewmodel.state.RegistrationState

@Composable
fun EditContactInfoView(viewModel: EditContactInfoViewModel, navController: NavHostController){
    
    val userContactInfoState by viewModel.userEditContactInfoState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ContactInfo(
            userState = userContactInfoState,
            onAddressChange = { viewModel.updateAddress(it) },
            onZipCodeChange = { viewModel.updateZipCode(it) },
            onCountryChange = { viewModel.updateCountry(it) },
            onPhoneNumberChange = { viewModel.updatePhoneNumber(it) },
            onDeleteAddress = { viewModel.deleteAddress() },
            onDeleteZipCode = { viewModel.deleteZipCode() },
            onDeletePhoneNumber = { viewModel.deletePhoneNumber() }
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

@Preview(showBackground = true)
@Composable
fun EditContactInfoPreview(){
    EditContactInfoView(viewModel = EditContactInfoViewModel(), navController = rememberNavController())
}