package com.CioffiDeVivo.dietideals.utils

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ZonedDateTimeAdapter: JsonDeserializer<ZonedDateTime>, JsonSerializer<ZonedDateTime> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ZonedDateTime {
        val dateString = json?.asString
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return ZonedDateTime.parse(dateString, formatter.withZone(ZoneId.systemDefault()))
    }

    override fun serialize(src: ZonedDateTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return JsonPrimitive(src?.format(formatter))
    }
}