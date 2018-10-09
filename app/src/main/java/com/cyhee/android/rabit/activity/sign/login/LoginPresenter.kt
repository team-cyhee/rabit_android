package com.cyhee.android.rabit.activity.sign.login

import android.util.Log
import com.cyhee.android.rabit.api.core.AuthApiAdapter
import com.cyhee.android.rabit.api.service.AuthApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class LoginPresenter(private val view : LoginContract.View) : LoginContract.Presenter {
    private val restClient: AuthApi = AuthApiAdapter.retrofit(AuthApi::class.java)

    override fun login(username: String, password: String) {
        restClient.token(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        {
                            view.success(it)
                        },
                        {
                            if(it is HttpException) {
                                Log.d("loginRequest", it.response().toString())
                                Log.d("loginRequest", it.response().body().toString())
                            }
                            else {
                                Log.d("loginRequest", it.toString())
                            }
                            view.fail(it)
                        }
                )
    }
}