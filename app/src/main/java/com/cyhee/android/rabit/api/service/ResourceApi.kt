package com.cyhee.android.rabit.api.service

import com.cyhee.android.rabit.model.*
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @GET("/rest/v1/maininfos")
    fun mainInfos() : Single<List<MainInfo>?>

    @POST("/rest/v1/goals/{id}/goallogs")
    fun postGoalLog(@Path("id") id : Long, @Body goalLog: GoalLogFactory.Post) : Completable

    @POST("/rest/v1/goals/{id}/comments")
    fun postCommentForGoal(@Path("id") id : Long, @Body comment: CommentFactory.Post) : Completable

    @POST("rest/v1/goallogs/{id}/comments")
    fun postCommentForGoalLog(@Path("id") id : Long, @Body comment: CommentFactory.Post) : Completable
}