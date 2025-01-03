package com.CioffiDeVivo.dietideals.presentation.common.sharedComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CioffiDeVivo.dietideals.data.models.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoBottomSheet(user: User, onDismissRequest: () -> Unit) {
    ModalBottomSheet(onDismissRequest = { onDismissRequest() }) {
        Box(Modifier.fillMaxWidth().heightIn(min = 500.dp)) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Column {
                        Text(
                            text = user.name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight(600)
                        )
                        if (user.isSeller) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = null,
                                    modifier = Modifier.size(10.dp)
                                )
                                Text(text = "Verified Insertionist", fontSize = 10.sp)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = user.name + "'s Bio:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600)
                )
                Text(text = user.bio, fontSize = 12.sp)
            }
        }
    }
}