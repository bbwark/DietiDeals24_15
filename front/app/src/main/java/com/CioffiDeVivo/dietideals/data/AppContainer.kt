package com.CioffiDeVivo.dietideals.data

import com.CioffiDeVivo.dietideals.data.network.interceptors.AuthInterceptor
import com.CioffiDeVivo.dietideals.data.network.apiServices.UserApiService
import com.CioffiDeVivo.dietideals.data.repositories.NetworkUserRepository
import com.CioffiDeVivo.dietideals.data.repositories.UserRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val userRepository: UserRepository
}

class DefaultAppContainer(userPreferencesRepository: UserPreferencesRepository): AppContainer{

    private val baseUrl = "http://16.171.206.112:8181"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(userPreferencesRepository))
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    private val retrofitService: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }

    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(retrofitService)
    }
}