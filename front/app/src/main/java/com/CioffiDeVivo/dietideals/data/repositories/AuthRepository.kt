package com.CioffiDeVivo.dietideals.data.repositories

import com.CioffiDeVivo.dietideals.data.models.User
import com.CioffiDeVivo.dietideals.data.network.apiServices.AuthApiService
import com.CioffiDeVivo.dietideals.data.requestModels.LogInRequest
import com.CioffiDeVivo.dietideals.data.responseModels.LogInResponse
import retrofit2.HttpException
import retrofit2.Response

interface AuthRepository {
    suspend fun registerUser(user: User): User
    suspend fun loginUser(logInRequest: LogInRequest): LogInResponse
}

class NetworkAuthRepository(private val authApiService: AuthApiService): AuthRepository{
    override suspend fun registerUser(user: User): User {
        val response = authApiService.registerUser(user)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }

    override suspend fun loginUser(logInRequest: LogInRequest): LogInResponse {
        val response = authApiService.loginUser(logInRequest)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }
}