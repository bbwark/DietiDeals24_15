package com.CioffiDeVivo.dietideals.data.network.apiServices

import com.CioffiDeVivo.dietideals.data.models.CreditCard
import com.CioffiDeVivo.dietideals.data.requestModels.CreditCardRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CreditCardApiService {

    @POST("/credit_cards")
    suspend fun createCreditCard(@Body creditCard: CreditCardRequest): Response<CreditCard>

    @DELETE("/credit_cards/{creditCardNumber}")
    suspend fun deleteCreditCard(@Path("creditCardNumber") cardNumber: String): Response<Void>

    @GET("/credit_cards/user/{id}")
    suspend fun getCreditCardsByUserId(@Path("id") userId: String): Response<Array<CreditCard>>

}