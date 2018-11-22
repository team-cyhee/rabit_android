package com.cyhee.android.rabit.api.service

import com.cyhee.android.rabit.api.response.TokenData
import com.cyhee.android.rabit.api.response.TokenVerified
import com.cyhee.android.rabit.model.User
import com.cyhee.android.rabit.model.UserFactory
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

/**
 * 로그인 인증 관련 요청을 담당하는 interface
 */
interface AuthApi {

    @GET("/oauth/check_token")
    fun checkToken (
        @Query("token") token : String
    ) : Single<TokenVerified>

    /**
     * 아이디 패스워드를 이용하여 token 획득 요청
     * @Headers - client id: rabit, client secret: rabit@password
     */
    @Headers("Authorization: Basic cmFiaXRfcmVzdDpyYWJpdEBwYXNzd29yZA==")
    @POST("/oauth/token?grant_type=password")
    fun token (
        @Query("username") username : String,
        @Query("password") password : String
    ) : Single<TokenData>

    @GET("/oauth/authorize?response_type=code&client_id=rabit_rest&redirect_uri=/oauth/token/rabit")
    fun token (
        @Header("Authorization") basic: String
    ) : Single<String>

    @GET("/oauth/token?grant_type=authorization_code&redirect_uri=/oauth/token/rabit")
    fun tokenByCode (
        @Header("Authorization") basic: String,
        @Query("code") code: String
    ) : Single<TokenData>

    @GET("/oauth/token/facebook")
    fun tokenByFacebook (
        @Header("Authorization") token: String
    ) : Single<TokenData>

    @GET("/oauth/token/google")
    fun tokenByGoogle (
        @Header("Authorization") token: String
    ) : Single<TokenData>

    @GET("/oauth/token/{social}")
    fun tokenBySocial (
        @Path("social") social: String,
        @Header("Authorization") token: String
    ) : Single<TokenData>

    @POST("/v1/users")
    fun register(
        @Body user: UserFactory.Post
    ) : Completable

    @POST("/oauth/token/{social}")
    fun socialRegister(
        @Path("social") social: String,
        @Header("Authorization") token: String,
        @Body user: UserFactory.Post
    ) : Completable

    @GET("/v1/users/{username}")
    fun exists(
        @Path("username") username: String
    ) : Completable

    @PUT("/v1/users/{username}/password")
    fun findPassword(
            @Path("username") username: String
    ) : Completable
}