package com.cyhee.android.rabit.api.service

import com.cyhee.android.rabit.api.response.TokenData
import com.cyhee.android.rabit.data.User
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * 로그인 인증 관련 요청을 담당하는 interface
 */
interface AuthApi {
    /**
     * 아이디 패스워드를 이용하여 token 획득 요청
     * @Headers - client id: rabit, client secret: rabit@password
     */
    @Headers("Authorization: Basic cmFiaXRfcmVzdDpyYWJpdEBwYXNzd29yZA==")
    @POST("/oauth/token?grant_type=password")
    fun token(
        @Query("username") username : String,
        @Query("password") password : String
    ) : Single<TokenData>


    @POST("/v1/users")
    fun register(
        @Body user: User
    ) : Completable
}