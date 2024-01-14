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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CioffiDeVivo.dietideals.DietiDealsViewModel
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.Components.MyDatePickerDialog
import com.CioffiDeVivo.dietideals.Components.pulsateClick
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterCredentialsView(viewModel: DietiDealsViewModel){
    var email: String by remember { mutableStateOf("") }
    var name: String by remember { mutableStateOf("") }
    var surname: String by remember { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var isEnabled by remember { mutableStateOf(true) }
    var isSellerButton by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            "Creation of an Account",
            fontSize = 30.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            singleLine = true,
            trailingIcon = {
                Icon(
                    Icons.Rounded.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable{email = ""}
                )
            },
            modifier = Modifier.width(320.dp),
            label = { Text("Email") },
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            singleLine = true,
            trailingIcon = {
                Icon(
                    Icons.Rounded.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable{name = ""}
                )
            },
            modifier = Modifier.width(320.dp),
            label = { Text("Name") },
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = surname,
            onValueChange = { surname = it },
            singleLine = true,
            trailingIcon = {
                Icon(
                    Icons.Rounded.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable{surname = ""}
                )
            },
            modifier = Modifier.width(320.dp),
            label = { Text("Surname") },
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if(passwordVisible){
                    Icons.Filled.Visibility
                }else{
                    Icons.Filled.VisibilityOff
                }
                val description = if (passwordVisible) "Hide password" else "Show password"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector  = image, description)
                }
            },
            modifier = Modifier.width(320.dp),
        )
        /*Scritta per le regole per la password*/
        Spacer(modifier = Modifier.height(10.dp))
        if(isSellerButton){
            SellerAccountComposables()
        }
        SellerAccountComposables()
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {  },
            enabled = if( email.isNotEmpty() && name.isNotEmpty() && surname.isNotEmpty() && password.isNotEmpty()){
                isEnabled
            }else{
                !isEnabled
            },
            modifier = Modifier.size(width = 330.dp, height = 50.dp).pulsateClick(),
            content = {
                Text(stringResource(R.string.CreateAccount), fontSize = 20.sp)
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            //If clicked isSeller on the user data class is true
            IconButton(onClick = {
                isSellerButton = !isSellerButton
                viewModel.sellerShowComposables = !viewModel.sellerShowComposables
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerAccountComposables(

){
    var address by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var creditCardNumber by remember { mutableStateOf("") }
    var crediCardDate by remember { mutableStateOf("") }
    var creditCardCVV by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("Open date picker dialog") }
    var showDatePicker by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = address,
        onValueChange = { address = it },
        singleLine = true,
        trailingIcon = {
            Icon(
                Icons.Rounded.Clear,
                contentDescription = null,
                modifier = Modifier.clickable{address = ""}
            )
        },
        modifier = Modifier.width(320.dp),
        label = { Text("Address") },
    )
    Spacer(modifier = Modifier.height(10.dp))
    OutlinedTextField(
        value = phoneNumber,
        onValueChange = { phoneNumber = it },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        trailingIcon = {
            Icon(
                Icons.Rounded.Clear,
                contentDescription = null,
                modifier = Modifier.clickable{phoneNumber = ""}
            )
        },
        modifier = Modifier.width(320.dp),
        label = { Text("Phone Number") },
    )
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
                    modifier = Modifier.clickable{creditCardNumber = ""}
                )
            },
            modifier = Modifier.width(200.dp),
            label = { Text("Credit Card") },
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            OutlinedTextField(
                value = crediCardDate,
                onValueChange = { crediCardDate = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                trailingIcon = {
                    Icon(
                        Icons.Rounded.CalendarMonth,
                        contentDescription = null,
                        modifier = Modifier.clickable{ showDatePicker = true },
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
                value = creditCardCVV,
                onValueChange = { creditCardCVV = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                trailingIcon = {
                    Icon(
                        Icons.Rounded.Clear,
                        contentDescription = null,
                        modifier = Modifier.clickable{creditCardCVV = ""}
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

