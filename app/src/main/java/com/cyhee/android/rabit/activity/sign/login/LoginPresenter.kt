package com.cyhee.android.rabit.activity.sign.login

import android.util.Log
import com.cyhee.android.rabit.activity.base.DialogHandler
import com.cyhee.android.rabit.api.core.AuthApiAdapter
import com.cyhee.android.rabit.api.service.AuthApi
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class LoginPresenter(private val view : LoginActivity) : LoginContract.Presenter {

    private val TAG = LoginPresenter::class.qualifiedName
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
                            Log.d(TAG, "failed to login")
                            DialogHandler.errorDialog(it, view)
                        }
                )
    }

    override fun loginByFacebook(token: String) {
        restClient.tokenByFacebook("Bearer $token")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe (
                        {
                            view.success(it)
                        },
                        {
                            Log.d(TAG, "failed to login by facebook")
                            if(it is HttpException && it.code() == 438)
                                Log.d(TAG, "need to register")
                            DialogHandler.errorDialog(it, view)
                        }
                )
    }

    override fun loginByGoogle(token: String) {
        restClient.tokenByGoogle("Bearer $token")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe (
                        {
                            view.success(it)
                        },
                        {
                            Log.d(TAG, "failed to login by google")
                            if(it is HttpException && it.code() == 438)
                                Log.d(TAG, "need to register")
                            DialogHandler.errorDialog(it, view)
                        }
                )
    }
}