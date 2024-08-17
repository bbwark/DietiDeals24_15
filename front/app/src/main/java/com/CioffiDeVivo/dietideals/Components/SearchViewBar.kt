package com.CioffiDeVivo.dietideals.Components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun SearchViewBar(
    modifier: Modifier = Modifier,
    categoriesToHide: Set<String>,
    updateCategories: (Set<String>) -> (Unit),
    updateSearchWord: (String) -> (Unit),
    navController: NavHostController
) {
    var state by remember { mutableStateOf("") }

    TextField(
        value = state,
        onValueChange = { value ->
            state = value
            updateSearchWord(value)
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
                            updateSearchWord("")
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = ""
                        )
                    }
                }
                FilterButton(categoriesToHide, updateCategories)
            }
        },
        singleLine = true,
        shape = RectangleShape
    )
}

@SuppressLint("MutableCollectionMutableState")
@Preview
@Composable
fun SearchViewBarPreview() {
    SearchViewBar(categoriesToHide = mutableSetOf(), updateSearchWord = {}, updateCategories = {}, navController = rememberNavController())
}