package com.CioffiDeVivo.dietideals.presentation.common.sharedComponents

import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenuField(
    menuList: List<String>,
    label: String,
    onChange: (String) -> Unit
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var selectedElementOfList by rememberSaveable { mutableStateOf(menuList[0]) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded }
    ) {
        OutlinedTextField(
            value = selectedElementOfList,
            onValueChange = { },
            readOnly = true,
            label = { Text(text = label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
            modifier = Modifier
                .width(145.dp)
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }) {
            menuList.forEachIndexed { index, text ->
                DropdownMenuItem(
                    text = { Text(text = text) },
                    onClick = {
                        selectedElementOfList = menuList[index]
                        onChange(selectedElementOfList)
                        isExpanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}