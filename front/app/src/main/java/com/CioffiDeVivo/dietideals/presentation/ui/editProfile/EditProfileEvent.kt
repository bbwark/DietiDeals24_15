package com.CioffiDeVivo.dietideals.presentation.ui.editProfile

sealed class EditProfileEvent{
    data class EmailChanged(val email: String): EditProfileEvent()
    object EmailDeleted: EditProfileEvent()
    data class NameChanged(val name: String): EditProfileEvent()
    object NameDeleted: EditProfileEvent()
    data class PasswordChanged(val password: String): EditProfileEvent()
    data class NewPasswordChanged(val newPassword: String): EditProfileEvent()
    data class DescriptionChanged(val description: String): EditProfileEvent()
    object DescriptionDeleted: EditProfileEvent()
    object Submit: EditProfileEvent()
}