package com.CioffiDeVivo.dietideals.data.repositories

import android.util.Log
import com.CioffiDeVivo.dietideals.data.models.Auction
import com.CioffiDeVivo.dietideals.data.models.User
import com.CioffiDeVivo.dietideals.data.requestModels.LogInRequest
import com.CioffiDeVivo.dietideals.data.responseModels.LogInResponse
import com.CioffiDeVivo.dietideals.data.network.apiServices.UserApiService
import io.ktor.client.statement.HttpResponse
import retrofit2.HttpException
import retrofit2.Response

interface UserRepository {
    suspend fun getUser(userId: String): User
    suspend fun getUserInfo(userId: String): User
    suspend fun updateUser(userId: String, user: User): User
    suspend fun deleteUser(userId: String)
    suspend fun addFavourite(userId: String, auction: Auction): User
    suspend fun removeFavourite(userId: String, auction: Auction): User
}

class NetworkUserRepository(private val userApiService: UserApiService): UserRepository {
    override suspend fun getUser(userId: String): User {
        val response = userApiService.getUser(userId)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }

    override suspend fun getUserInfo(userId: String): User {
        val response = userApiService.getUserInfo(userId)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }

    override suspend fun updateUser(userId: String, user: User): User {
        val response = userApiService.updateUser(userId, user)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }

    override suspend fun deleteUser(userId: String) {
        val response = userApiService.deleteUser(userId)
        if(!response.isSuccessful){
            throw HttpException(response)
        }
    }

    override suspend fun addFavourite(userId: String, auction: Auction): User {
        val response = userApiService.addFavourite(userId, auction)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }

    override suspend fun removeFavourite(userId: String, auction: Auction): User {
        val response = userApiService.removeFavourite(userId, auction)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }

}