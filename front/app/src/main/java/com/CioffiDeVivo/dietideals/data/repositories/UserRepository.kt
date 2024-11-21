package com.CioffiDeVivo.dietideals.data.repositories

import com.CioffiDeVivo.dietideals.data.models.User
import com.CioffiDeVivo.dietideals.data.requestModels.LogInRequest
import com.CioffiDeVivo.dietideals.data.responseModels.LogInResponse
import com.CioffiDeVivo.dietideals.data.network.apiServices.UserApiService

interface UserRepository {

    suspend fun getUserById(userId: String): User
    suspend fun login(credentials: LogInRequest): LogInResponse

}

class NetworkUserRepository(private val userApiService: UserApiService): UserRepository {
    override suspend fun getUserById(userId: String): User = userApiService.getUserById(userId)
    override suspend fun login(credentials: LogInRequest): LogInResponse = userApiService.login(credentials)

}