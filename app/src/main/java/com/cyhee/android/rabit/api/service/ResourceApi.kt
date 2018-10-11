package com.cyhee.android.rabit.api.service

import com.cyhee.android.rabit.model.Comment
import com.cyhee.android.rabit.model.Goal
import com.cyhee.android.rabit.model.GoalLog
import com.cyhee.android.rabit.model.Page
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ResourceApi {
    @GET("/rest/v1/users")
    fun users() : Single<String>

    @GET("/rest/v1/goals")
    fun goals() : Single<Page<Goal>>

    @GET("/rest/v1/goallogs")
    fun goalLogs() : Single<Page<GoalLog>>

    @GET("/rest/v1/goallogs/{id}")
    fun goalLog(@Path("id") id : Long) : Single<GoalLog>

    @GET("/rest/v1/goallogs/{id}/comments")
    fun goalLogStoreComments(@Path("id") id : Long) : Single<Page<Comment>>
}