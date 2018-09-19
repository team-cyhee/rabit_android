package com.cyhee.android.rabit.api.service

import retrofit2.Call
import retrofit2.http.POST

interface ResourceApi {
    @POST("/rest/v1/users")
    fun users() : Call<String>
}