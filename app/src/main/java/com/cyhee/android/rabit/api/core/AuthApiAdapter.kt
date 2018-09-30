package com.cyhee.android.rabit.api.core

import com.cyhee.android.rabit.api.resource.RabitUrl
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * AuthApiAdapter Class
 */
object AuthApiAdapter : BaseApiAdapter(RabitUrl.authUrl())