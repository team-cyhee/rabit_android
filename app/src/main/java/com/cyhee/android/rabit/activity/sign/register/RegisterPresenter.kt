package com.cyhee.android.rabit.activity.sign.login

import android.util.Log
import com.cyhee.android.rabit.api.core.AuthApiAdapter
import com.cyhee.android.rabit.api.service.AuthApi
import com.cyhee.android.rabit.model.UserFactory
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class RegisterPresenter(private val view : RegisterActivity) : RegisterContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: AuthApi = AuthApiAdapter.retrofit(AuthApi::class.java)

    override fun register(user : UserFactory.Post) {
        restClient.exists(user.username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
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