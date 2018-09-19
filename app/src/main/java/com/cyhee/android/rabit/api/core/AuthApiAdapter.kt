package com.cyhee.android.rabit.api.core

import com.cyhee.android.rabit.api.resource.RabitUrl
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * AuthApiAdapter Class
 */
object AuthApiAdapter {
    private var okHttpClient: OkHttpClient
    private var retrofit: Retrofit

    // underscores 형식을 받아 camel case 로 변환
    private var gson: Gson =
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    init{
        val httpLogging = HttpLoggingInterceptor()
        httpLogging.level = HttpLoggingInterceptor.Level.BASIC

        okHttpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(httpLogging)
        }.build()

        // Retrofit2 + OKHttp3를 연결한다
        retrofit = Retrofit.Builder().apply{
            baseUrl(RabitUrl.authUrl())
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create(gson)) // Gson을 이용해 json파싱
        }.build()
    }

    /**
     * retrofit 서비스 생성
     */
    internal fun <T> retrofit(restClass: Class<T>): T {
        return retrofit.create(restClass)
    }
}