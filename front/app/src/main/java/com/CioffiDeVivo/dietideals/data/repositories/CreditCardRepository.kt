package com.CioffiDeVivo.dietideals.data.repositories

import com.CioffiDeVivo.dietideals.data.models.CreditCard
import com.CioffiDeVivo.dietideals.data.network.apiServices.CreditCardApiService
import retrofit2.HttpException

interface CreditCardRepository {
    suspend fun createCreditCard(creditCard: CreditCard): CreditCard
    suspend fun deleteCreditCard(cardNumber: String)
    suspend fun getCreditCardsByUserId(userId: String): Array<CreditCard>
}

class NetworkCreditCardRepository(private val creditCardApiService: CreditCardApiService): CreditCardRepository{
    override suspend fun createCreditCard(creditCard: CreditCard): CreditCard {
        val response = creditCardApiService.createCreditCard(creditCard)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }

    override suspend fun deleteCreditCard(cardNumber: String) {
        val response = creditCardApiService.deleteCreditCard(cardNumber)
        if(!response.isSuccessful){
            throw HttpException(response)
        }
    }

    override suspend fun getCreditCardsByUserId(userId: String): Array<CreditCard> {
        val response = creditCardApiService.getCreditCardsByUserId(userId)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }
}