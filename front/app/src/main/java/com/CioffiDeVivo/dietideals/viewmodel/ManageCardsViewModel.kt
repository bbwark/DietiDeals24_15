package com.CioffiDeVivo.dietideals.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.CioffiDeVivo.dietideals.domain.DataModels.CreditCard
import com.CioffiDeVivo.dietideals.domain.DataModels.User
import com.CioffiDeVivo.dietideals.viewmodel.state.EditProfileState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.util.UUID

class ManageCardsViewModel : ViewModel(){

    private val _userCardsState = MutableStateFlow(EditProfileState())
    val userCardsState: StateFlow<EditProfileState> = _userCardsState.asStateFlow()

    var user by mutableStateOf(
        User(
            UUID.randomUUID(),
            "Nametest Surnametest",
            "",
            "passwordtest",
            creditCards = arrayOf(
                CreditCard("556666666666", LocalDate.now().plusYears(1),"222"),
                CreditCard("456666666666", LocalDate.now().plusYears(2), "222"),
                CreditCard("356666666666", LocalDate.now().plusYears(2), "222")
            )
        )
    )

}