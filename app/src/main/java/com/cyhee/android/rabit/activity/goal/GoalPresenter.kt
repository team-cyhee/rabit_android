package com.cyhee.android.rabit.activity.goal

import android.util.Log
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.cyhee.android.rabit.model.Comment
import com.cyhee.android.rabit.model.CommentFactory
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class GoalPresenter(private val view: GoalActivity) : GoalContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    override fun goalInfo(id: Long) {
        restClient.goalInfo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("goal",it.toString())
                            view.showGoalInfo(it)
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

        comments(id)
    }

    override fun comments(goalId: Long) {
        restClient.goalStoreComments(goalId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("comments",it.toString())
                            view.showComments(it.content)
                        },
                        {
                            if(it is HttpException) {
                                Log.d("comments",it.response().toString())
                                Log.d("comments",it.response().body().toString())
                                Log.d("comments",it.response().body().toString())
                                Log.d("comments",it.response().errorBody().toString())
                                Log.d("comments",it.response().errorBody()?.string())
                            }
                            else {
                                Log.d("comments",it.toString())
                            }
                        }
                )
    }

    override fun postCommentForGoal(id: Long, comment: CommentFactory.Post) {
        restClient.postCommentForGoal(id, comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe (
                        {
                        },
                        {
                            // TODO: post완료되면 화면 새로고침?
                        }
                )
    }
}