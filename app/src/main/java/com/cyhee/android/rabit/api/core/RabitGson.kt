package com.cyhee.android.rabit.api.core

import com.cyhee.android.rabit.api.core.deserializer.MainInfoDeserializer
import com.cyhee.android.rabit.model.MainInfo
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

object RabitGson {
    fun gson(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                .registerTypeAdapter(MainInfo::class.java, MainInfoDeserializer)
                .create()
    }
}