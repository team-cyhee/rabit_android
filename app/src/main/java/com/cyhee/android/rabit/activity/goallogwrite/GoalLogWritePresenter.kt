package com.cyhee.android.rabit.activity.goallogwrite

import android.util.Log
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.cyhee.android.rabit.client.PostClient
import com.cyhee.android.rabit.model.GoalLogFactory
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class GoalLogWritePresenter(private val view: GoalLogWriteActivity) : GoalLogWriteContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    override fun goalNames() {
        restClient.goalsByUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("goal", it.toString())
                            view.showGoalNames(it.toMutableList())
                        },
                        {
                            if(it is HttpException) {
                                Log.d("goal",it.response().toString())
                                Log.d("goal",it.response().body().toString())
                                Log.d("goal",it.response().body().toString())
                                Log.d("goal",it.response().errorBody().toString())
                                Log.d("goal",it.response().errorBody()?.string())
                            }
                            else {
                                Log.d("goal",it.toString())
                            }
                        }
                )
    }

    override fun postGoalLog(id: Long, goalLog: GoalLogFactory.Post) {
        PostClient.postGoalLog(id, goalLog, scopeProvider)
    }
}