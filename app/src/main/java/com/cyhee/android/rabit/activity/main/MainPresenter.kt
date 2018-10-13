package com.cyhee.android.rabit.activity.main

import android.util.Log
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class MainPresenter(private val view: MainActivity) : MainContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    override fun goalLogs() {
        restClient.goalLogs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("goalLog",it.toString())
                            view.showGoalLogs(it.content)
                        },
                        {
                            if(it is HttpException) {
                                Log.d("goalLog",it.response().toString())
                                Log.d("goalLog",it.response().body().toString())
                                Log.d("goalLog",it.response().body().toString())
                                Log.d("goalLog",it.response().errorBody().toString())
                                Log.d("goalLog",it.response().errorBody()?.string())
                            }
                            else {
                                Log.d("goalLog",it.toString())
                            }
                        }
                )
    }
}