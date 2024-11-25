package com.CioffiDeVivo.dietideals.data.network.apiServices

import com.CioffiDeVivo.dietideals.data.models.Item
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface ItemApiService {

    @POST("/items")
    suspend fun createItem(@Body item: Item): Response<Item>

    @DELETE("/items/{id}")
    suspend fun deleteItem(@Path("id") itemId: String): Response<Void>

}