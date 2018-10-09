package com.cyhee.android.rabit.activity.sign.login

import android.util.Log
import com.cyhee.android.rabit.api.core.AuthApiAdapter
import com.cyhee.android.rabit.api.service.AuthApi
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class LoginPresenter(private val view : LoginActivity) : LoginContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: AuthApi = AuthApiAdapter.retrofit(AuthApi::class.java)

    override fun login(username: String, password: String) {
        restClient.token(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
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