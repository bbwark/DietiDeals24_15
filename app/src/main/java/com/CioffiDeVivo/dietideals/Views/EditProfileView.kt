package com.CioffiDeVivo.dietideals.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.Components.ViewTitle
import com.CioffiDeVivo.dietideals.DataModels.User
import com.CioffiDeVivo.dietideals.DietiDealsViewModel
import com.CioffiDeVivo.dietideals.R
import java.util.UUID

@Composable
fun EditProfile(viewModel: DietiDealsViewModel, navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        ViewTitle(title = stringResource(id = R.string.editProfile))
        Spacer(modifier = Modifier.height(40.dp))
        EditProfileComposable()
    }
}

@Composable
fun EditProfileComposable(){
    var user = User(UUID.randomUUID(),"test","test","test")
    var newPassword by remember{ mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var newpasswordVisible by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }
    val maxDescriptionCharacters = 100
    OutlinedTextField(
        value = user.name,
        onValueChange = { user.name = it },
        singleLine = true,
        trailingIcon = {
            Icon(
                Icons.Rounded.Clear,
                contentDescription = null,
                modifier = Modifier.clickable{user.name = ""}
            )
        },
        modifier = Modifier.width(320.dp),
        label = { Text("name") },
    )
    Spacer(modifier = Modifier.height(40.dp))
    OutlinedTextField(
        value = description,
        onValueChange = {
                        if(it.length <= maxDescriptionCharacters){
                            description = it
                        }
        },
        supportingText = {
                         Text(
                             text = "${description.length} / $maxDescriptionCharacters",
                             modifier = Modifier.fillMaxWidth(),
                             textAlign = TextAlign.End
                         )
        },
        singleLine = false,
        maxLines = 7,
        trailingIcon = {
            Icon(
                Icons.Rounded.Clear,
                contentDescription = null,
                modifier = Modifier.clickable{description = ""}
            )
        },
        modifier = Modifier.size(320.dp,200.dp),
        label = { Text("Description") },
    )
    Spacer(modifier = Modifier.height(40.dp))
    OutlinedTextField(
        value = user.password,
        onValueChange = { user.password = it },
        label = { Text("Old Password") },
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if(passwordVisible){
                Icons.Filled.Visibility
            }else{
                Icons.Filled.VisibilityOff
            }
            val descriptionPass = if (passwordVisible) "Hide password" else "Show password"
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector  = image, descriptionPass)
            }
        },
        modifier = Modifier.width(320.dp),
    )
    Spacer(modifier = Modifier.height(40.dp))
    OutlinedTextField(
        value = newPassword,
        onValueChange = { newPassword = it },
        label = { Text("New Password") },
        singleLine = true,
        visualTransformation = if (newpasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if(newpasswordVisible){
                Icons.Filled.Visibility
            }else{
                Icons.Filled.VisibilityOff
            }
            val descriptionNewPass = if (newpasswordVisible) "Hide password" else "Show password"
            IconButton(onClick = { newpasswordVisible = !newpasswordVisible }) {
                Icon(imageVector  = image, descriptionNewPass)
            }
        },
        modifier = Modifier.width(320.dp),
    )
    Spacer(modifier = Modifier.height(40.dp))
    Button(onClick = { /*TODO*/ }) {
        Text(text = stringResource(id = R.string.saveChanges))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EditProfilePreview(){
    EditProfile(viewModel = DietiDealsViewModel(), navController = rememberNavController())
}