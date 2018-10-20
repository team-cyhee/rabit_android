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
import java.util.*

object DateDeserializer : JsonDeserializer<Date> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date {
        return Date(json?.asJsonPrimitive!!.asLong)
    }
}