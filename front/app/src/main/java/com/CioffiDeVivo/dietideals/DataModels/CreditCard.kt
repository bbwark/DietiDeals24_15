package com.CioffiDeVivo.dietideals.DataModels

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

data class CreditCard @RequiresApi(Build.VERSION_CODES.O) constructor(
    val creditCardNumber: String = "",
    val expirationDate: LocalDate = LocalDate.now(),
    val cvv: String = "",
    val iban: String = "",
)