package com.CioffiDeVivo.dietideals.presentation.ui.editProfile

sealed class EditProfileEvent{
    data class EmailChanged(val email: String): EditProfileEvent()
    data class EmailDeleted(val email: String): EditProfileEvent()
    data class NameChanged(val name: String): EditProfileEvent()
    data class NameDeleted(val email: String): EditProfileEvent()
    data class SurnameChanged(val surname: String): EditProfileEvent()
    data class SurnameDeleted(val email: String): EditProfileEvent()
    data class PasswordChanged(val password: String): EditProfileEvent()
    data class NewPasswordChanged(val newPassword: String): EditProfileEvent()
    data class DescriptionChanged(val description: String) : EditProfileEvent()
    data class DescriptionDeleted(val description: String) : EditProfileEvent()
    data class Submit(val submitted: Boolean = true) : EditProfileEvent()
}