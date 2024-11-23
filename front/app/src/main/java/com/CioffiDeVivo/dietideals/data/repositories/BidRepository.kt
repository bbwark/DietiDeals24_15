package com.CioffiDeVivo.dietideals.data.repositories

import com.CioffiDeVivo.dietideals.data.models.Bid
import com.CioffiDeVivo.dietideals.data.network.apiServices.BidApiService
import com.CioffiDeVivo.dietideals.data.requestModels.BidRequest
import retrofit2.HttpException
import retrofit2.Response

interface BidRepository {
    suspend fun createBid(bid: BidRequest): Bid
    suspend fun deleteBid(bidId: String)
    suspend fun getBidsByAuctionId(auctionId: String): Array<Bid>
    suspend fun chooseWinningBid(bid: Bid): Bid
}

class NetworkBidRepository(private val bidApiService: BidApiService): BidRepository{
    override suspend fun createBid(bid: BidRequest): Bid {
        val response = bidApiService.createBid(bid)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }

    override suspend fun deleteBid(bidId: String) {
        val response = bidApiService.deleteBid(bidId)
        if(!response.isSuccessful){
            throw HttpException(response)
        }
    }

    override suspend fun getBidsByAuctionId(auctionId: String): Array<Bid> {
        val response = bidApiService.getBidsByAuctionId(auctionId)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }

    override suspend fun chooseWinningBid(bid: Bid): Bid {
        val response = bidApiService.chooseWinningBid(bid)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }
}