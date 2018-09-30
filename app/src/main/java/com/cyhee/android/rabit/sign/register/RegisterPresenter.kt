package com.cyhee.android.rabit.sign.login

import com.cyhee.android.rabit.api.core.AuthApiAdapter
import com.cyhee.android.rabit.api.response.TokenData
import com.cyhee.android.rabit.api.service.AuthApi
import com.cyhee.android.rabit.data.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class RegisterPresenter(private val view : RegisterActivity) : RegisterContract.Presenter {
    private val restClient: AuthApi = AuthApiAdapter.retrofit(AuthApi::class.java)

    override fun register(user : User) {
        restClient.register(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        {
                            view.success()
                        },
                        {
                            if(it is HttpException) {
                                print(it.response())
                                print(it.response().body())
                            }
                            else {
                                print(it)
                            }
                        }
                )
    }
}