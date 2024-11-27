package com.CioffiDeVivo.dietideals.data.network.apiServices

import com.CioffiDeVivo.dietideals.data.models.Auction
import com.CioffiDeVivo.dietideals.data.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApiService{

    @GET("/users/{id}")
    suspend fun getUser(@Path("id") userId: String): Response<User>

    @GET("/users/{id}")
    suspend fun getUserInfo(@Path("id") userId: String): Response<User>

    @PUT("/users/{id}")
    suspend fun updateUser(@Path("id") userId: String, @Body updatedUser: User): Response<User>

    @DELETE("/users/{id}")
    suspend fun deleteUser(@Path("id") userId: String): Response<Void>

    @PUT("/users/add/favourite/{auctionId}/{id}")
    suspend fun addFavourite(@Path("auctionId") auctionId: String, @Path("id") userId: String): Response<User>

    @PUT("/users/remove/favourite/{auctionId}/{userId}")
    suspend fun removeFavourite(@Path("auctionId") auctionId: String, @Path("id") userId: String): Response<User>

}