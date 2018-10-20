package com.cyhee.android.rabit.api.core

import com.cyhee.android.rabit.api.core.deserializer.DateDeserializer
import com.cyhee.android.rabit.api.core.deserializer.DateSerializer
import com.cyhee.android.rabit.api.core.deserializer.MainInfoDeserializer
import com.cyhee.android.rabit.model.MainInfo
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.*

object RabitGson {
    fun gson(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date::class.java, DateDeserializer)
                .registerTypeAdapter(Date::class.java, DateSerializer)
                .registerTypeAdapter(MainInfo::class.java, MainInfoDeserializer)
                .create()
    }
}