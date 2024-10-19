package com.CioffiDeVivo.dietideals.presentation.common.sharedComponents

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.CioffiDeVivo.dietideals.R

@Composable
fun DialogAlert(
    showDialog: MutableState<Boolean>,
    dialogText: String
){
    if(showDialog.value){
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(stringResource(R.string.alert)) },
            text = { Text(dialogText) },
            confirmButton = {
                TextButton(
                    onClick = { showDialog.value = false }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}

@Composable
fun DialogInfo(
    showDialog: MutableState<Boolean>,
    dialogText: String
){
    if(showDialog.value){
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(stringResource(R.string.info)) },
            text = { Text(dialogText) },
            confirmButton = {
                TextButton(
                    onClick = { showDialog.value = false }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}