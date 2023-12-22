package com.CioffiDeVivo.dietideals

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import com.CioffiDeVivo.dietideals.DataModels.User
import java.util.UUID

class DietiDealsViewModel : ViewModel() {
    var user by mutableStateOf(User(UUID.randomUUID(), "usertest","emailtest","passwordtest"))
}