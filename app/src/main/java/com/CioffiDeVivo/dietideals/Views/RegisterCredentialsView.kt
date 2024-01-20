package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.Components.ContactInfo
import com.CioffiDeVivo.dietideals.DietiDealsViewModel
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.Components.MyDatePickerDialog
import com.CioffiDeVivo.dietideals.Components.PasswordsTextfields
import com.CioffiDeVivo.dietideals.Components.ViewTitle
import com.CioffiDeVivo.dietideals.Components.pulsateClick
import com.CioffiDeVivo.dietideals.DataModels.CreditCard
import com.CioffiDeVivo.dietideals.DataModels.User
import com.CioffiDeVivo.dietideals.DataModels.UserTest

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterCredentialsView(viewModel: DietiDealsViewModel){

    val usertest by viewModel.user1.collectAsState()
    val isEnabled by remember { mutableStateOf(true) }
    var isSellerButton by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        ViewTitle(title = stringResource(id = R.string.createAccount))
        Spacer(modifier = Modifier.height(30.dp))
        BuyerComposables(
            user = usertest,
            emailOnChange = { viewModel.updateEmailTextField(it) },
            nameOnChange = { viewModel.updateNameTextField(it) },
            surnameOnChange = { viewModel.updateSurnameTextField(it) },
            viewModel = viewModel
        )
        Spacer(modifier = Modifier.height(10.dp))
        if(isSellerButton){
            SellerAccountComposables()
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {  },
            enabled = if( viewModel.user.name.isNotEmpty() && viewModel.user.email.isNotEmpty() && viewModel.user.password.isNotEmpty()){
                isEnabled
            }else{
                !isEnabled
            },
            modifier = Modifier
                .size(width = 330.dp, height = 50.dp)
                .pulsateClick(),
            content = {
                Text(stringResource(R.string.createAccount), fontSize = 20.sp)
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            //If clicked isSeller on the user data class is true
            IconButton(onClick = {
                isSellerButton = !isSellerButton
            }) {
                val checkedBox = if(isSellerButton){
                    Icons.Filled.CheckBox
                }else{
                    Icons.Filled.CheckBoxOutlineBlank
                }
                Icon(imageVector  = checkedBox, null)
            }
            Text("Do you want to create a Seller Account?")
        }
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                "Do you have an Account? ",

                )
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    "Log In",
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BuyerComposables(
    user: UserTest,
    emailOnChange: (String) -> Unit,
    nameOnChange: (String) -> Unit,
    surnameOnChange: (String) -> Unit,
    viewModel: DietiDealsViewModel
) {

    OutlinedTextField(
        value = user.email,
        onValueChange = emailOnChange,
        singleLine = true,
        trailingIcon = {
            Icon(
                Icons.Rounded.Clear,
                contentDescription = null,
                modifier = Modifier.clickable { viewModel.cancelEmailTextField() }
            )
        },
        modifier = Modifier.width(320.dp),
        label = { Text("Email") },
    )
    Spacer(modifier = Modifier.height(10.dp))
    OutlinedTextField(
        value = user.name,
        onValueChange = nameOnChange,
        singleLine = true,
        trailingIcon = {
            Icon(
                Icons.Rounded.Clear,
                contentDescription = null,
                modifier = Modifier.clickable { viewModel.cancelNameTextField() }
            )
        },
        modifier = Modifier.width(320.dp),
        label = { Text("Name") },
    )
    Spacer(modifier = Modifier.height(10.dp))
    OutlinedTextField(
        value = user.surname,
        onValueChange = surnameOnChange,
        singleLine = true,
        trailingIcon = {
            Icon(
                Icons.Rounded.Clear,
                contentDescription = null,
                modifier = Modifier.clickable { viewModel.cancelSurnameTextField() }
            )
        },
        modifier = Modifier.width(320.dp),
        label = { Text("Surname") },
    )
    Spacer(modifier = Modifier.height(10.dp))
    PasswordsTextfields(isToChangePassword = false)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerAccountComposables(){

    var creditCardNumber by remember { mutableStateOf("") }
    var creditCardCvv by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("Open date picker dialog") }
    var showDatePicker by remember { mutableStateOf(false) }
    var expirationDate by remember { mutableStateOf("") }


    ContactInfo()
    Spacer(modifier = Modifier.height(10.dp))
    Row (
        verticalAlignment = Alignment.CenterVertically
    ){
        OutlinedTextField(
            value = creditCardNumber,
            onValueChange = { creditCardNumber = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            trailingIcon = {
                Icon(
                    Icons.Rounded.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable{ creditCardNumber = ""}
                )
            },
            modifier = Modifier.width(200.dp),
            label = { Text("Credit Card") },
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            OutlinedTextField(
                value = expirationDate,
                onValueChange = { expirationDate = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                trailingIcon = {
                    Icon(
                        Icons.Rounded.CalendarMonth,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { showDatePicker = true }
                            .pulsateClick(),
                    )
                },
                modifier = Modifier.size(width = 110.dp, height = 40.dp),
                label = { Text(
                    "MM/AA",
                    fontSize = 8.sp
                    ) },
            )
            if(showDatePicker){
                MyDatePickerDialog(
                    onDateSelected = { date = it },
                    onDismiss = { showDatePicker = false }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = creditCardCvv,
                onValueChange = { creditCardCvv = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                trailingIcon = {
                    Icon(
                        Icons.Rounded.Clear,
                        contentDescription = null,
                        modifier = Modifier.clickable{ creditCardCvv = ""}
                    )
                },
                modifier = Modifier.size(width = 110.dp, height = 40.dp),
                label = { Text(
                    "CVV",
                    fontSize = 8.sp
                ) },            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun RegisterCredentialsPreview(){
    RegisterCredentialsView(viewModel = DietiDealsViewModel())
}

