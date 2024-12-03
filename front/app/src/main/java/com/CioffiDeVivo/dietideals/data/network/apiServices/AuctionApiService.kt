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
import retrofit2.http.Query

interface AuctionApiService {

    @POST("/auctions")
    suspend fun createAuction(@Body auction: Auction): Response<Auction>

    @PUT("/auctions/{id}")
    suspend fun updateAuction(@Path("id") auctionId: String, @Body updatedAuction: Auction): Response<Auction>

    @GET("/auctions/{id}")
    suspend fun getAuction(@Path("id") auctionId: String): Response<Auction>

    @GET("/auctions/item/{name}/{pageNumber}")
    suspend fun getAuctionsByItemName(@Path("name") itemName: String, @Path("pageNumber") pageNumber: Int, @Query("categories") categories: List<String>): Response<Array<Auction>>

    @GET("/auctions/owner/{id}")
    suspend fun getRandomAuctions(@Path("id") ownerId: String): Response<Array<Auction>>

    @GET("/auctions/bidders/{id}")
    suspend fun getAuctionBidders(@Path("id") auctionId: String): Response<Array<User>>

    @GET("/auctions/ending/{id}")
    suspend fun getEndingAuctions(@Path("id") userId: String): Response<Array<Auction>>

    @GET("/auctions/participated/{id}")
    suspend fun getParticipatedAuctions(@Path("id") userId: String): Response<Array<Auction>>

    @DELETE("/auctions/{id}")
    suspend fun deleteAuction(@Path("id") auctionId: String): Response<Void>

}