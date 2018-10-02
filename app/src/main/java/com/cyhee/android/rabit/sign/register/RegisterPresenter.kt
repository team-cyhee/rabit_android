package com.cyhee.android.rabit.sign.login

import android.util.Log
import com.cyhee.android.rabit.api.core.AuthApiAdapter
import com.cyhee.android.rabit.api.response.TokenData
import com.cyhee.android.rabit.api.service.AuthApi
import com.cyhee.android.rabit.data.User
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class RegisterPresenter(private val view : RegisterActivity) : RegisterContract.Presenter {
    private val restClient: AuthApi = AuthApiAdapter.retrofit(AuthApi::class.java)

    override fun register(user : User) {
        restClient.exists(user.username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        {
                            view.duplicatedUsername()
                        },
                        {
                            restClient.register(user)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            {
                                                view.success()
                                            },
                                            {
                                                if(it is HttpException) {
                                                    Log.d("register",it.response().toString())
                                                    Log.d("register",it.response().body().toString())
                                                    Log.d("register",it.response().body().toString())
                                                    Log.d("register",it.response().errorBody().toString())
                                                    Log.d("register",it.response().errorBody()?.string())
                                                }
                                                else {
                                                    Log.d("register",it.toString())
                                                }
                                            }
                                    )
                        }
                )
    }
}