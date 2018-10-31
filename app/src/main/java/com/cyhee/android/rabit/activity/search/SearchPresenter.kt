package com.cyhee.android.rabit.activity.search

import android.util.Log
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class SearchPresenter(private val view: SearchActivity) : SearchContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    override fun searchUsers(keyword: String) {
        restClient.searchUsers(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("likes",it.toString())
                            view.showUserResult(it.content.toMutableList())
                        },
                        {
                            if(it is HttpException) {
                                Log.d("likes",it.response().toString())
                                Log.d("likes",it.response().body().toString())
                                Log.d("likes",it.response().body().toString())
                                Log.d("likes",it.response().errorBody().toString())
                                Log.d("likes",it.response().errorBody()?.string())
                            }
                            else {
                                Log.d("likes",it.toString())
                            }
                        }
                )
    }

    override fun searchGoals(keyword: String) {
        restClient.searchGoals(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("likes",it.toString())
                            view.showGoalResult(it.content.toMutableList())
                        },
                        {
                            if(it is HttpException) {
                                Log.d("likes",it.response().toString())
                                Log.d("likes",it.response().body().toString())
                                Log.d("likes",it.response().body().toString())
                                Log.d("likes",it.response().errorBody().toString())
                                Log.d("likes",it.response().errorBody()?.string())
                            }
                            else {
                                Log.d("likes",it.toString())
                            }
                        }
                )
    }

    override fun searchGoalLogs(keyword: String) {
        restClient.searchGoalLogs(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("likes",it.toString())
                            view.showGoalLogResult(it.content.toMutableList())
                        },
                        {
                            if(it is HttpException) {
                                Log.d("likes",it.response().toString())
                                Log.d("likes",it.response().body().toString())
                                Log.d("likes",it.response().body().toString())
                                Log.d("likes",it.response().errorBody().toString())
                                Log.d("likes",it.response().errorBody()?.string())
                            }
                            else {
                                Log.d("likes",it.toString())
                            }
                        }
                )
    }
}