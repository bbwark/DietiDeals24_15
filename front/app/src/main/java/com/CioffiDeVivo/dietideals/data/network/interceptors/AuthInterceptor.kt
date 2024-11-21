package com.CioffiDeVivo.dietideals.data.network.interceptors

import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val userPreferencesRepository: UserPreferencesRepository): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if(request.url.encodedPath.startsWith("/auth/")){
            return chain.proceed(request)
        }
        val token = runBlocking { userPreferencesRepository.token.firstOrNull() }
        val authenticatedRequest = request.newBuilder()
            .apply {
                addHeader("Authorization", "Bearer ${token.toString()}")
            }
            .build()
        return chain.proceed(authenticatedRequest)
    }

}