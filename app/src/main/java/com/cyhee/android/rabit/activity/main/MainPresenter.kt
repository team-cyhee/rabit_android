package com.cyhee.android.rabit.activity.main

import android.util.Log
import com.cyhee.android.rabit.activity.base.DialogHandler
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.cyhee.android.rabit.client.PostClient
import com.cyhee.android.rabit.model.CommentFactory
import com.cyhee.android.rabit.model.ContentType
import com.cyhee.android.rabit.model.GoalLogFactory
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class MainPresenter(private val view: MainActivity) : MainContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    override fun mainInfos() {
        restClient.mainInfos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("mainInfo",it.toString())
                            view.showMainInfos(it!!.toMutableList())
                        },
                        {
                            if(it is HttpException) {
                                Log.d("mainInfo",it.response().toString())
                                Log.d("mainInfo",it.response().body().toString())
                                Log.d("mainInfo",it.response().body().toString())
                                Log.d("mainInfo",it.response().errorBody().toString())
                                Log.d("mainInfo",it.response().errorBody()?.string())
                            }
                            else {
                                Log.d("mainInfo",it.toString())
                            }
                        }
                )
    }

    override fun postGoalLog(id: Long, goalLog: GoalLogFactory.Post) {
        PostClient.postGoalLog(id, goalLog, scopeProvider)
    }

    override fun toggleLikeForGoal(id: Long, post: Boolean) {
        if(post)
            restClient.postLikeForGoal(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .autoDisposable(scopeProvider)
                    .subscribe(
                            {
                                view.toggleLike(id, ContentType.GOAL, true)
                            },
                            {
                                DialogHandler.errorDialog(it, view)
                            }
                    )
        else
            restClient.deleteLikeForGoal(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .autoDisposable(scopeProvider)
                    .subscribe(
                            {
                                view.toggleLike(id, ContentType.GOAL, false)
                            },
                            {
                                DialogHandler.errorDialog(it, view)
                            }
                    )

    }

    override fun toggleLikeForGoalLog(id: Long, post: Boolean) {
        if(post)
            restClient.postLikeForGoalLog(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .autoDisposable(scopeProvider)
                    .subscribe(
                            {
                                view.toggleLike(id, ContentType.GOALLOG, true)
                            },
                            {
                                DialogHandler.errorDialog(it, view)
                            }
                    )
        else
            restClient.deleteLikeForGoalLog(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .autoDisposable(scopeProvider)
                    .subscribe(
                            {
                                view.toggleLike(id, ContentType.GOALLOG, false)
                            },
                            {
                                DialogHandler.errorDialog(it, view)
                            }
                    )
    }

    override fun postCommentForGoal(id: Long, comment: CommentFactory.Post) {
        PostClient.postCommentForGoal(id, comment, scopeProvider)
    }

    override fun postCommentForGoalLog(id: Long, comment: CommentFactory.Post) {
        PostClient.postCommentForGoalLog(id, comment, scopeProvider)
    }
}