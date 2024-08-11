package com.CioffiDeVivo.dietideals.utils

import com.google.gson.Gson
import com.google.gson.JsonObject

fun extractKeyFromJson(json: String, key: String): String {
    val jsonObject = Gson().fromJson(json, JsonObject::class.java)
    return jsonObject.get(key).asString
}