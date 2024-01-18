package com.CioffiDeVivo.dietideals.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.DataModels.User
import java.util.UUID

@Composable
fun ContactInfo(){
    var zipCode by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var user = User(UUID.randomUUID(),"test","test","test")

    OutlinedTextField(
        value = user.address.toString(),
        onValueChange = { user.address = it },
        singleLine = true,
        trailingIcon = {
            Icon(
                Icons.Rounded.Clear,
                contentDescription = null,
                modifier = Modifier.clickable{user.address = ""}
            )
        },
        modifier = Modifier.width(320.dp),
        label = { Text("Address") },
    )
    Spacer(modifier = Modifier.height(10.dp))
    Row {
        OutlinedTextField(
            value = zipCode,
            onValueChange = { zipCode = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            trailingIcon = {
                Icon(
                    Icons.Rounded.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable{zipCode = ""}
                )
            },
            modifier = Modifier.width(150.dp),
            label = { Text("Zip Code") },
        )
        Spacer(modifier = Modifier.width(20.dp))
        OutlinedTextField(
            value = country,
            onValueChange = { country = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            trailingIcon = {
                Icon(
                    Icons.Rounded.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable{country = ""}
                )
            },
            modifier = Modifier.width(150.dp),
            label = { Text("Country") },
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    OutlinedTextField(
        value = user.phoneNumber.toString(),
        onValueChange = { user.phoneNumber = it },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        trailingIcon = {
            Icon(
                Icons.Rounded.Clear,
                contentDescription = null,
                modifier = Modifier.clickable{user.phoneNumber = ""}
            )
        },
        modifier = Modifier.width(320.dp),
        label = { Text("Phone Number") },
    )
}