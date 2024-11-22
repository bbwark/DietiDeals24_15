package com.CioffiDeVivo.dietideals.data.network.apiServices

import com.CioffiDeVivo.dietideals.data.models.User
import com.CioffiDeVivo.dietideals.data.requestModels.LogInRequest
import com.CioffiDeVivo.dietideals.data.responseModels.LogInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("/auth/registerUser")
    suspend fun registerUser(@Body user: User): Response<User>

    @POST("/auth/loginUser")
    suspend fun loginUser(@Body logInRequest: LogInRequest): Response<LogInResponse>
}