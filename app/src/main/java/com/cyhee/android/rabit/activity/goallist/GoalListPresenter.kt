package com.cyhee.android.rabit.activity.goallist

import android.util.Log
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.cyhee.android.rabit.client.PostClient
import com.cyhee.android.rabit.model.CommentFactory
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class GoalListPresenter(private val view: GoalListActivity) : GoalListContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    override fun userGoalInfos(username: String, time: Long?) {
        restClient.userGoalInfos(username, time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("goalInfos",it.toString())
                            view.showGoals(it.toMutableList())
                        },
                        {
                            if(it is HttpException) {
                                Log.d("goalInfos",it.response().toString())
                                Log.d("goalInfos",it.response().body().toString())
                                Log.d("goalInfos",it.response().body().toString())
                                Log.d("goalInfos",it.response().errorBody().toString())
                                Log.d("goalInfos",it.response().errorBody()?.string())
                            }
                            else {
                                Log.d("goalInfos",it.toString())
                            }
                        }
                )
    }
}