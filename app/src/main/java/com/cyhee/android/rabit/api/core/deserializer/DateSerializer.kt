package com.cyhee.android.rabit.api.core.deserializer

import com.google.gson.*
import java.lang.reflect.Type
import java.util.*

object DateSerializer : JsonSerializer<Date> {

    override fun serialize(src: Date?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src?.time)
    }
}