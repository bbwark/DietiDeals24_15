package com.CioffiDeVivo.dietideals.data.repositories

import com.CioffiDeVivo.dietideals.data.models.User
import com.CioffiDeVivo.dietideals.data.network.apiServices.UserApiService
import retrofit2.HttpException

interface UserRepository {
    suspend fun getUser(userId: String): User
    suspend fun getUserInfo(userId: String): User
    suspend fun updateUser(userId: String, user: User): User
    suspend fun deleteUser(userId: String)
    suspend fun addFavourite(auctionId: String, userId: String)
    suspend fun removeFavourite(auctionId: String, userId: String)
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

    override suspend fun addFavourite(auctionId: String, userId: String) {
        val response = userApiService.addFavourite(auctionId, userId)
        if(!response.isSuccessful){
            throw HttpException(response)
        }
    }

    override suspend fun removeFavourite(auctionId: String, userId: String) {
        val response = userApiService.removeFavourite(auctionId, userId)
        if(!response.isSuccessful){
            throw HttpException(response)
        }
    }

}