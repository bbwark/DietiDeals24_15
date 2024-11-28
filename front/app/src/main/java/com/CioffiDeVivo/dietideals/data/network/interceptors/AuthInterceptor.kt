package com.CioffiDeVivo.dietideals.data.network.interceptors

import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val userPreferencesRepository: UserPreferencesRepository): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val isAuthEndpoint = request.url.encodedPath.startsWith("/auth/")
        if(isAuthEndpoint){
            return chain.proceed(request)
        }
        val token = runBlocking { userPreferencesRepository.getTokenPreference() }
        if(token.isEmpty()){
            throw IllegalStateException("Token is Missing!")
        }
        val authenticatedRequest = request.newBuilder()
            .addHeader(HttpHeaders.Authorization, "Bearer $token")
            .build()
        return chain.proceed(authenticatedRequest)
    }

}