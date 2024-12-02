package com.CioffiDeVivo.dietideals.data.repositories

import com.CioffiDeVivo.dietideals.data.models.Auction
import com.CioffiDeVivo.dietideals.data.models.User
import com.CioffiDeVivo.dietideals.data.network.apiServices.AuctionApiService
import retrofit2.HttpException

interface AuctionRepository {
    suspend fun createAuction(auction: Auction): Auction
    suspend fun updateAuction(auctionId: String, updatedAuction: Auction): Auction
    suspend fun getAuction(auctionId: String): Auction
    suspend fun getAuctionsByItemName(itemName: String, pageNumber: Int, categories: Set<String>): Array<Auction>
    suspend fun getRandomAuctions(ownerId: String): Array<Auction>
    suspend fun getAuctionBidders(auctionId: String): Array<User>
    suspend fun getEndingAuctions(userId: String): Array<Auction>
    suspend fun getParticipatedAuctions(userId: String): Array<Auction>
    suspend fun deleteAuction(auctionId: String)
}

class NetworkAuctionRepository(private val auctionApiService: AuctionApiService): AuctionRepository{
    override suspend fun createAuction(auction: Auction): Auction {
        val response = auctionApiService.createAuction(auction)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }

    override suspend fun updateAuction(auctionId: String, updatedAuction: Auction): Auction {
        val response = auctionApiService.updateAuction(auctionId, updatedAuction)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }

    override suspend fun getAuction(auctionId: String): Auction {
        val response = auctionApiService.getAuction(auctionId)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }

    override suspend fun getAuctionsByItemName(itemName: String, pageNumber: Int, categories: Set<String>): Array<Auction> {
        val response = auctionApiService.getAuctionsByItemName(itemName, pageNumber, categories)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }

    override suspend fun getRandomAuctions(ownerId: String): Array<Auction> {
        val response = auctionApiService.getRandomAuctions(ownerId)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }

    override suspend fun getAuctionBidders(auctionId: String): Array<User> {
        val response = auctionApiService.getAuctionBidders(auctionId)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }

    override suspend fun getEndingAuctions(userId: String): Array<Auction> {
        val response = auctionApiService.getEndingAuctions(userId)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }

    override suspend fun getParticipatedAuctions(userId: String): Array<Auction> {
        val response = auctionApiService.getParticipatedAuctions(userId)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }

    override suspend fun deleteAuction(auctionId: String) {
        val response = auctionApiService.deleteAuction(auctionId)
        if(!response.isSuccessful){
            throw HttpException(response)
        }
    }
}