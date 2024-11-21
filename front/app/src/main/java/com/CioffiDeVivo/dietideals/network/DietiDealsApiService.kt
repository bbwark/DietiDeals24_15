package com.CioffiDeVivo.dietideals.network

import com.CioffiDeVivo.dietideals.domain.models.User
import com.CioffiDeVivo.dietideals.domain.requestModels.LogInRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DietiDealsApiService{

    @GET("/users/{id}")
    suspend fun getUserById(@Path("id") userId: String): User

    @POST("/auth/loginUser")
    suspend fun login(@Body credentials: LogInRequest): User
}