package com.CioffiDeVivo.dietideals.DataModels

sealed class RegistrationEvent {
    data class EmailChanged(val email: String): RegistrationEvent()
    data class NameChanged(val name: String): RegistrationEvent()
    data class SurnameChanged(val surname: String): RegistrationEvent()
    object Submit: RegistrationEvent()
}