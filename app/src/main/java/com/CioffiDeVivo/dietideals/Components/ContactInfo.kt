package com.CioffiDeVivo.dietideals.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowCircleDown
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.DataModels.UserTest

@Composable
fun ContactInfo(
    user: UserTest,
    addressOnChange: (String) -> Unit,
    zipCodeOnChange: (String) -> Unit,
    countryOnChange: (String) -> Unit,
    phoneNumberOnChange: (String) -> Unit
){

    OutlinedTextField(
        value = user.address,
        onValueChange = { addressOnChange(it) },
        singleLine = true,
        trailingIcon = {
            Icon(
                Icons.Rounded.Clear,
                contentDescription = null,
                modifier = Modifier.clickable{  }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp),
        label = { Text("Address") },
    )
    Spacer(modifier = Modifier.height(10.dp))
    Row {
        OutlinedTextField(
            value = user.zipCode,
            onValueChange = { zipCodeOnChange(it) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            trailingIcon = {
                Icon(
                    Icons.Rounded.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable{  }
                )
            },
            modifier = Modifier.width(150.dp),
            label = { Text("Zip Code") },
        )
        Spacer(modifier = Modifier.width(30.dp))
        OutlinedTextField(
            value = user.country,
            onValueChange = { countryOnChange(it) },
            singleLine = true,
            trailingIcon = {
                Icon(
                    Icons.Rounded.ArrowCircleDown,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { /*Lista di paesi*/ }
                        .pulsateClick()
                )
            },
            modifier = Modifier.width(150.dp),
            label = { Text("Country") },
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    OutlinedTextField(
        value = user.phoneNumber,
        onValueChange = { phoneNumberOnChange(it) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        trailingIcon = {
            Icon(
                Icons.Rounded.Clear,
                contentDescription = null,
                modifier = Modifier.clickable{  }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp),
        label = { Text("Phone Number") },
    )
}
