package com.CioffiDeVivo.dietideals.data.network.apiServices

import com.CioffiDeVivo.dietideals.data.models.Bid
import com.CioffiDeVivo.dietideals.data.requestModels.BidRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BidApiService {

    @POST("/bids")
    suspend fun createBid(@Body bid: BidRequest): Response<Bid>

    @DELETE("/bids/{id}")
    suspend fun deleteBid(@Path("id") bidId: String): Response<Void>

    @GET("/bids/auction/{id}")
    suspend fun getBidsByAuctionId(@Path("id") auctionId: String): Response<Array<Bid>>

    @POST("/bids/chooseWinningBid")
    suspend fun chooseWinningBid(@Body bid: Bid): Response<Bid>

}