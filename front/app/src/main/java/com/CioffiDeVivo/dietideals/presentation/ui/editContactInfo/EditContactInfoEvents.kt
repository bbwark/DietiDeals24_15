package com.CioffiDeVivo.dietideals.presentation.ui.editContactInfo

import com.CioffiDeVivo.dietideals.data.models.Country

sealed class EditContactInfoEvents{
    data class AddressChanged(val address: String): EditContactInfoEvents()
    object AddressDeleted: EditContactInfoEvents()
    data class ZipCodeChanged(val zipCode: String): EditContactInfoEvents()
    object ZipCodeDeleted: EditContactInfoEvents()
    data class CountryChanged(val country: Country): EditContactInfoEvents()
    data class PhoneNumberChanged(val phoneNumber: String): EditContactInfoEvents()
    object PhoneNumberDeleted: EditContactInfoEvents()
    object Submit: EditContactInfoEvents()
}