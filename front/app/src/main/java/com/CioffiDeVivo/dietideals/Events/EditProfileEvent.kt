package com.CioffiDeVivo.dietideals.Events

sealed class EditProfileEvent {
    data class NameChanged(val name: String) : EditProfileEvent()
    data class SurnameChanged(val surname: String) : EditProfileEvent()
    data class PasswordChanged(val password: String) : EditProfileEvent()
    data class NewPasswordChanged(val newPassword: String) : EditProfileEvent()
    data class DescriptionChanged(val description: String) : EditProfileEvent()
    object Submit: EditProfileEvent()
}