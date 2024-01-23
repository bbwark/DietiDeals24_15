package com.CioffiDeVivo.dietideals.Events

import com.CioffiDeVivo.dietideals.DataModels.LoginEvent

sealed class EditContactInfoEvents {
    data class AddressChanged(val address: String) : EditContactInfoEvents()
    data class ZipCodeChanged(val zipcode: String) : EditContactInfoEvents()
    data class CountryChanged(val country: String) : EditContactInfoEvents()
    data class PhoneNumberChanged(val phoneNumber: String) : EditContactInfoEvents()
    object Submit: EditContactInfoEvents()
}