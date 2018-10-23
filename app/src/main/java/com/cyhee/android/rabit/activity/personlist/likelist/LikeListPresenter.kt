package com.cyhee.android.rabit.activity.personlist.likelist

import android.util.Log
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class LikeListPresenter(private val view: LikeListActivity) : LikeListContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    override fun likesForGoal(id: Long) {
        restClient.goalStoreLikes(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("likes",it.toString())
                            view.showLikes(it.content.toMutableList())
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

    override fun likesForGoalLog(id: Long) {
        restClient.goalLogStoreLikes(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("likes",it.toString())
                            view.showLikes(it.content.toMutableList())
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