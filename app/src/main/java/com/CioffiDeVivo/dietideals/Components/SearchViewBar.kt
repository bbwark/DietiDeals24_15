package com.CioffiDeVivo.dietideals.Components

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.CioffiDeVivo.dietideals.DietiDealsViewModel

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchViewBar(
    modifier: Modifier = Modifier,
    viewModel: DietiDealsViewModel
) {
    var state by remember { mutableStateOf("pippo") }

    TextField(
        value = state,
        onValueChange = { value ->
            state = value
            //add API Request here or call to viewModel method that make API Request
            //it is possible to use a debounce modifier to delay the request of a fixed amount of time to optimize the number of the requests
        },
        modifier = modifier.fillMaxWidth(),
        leadingIcon = {
            IconButton(onClick = {
                //pop navigation back to home
            }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                )
            }
        },
        trailingIcon = {
            if (state != "") {
                IconButton(
                    onClick = {
                        state = ""// Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun SearchViewBarPreview() {
    SearchViewBar(viewModel = DietiDealsViewModel())
}