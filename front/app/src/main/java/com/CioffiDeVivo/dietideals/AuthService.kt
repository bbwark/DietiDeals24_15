package com.CioffiDeVivo.dietideals

import android.content.Context
import com.CioffiDeVivo.dietideals.domain.RequestModels.User
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders

class AuthService(context: Context) {

    val URL = "${context.getString(R.string.base_url)}:${context.getString(R.string.port)}"

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
                url("${URL}/auth/registerUser")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(userToRegister)
            }
            result = response
        }
        return result
    }

    //post mapping login /auth/loginUser   (out JWT in email & password)
    suspend fun loginUser(email: String, password: String): String {
        var result: String
        val login = object {
            val email = email
            val password = password
        }
        HttpClient(CIO).use {
            val gson = Gson()
            val body = gson.toJson(login)
            val response = it.post {
                url("${URL}/auth/loginUser")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(body)
            }
            val jsonObject = gson.fromJson(response.bodyAsText(), JsonObject::class.java)
            result = jsonObject.get("jwt").asString
        }
        return result;
    }
}