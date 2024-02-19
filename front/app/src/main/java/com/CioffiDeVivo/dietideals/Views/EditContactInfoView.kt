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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.Components.ContactInfo
import com.CioffiDeVivo.dietideals.Components.DetailsViewTopBar
import com.CioffiDeVivo.dietideals.DietiDealsViewModel
import com.CioffiDeVivo.dietideals.Events.EditContactInfoEvents
import com.CioffiDeVivo.dietideals.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditContactInfoView(viewModel: DietiDealsViewModel, navController: NavHostController){
    
    val userContactinfoState by viewModel.userState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        DetailsViewTopBar(
            caption = stringResource(R.string.contactInfo),
            destinationRoute = "",
            navController = navController
        )
        ContactInfo(
            user = userContactinfoState,
            onAddressChange = { viewModel.editContactInfoAction(EditContactInfoEvents.AddressChanged(it)) },
            onZipCodeChange = { viewModel.editContactInfoAction(EditContactInfoEvents.ZipCodeChanged(it)) },
            onCountryChange = { viewModel.editContactInfoAction(EditContactInfoEvents.CountryChanged(it)) },
            onPhoneNumberChange = { viewModel.editContactInfoAction(EditContactInfoEvents.PhoneNumberChanged(it)) }
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
fun EditContactInfoPreview(){
    EditContactInfoView(viewModel = DietiDealsViewModel(), navController = rememberNavController())
}