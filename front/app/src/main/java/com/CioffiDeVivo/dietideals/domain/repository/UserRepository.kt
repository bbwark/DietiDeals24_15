package com.CioffiDeVivo.dietideals.domain.repository

import com.CioffiDeVivo.dietideals.domain.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object UserRepository{
    private val _loggedUser: MutableStateFlow<User?> = MutableStateFlow(User("1"))
    val loggedUser: StateFlow<User?> = _loggedUser.asStateFlow()

    fun login(user: User) {
        _loggedUser.value = user
    }

    fun logout() {
        _loggedUser.value = null
    }
}