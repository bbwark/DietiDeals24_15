package com.CioffiDeVivo.dietideals.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun PasswordsTextfields(
    password: String,
    passwordOnChange: (String) -> Unit,
    isToChangePassword: Boolean
){

    var newPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var newpasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = { passwordOnChange(it) },
        label = {
            if (isToChangePassword){
                Text(text = "Old Password")
            }else{
                Text(text = "Password")
            }
        },
        supportingText = {
                         if (isToChangePassword){
                             Text(text = "Insert old password to change it into a new one")
                         }else{
                             Column {
                                 Text(text = "8-16 characters")
                                 Text(text = "a digit")
                                 Text(text = "a letter")
                             }
                         }
        },
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
    )
    Spacer(modifier = Modifier.height(10.dp))
    OutlinedTextField(
        value = newPassword,
        onValueChange = { newPassword = it },
        label = {
                if(isToChangePassword){
                    Text(text = "New Password")
                }else{
                    Text(text = "Re-write Password")
                }
        },
        supportingText = {
                         if (isToChangePassword){
                             Column() {
                                 Text(text = "8-16 characters")
                                 Text(text = "a digit")
                                 Text(text = "a letter")
                             }
                         }
        },
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
    )
}