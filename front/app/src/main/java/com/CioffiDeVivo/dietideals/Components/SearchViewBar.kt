package com.CioffiDeVivo.dietideals.Components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.CioffiDeVivo.dietideals.viewmodel.MainViewModel
import com.CioffiDeVivo.dietideals.viewmodel.SearchViewModel

@Composable
fun SearchViewBar(
    modifier: Modifier = Modifier,
    categoriesToHide: MutableState<MutableSet<String>> = mutableStateOf(mutableSetOf()),
    viewModel: DietiDealsViewModel
) {
    var state by remember { mutableStateOf("") }

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
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = ""
                )
            }
        },
        trailingIcon = {
            Row {
                if (state != "") {
                    IconButton(
                        onClick = {
                            state = ""// Remove text from TextField when you press the 'X' icon
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = ""
                        )
                    }
                }
                FilterButton(categoriesToHide)
            }
        },
        singleLine = true,
        shape = RectangleShape
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun SearchViewBarPreview() {
    SearchViewBar(viewModel = SearchViewModel())
}