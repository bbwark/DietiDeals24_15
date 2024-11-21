package com.CioffiDeVivo.dietideals.data.network.apiServices

import com.CioffiDeVivo.dietideals.data.models.User
import com.CioffiDeVivo.dietideals.data.requestModels.LogInRequest
import com.CioffiDeVivo.dietideals.data.responseModels.LogInResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiService{

    @GET("/users/{id}")
    suspend fun getUserById(@Path("id") userId: String): User

    @POST("/auth/loginUser")
    suspend fun login(@Body credentials: LogInRequest): LogInResponse
}