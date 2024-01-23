package com.CioffiDeVivo.dietideals.DataModels

sealed class EditProfileEvent {
    data class NameChanged(val name: String) : EditProfileEvent()
    data class PasswordChanged(val password: String) : EditProfileEvent()
    data class DescriptionChanged(val description: String) : EditProfileEvent()
    object Submit: EditProfileEvent()
}