package com.cyhee.android.rabit.activity.goalloglist

import android.util.Log
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.cyhee.android.rabit.client.PostClient
import com.cyhee.android.rabit.model.CommentFactory
import com.cyhee.android.rabit.model.GoalLogFactory
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class GoalLogListPresenter(private val view: GoalLogListActivity) : GoalLogListContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    override fun goalLogs(id: Long) {
        restClient.goalStoreGoalLogs(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("goalGl",it.toString())
                            view.showComGls(it!!.toMutableList())
                        },
                        {
                            if(it is HttpException) {
                                Log.d("goalGl",it.response().toString())
                                Log.d("goalGl",it.response().body().toString())
                                Log.d("goalGl",it.response().body().toString())
                                Log.d("goalGl",it.response().errorBody().toString())
                                Log.d("goalGl",it.response().errorBody()?.string())
                            }
                            else {
                                Log.d("goalGl",it.toString())
                            }
                        }
                )
    }

    override fun postGoalLog(id: Long, goalLog: GoalLogFactory.Post) {
        PostClient.postGoalLog(id, goalLog, scopeProvider)
    }

    override fun toggleLikeForGoalLog(id: Long, post: Boolean) {
        if (post)
            PostClient.postLikeForGoalLog(id, scopeProvider) {
            }
        else
            restClient.deleteLikeForGoalLog(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .autoDisposable(scopeProvider)
                    .subscribe(
                            {
                            },
                            {
                            }
                    )
    }

    override fun postCommentForGoalLog(id: Long, comment: CommentFactory.Post) {
        PostClient.postCommentForGoalLog(id, comment, scopeProvider)
    }
}