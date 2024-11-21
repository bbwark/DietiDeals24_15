package com.CioffiDeVivo.dietideals.presentation.ui.editContactInfo

import com.CioffiDeVivo.dietideals.data.models.Country

sealed class EditContactInfoEvents{
    data class AddressChanged(val address: String) : EditContactInfoEvents()
    data class AddressDeleted(val address: String) : EditContactInfoEvents()
    data class ZipCodeChanged(val zipcode: String) : EditContactInfoEvents()
    data class ZipCodeDeleted(val address: String) : EditContactInfoEvents()
    data class CountryChanged(val country: Country) : EditContactInfoEvents()
    data class PhoneNumberChanged(val phoneNumber: String) : EditContactInfoEvents()
    data class PhoneNumberDeleted(val address: String) : EditContactInfoEvents()
    data class Submit(val submitted: Boolean = true) : EditContactInfoEvents()
}