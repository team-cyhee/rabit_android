package com.cyhee.android.rabit.api.core.deserializer

import com.cyhee.android.rabit.model.ContentType
import com.cyhee.android.rabit.model.GoalInfo
import com.cyhee.android.rabit.model.GoalLogInfo
import com.cyhee.android.rabit.model.MainInfo
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

object MainInfoDeserializer : JsonDeserializer<MainInfo> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): MainInfo {
        val jsonObject = json?.asJsonObject
        val typeStr = jsonObject!!.get("type").asString
        val contentType = ContentType.valueOf(typeStr)

        when (contentType) {
            ContentType.GOAL -> return context!!.deserialize(json, GoalInfo::class.java)
            ContentType.GOALLOG -> return context!!.deserialize(json, GoalLogInfo::class.java)
        }

        throw JsonParseException("Type must be goal or goal log")
    }
}