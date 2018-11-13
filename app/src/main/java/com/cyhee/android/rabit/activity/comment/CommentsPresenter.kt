package com.cyhee.android.rabit.activity.comment

import android.util.Log
import com.cyhee.android.rabit.activity.base.DialogHandler
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.cyhee.android.rabit.client.PostClient
import com.cyhee.android.rabit.model.CommentFactory
import com.cyhee.android.rabit.model.ContentType
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class CommentsPresenter(private val view: CommentsActivity) : CommentsContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    override fun commentsForGoal(id: Long) {
        restClient.goalStoreComments(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("comments",it.toString())
                            view.showComments(it.content.toMutableList(), id, ContentType.GOAL)
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

    override fun commentsForGoalLog(id: Long) {
        restClient.goalLogStoreComments(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("comments",it.toString())
                            view.showComments(it.content.toMutableList(), id, ContentType.GOALLOG)
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
//        PostClient.postCommentForGoal(id, comment, scopeProvider)
        restClient.postCommentForGoal(id, comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            it.headers().get("Location")?.apply {
                                addComment(this.split("/").last().toLong())
                            }
                        },
                        {
                            DialogHandler.errorDialog(it, view)
                        }
                )
    }

    override fun postCommentForGoalLog(id: Long, comment: CommentFactory.Post) {
//        PostClient.postCommentForGoalLog(id, comment, scopeProvider)
        restClient.postCommentForGoalLog(id, comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            it.headers().get("Location")?.apply {
                                addComment(this.split("/").last().toLong())
                            }
                        },
                        {
                            DialogHandler.errorDialog(it, view)
                        }
                )
    }

    private fun addComment(id: Long) {
        restClient.comment(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            view.addComment(it)
                        },
                        {
                            DialogHandler.errorDialog(it, view)
                        }
                )
    }
}