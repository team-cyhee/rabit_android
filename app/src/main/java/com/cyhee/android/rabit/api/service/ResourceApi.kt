package com.cyhee.android.rabit.api.service

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface ResourceApi {
    @GET("/rest/v1/users")
    fun users() : Single<String>

}