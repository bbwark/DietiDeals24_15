package com.CioffiDeVivo.dietideals.data

import com.CioffiDeVivo.dietideals.network.DietiDealsApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val userRepository: UserRepository
}

class DefaultAppContainer: AppContainer{

    private val baseUrl = "http://16.171.206.112:8181"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitService: DietiDealsApiService by lazy {
        retrofit.create(DietiDealsApiService::class.java)
    }

    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(retrofitService)
    }
}