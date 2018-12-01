package com.cyhee.android.rabit.api.service

import com.cyhee.android.rabit.model.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody


interface ResourceApi {
    @GET("/rest/v1/users")
    fun users() : Single<String>

    @GET("/rest/v1/users/username/{username}")
    fun user(@Path("username") username: String) : Single<User>

    @PUT("/rest/v1/users/username/{username}")
    fun putUser(@Path("username") username: String, @Body user: UserFactory.Edit) : Completable

    @GET("/rest/v1/goals")
    fun goals() : Single<Page<Goal>>

    @GET("/rest/v1/goals/by-user")
    fun goalsByUser() : Single<List<Goal>>

    @GET("/rest/v1/goals/{id}")
    fun goal(@Path("id") id: Long) : Single<Goal>

    @PUT("/rest/v1/goals/{id}")
    fun putGoal(@Path("id") id: Long, @Body goal: GoalFactory.Post) : Completable

    @DELETE("/rest/v1/goals/{id}")
    fun deleteGoal(@Path("id") id: Long) : Completable

    @GET("/rest/v1/goals/{id}/goallogs/info")
    fun goalStoreGoalLogs(@Path("id") id: Long) : Single<List<GoalLogInfo>>

    @GET("/rest/v1/goals/{id}/comments")
    fun goalStoreComments(@Path("id") id: Long) : Single<Page<Comment>>

    @GET("/rest/v1/goals/{id}/likes")
    fun goalStoreLikes(@Path("id") id: Long) : Single<Page<User>>

    @GET("/rest/v1/goals/{id}/companions")
    fun goalCompanions(@Path("id") id: Long) : Single<Page<User>>

    @GET("/rest/v1/goals/info/user/{username}")
    fun userGoalInfos(@Path("username") username: String) : Single<List<GoalInfo>>

    @GET("/rest/v1/goals/info/user")
    fun userGoalInfos() : Single<List<GoalInfo>>

    @GET("/rest/v1/goals/info/{id}")
    fun goalInfo(@Path("id") id: Long) : Single<GoalInfo>

    @GET("/rest/v1/goallogs")
    fun goalLogs() : Single<Page<GoalLog>>

    @GET("/rest/v1/goallogs/{id}")
    fun goalLog(@Path("id") id: Long) : Single<GoalLog>

    @PUT("rest/v1/goallogs/{id}")
    fun putGoalLog(@Path("id") id: Long, @Body goalLog: GoalLogFactory.Post) : Completable

    @DELETE("rest/v1/goallogs/{id}")
    fun deleteGoalLog(@Path("id") id: Long) : Completable

    @GET("/rest/v1/goallogs/{id}/comments")
    fun goalLogStoreComments(@Path("id") id: Long) : Single<Page<Comment>>

    @GET("/rest/v1/goallogs/{id}/likes")
    fun goalLogStoreLikes(@Path("id") id: Long) : Single<Page<User>>

    @GET("/rest/v1/goallogs/info/{id}")
    fun goalLogInfo(@Path("id") id: Long) : Single<GoalLogInfo>

    @GET("/rest/v1/goallogs/info/com/{id}")
    fun comGoalLogInfos(@Path("id") id: Long) : Single<List<GoalLogInfo>>

    @GET("/rest/v1/maininfos")
    fun mainInfos() : Single<List<MainInfo>?>

    @GET("/rest/v1/maininfos/{username}")
    fun userMainInfos(@Path("username") username: String) : Single<List<MainInfo>?>

    @GET("/rest/v1/wallinfo/{username}")
    fun wallInfo(@Path("username") username: String) : Single<WallInfo>

    @GET("/rest/v1/users/{username}/followees")
    fun followees(@Path("username") username: String) : Single<Page<User>>

    @GET("/rest/v1/users/{username}/followers")
    fun followers(@Path("username") username: String) : Single<Page<User>>

    @GET("/rest/v1/users/search")
    fun searchUsers(@Query("keyword") keyword: String) : Single<Page<User>>

    @GET("/rest/v1/goals/search")
    fun searchGoals(@Query("keyword") keyword: String) : Single<Page<Goal>>

    @GET("/rest/v1/goallogs/search")
    fun searchGoalLogs(@Query("keyword") keyword: String) : Single<Page<GoalLog>>

    @POST("/rest/v1/goals")
    fun postGoal(@Body goal: GoalFactory.Post): Single<Page<Goal>>

    @POST("/rest/v1/goals/{id}/goallogs")
    fun postGoalLog(@Path("id") id: Long, @Body goalLog: GoalLogFactory.Post) : Completable

    @POST("/rest/v1/goals/{id}/comments")
    fun postCommentForGoal(@Path("id") id: Long, @Body comment: CommentFactory.Post) : Single<Response<Void>>

    @POST("rest/v1/goallogs/{id}/comments")
    fun postCommentForGoalLog(@Path("id") id: Long, @Body comment: CommentFactory.Post) : Single<Response<Void>>

    @POST("rest/v1/users/{username}/followers")
    fun postFollow(@Path("username") username: String) : Completable

    @POST("/rest/v1/goals/{id}/likes")
    fun postLikeForGoal(@Path("id") id: Long) : Completable

    @DELETE("/rest/v1/goals/{id}/likes")
    fun deleteLikeForGoal(@Path("id") id: Long) : Completable

    @POST("rest/v1/goallogs/{id}/likes")
    fun postLikeForGoalLog(@Path("id") id: Long) : Completable

    @DELETE("rest/v1/goallogs/{id}/likes")
    fun deleteLikeForGoalLog(@Path("id") id: Long) : Completable

    @POST("rest/v1/goals/{id}/companion-goals")
    fun postCompanion(@Path("id") id : Long, @Body companionGoal: GoalFactory.Post) : Completable

    @GET("/rest/v1/comments/{id}")
    fun comment(@Path("id") id: Long) : Single<Comment>

    @POST("/rest/v1/questions")
    fun postQuestion(@Body question: QuestionFactory.Post) : Completable

    @Multipart
    @POST("/rest/v1/files")
    fun fileUpload(@Part("description") description: RequestBody, @Part file: MultipartBody.Part): Single<Response<Void>>

    @Streaming
    @GET("/rest/v1/files/{id}")
    fun fileDownload(@Path("id") id: Long): Observable<Response<ResponseBody>>

    @GET("/rest/v1/notices/titles")
    fun noticeTitles() : Single<Page<Notice>>

    @GET("/rest/v1/notices/{id}")
    fun notice(@Path("id") id: Long) : Single<Notice>

    @GET("/rest/v1/alarms/user")
    fun alarms() : Single<Page<Alarm>>

    @GET("/rest/v1/reports/{id}")
    fun report(@Path("id") id: Long) : Single<Report>

    @POST("/rest/v1/reports")
    fun postReport(@Body report: ReportFactory.Post) : Completable
}