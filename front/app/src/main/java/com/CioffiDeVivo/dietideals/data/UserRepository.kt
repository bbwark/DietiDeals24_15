package com.CioffiDeVivo.dietideals.data

import com.CioffiDeVivo.dietideals.domain.models.User
import com.CioffiDeVivo.dietideals.domain.requestModels.LogInRequest
import com.CioffiDeVivo.dietideals.network.DietiDealsApiService

interface UserRepository {

    suspend fun getUserById(userId: String): User
    suspend fun login(credentials: LogInRequest): User

}

class NetworkUserRepository(private val dietiDealsApiService: DietiDealsApiService): UserRepository {

    override suspend fun getUserById(userId: String): User = dietiDealsApiService.getUserById(userId)
    override suspend fun login(credentials: LogInRequest): User = dietiDealsApiService.login(credentials)

}