package com.cyhee.android.rabit.activity.goallog

import android.util.Log
import com.cyhee.android.rabit.activity.base.DialogHandler
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.cyhee.android.rabit.client.PostClient
import com.cyhee.android.rabit.model.CommentFactory
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class GoalLogPresenter(private val view: GoalLogActivity) : GoalLogContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    override fun goalLogInfo(id: Long) {
        restClient.goalLogInfo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("goalLog",it.toString())
                            view.showGoalLogInfo(it)
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

    override fun toggleLikeForGoalLog(id: Long, post: Boolean) {
        if(post)
            PostClient.postLikeForGoalLog(id, scopeProvider) {
                view.toggleLike(true)
            }
        else
            restClient.deleteLikeForGoalLog(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .autoDisposable(scopeProvider)
                    .subscribe(
                            {
                                view.toggleLike(false)
                            },
                            {
                                DialogHandler.errorDialog(it, view)
                            }
                    )
    }

    override fun deleteGoalLog(id: Long) {
        restClient.deleteGoalLog(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                        },
                        {
                            DialogHandler.errorDialog(it, view)
                        }
                )
    }
}