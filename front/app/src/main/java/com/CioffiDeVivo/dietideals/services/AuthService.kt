package com.CioffiDeVivo.dietideals.services

import android.content.Context
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.domain.requestModels.User
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import java.lang.IllegalStateException
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

object AuthService{
    private var _URL: String? = null

    private class InitCheck<T>(private val initializer: () -> T) : ReadOnlyProperty<Any?, T> {
        private var value: T? = null
        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return value ?: initializer().also { value = it }
        }
    }

    private val URL by InitCheck { _URL ?: throw IllegalStateException("ApiService not initialized correctly") }

    fun initialize(context: Context) {
        _URL = "${context.getString(R.string.base_url)}:${context.getString(R.string.port)}"
    }

/*
* ================== AUTH APIs ==================
*
* */

    //post mapping register /auth/registerUser   (in user)
    suspend fun registerUser(user: User): HttpResponse {
        var result: HttpResponse
        HttpClient(CIO).use {
            val gson = Gson()
            val userToRegister = gson.toJson(user)
            val response = it.post {
                url("$URL/auth/registerUser")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(userToRegister)
            }
            result = response
        }
        return result
    }

    //post mapping login /auth/loginUser   (out JWT in email & password)
    suspend fun loginUser(email: String, password: String): HttpResponse {
        var result: HttpResponse
        val login = object {
            val email = email
            val password = password
        }
        HttpClient(CIO).use {
            val gson = Gson()
            val body = gson.toJson(login)
            val response = it.post {
                url("$URL/auth/loginUser")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(body)
            }
            result = response
        }
        return result;
    }
}