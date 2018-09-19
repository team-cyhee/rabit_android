package com.cyhee.android.rabit.api.core.interceptors

import com.cyhee.android.rabit.activity.App
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class TokenInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val token = App.prefs.token

        // when token is empty do nothing
        if(token.isNullOrBlank())
            return chain.proceed(original)

        // add token header
        val requestBuilder = original.newBuilder().header("Authorization", "Bearer $token")

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}