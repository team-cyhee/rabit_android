package com.cyhee.android.rabit.activity.comgl

import android.util.Log
import com.cyhee.android.rabit.activity.base.DialogHandler
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

class ComGlPresenter(private val view: ComGlActivity) : ComGlContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    override fun comGls(id: Long) {
        restClient.comGoalLogInfos(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("comgl",it.toString())
                            view.showComGls(it!!.toMutableList())
                        },
                        {
                            if(it is HttpException) {
                                Log.d("comgl",it.response().toString())
                                Log.d("comgl",it.response().body().toString())
                                Log.d("comgl",it.response().body().toString())
                                Log.d("comgl",it.response().errorBody().toString())
                                Log.d("comgl",it.response().errorBody()?.string())
                            }
                            else {
                                Log.d("comgl",it.toString())
                            }
                        }
                )
    }

    override fun postGoalLog(id: Long, goalLog: GoalLogFactory.Post) {
        PostClient.postGoalLog(id, goalLog, scopeProvider){}
    }

    override fun toggleLikeForGoalLog(id: Long, post: Boolean) {
        if (post)
            PostClient.postLikeForGoalLog(id, scopeProvider) {
                view.toggleLike(id, true)
            }
        else
            restClient.deleteLikeForGoalLog(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .autoDisposable(scopeProvider)
                    .subscribe(
                            {
                                view.toggleLike(id, false)
                            },
                            {
                                DialogHandler.errorDialog(it, view)
                            }
                    )
    }

    override fun postCommentForGoalLog(id: Long, comment: CommentFactory.Post) {
        PostClient.postCommentForGoalLog(id, comment, scopeProvider)
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