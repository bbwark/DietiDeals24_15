package com.CioffiDeVivo.dietideals.data.repositories

import com.CioffiDeVivo.dietideals.data.models.Item
import com.CioffiDeVivo.dietideals.data.network.apiServices.ItemApiService
import retrofit2.HttpException
import retrofit2.Response

interface ItemRepository {
    suspend fun createItem(item: Item): Item
    suspend fun deleteItem(itemId: String)
}

class NetworkItemRepository(private val itemApiService: ItemApiService): ItemRepository{
    override suspend fun createItem(item: Item): Item {
        val response = itemApiService.createItem(item)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }

    override suspend fun deleteItem(itemId: String) {
        val response = itemApiService.deleteItem(itemId)
        if(!response.isSuccessful){
            throw HttpException(response)
        }
    }
}
