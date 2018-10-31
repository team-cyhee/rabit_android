package com.cyhee.android.rabit.activity.wall

import android.util.Log
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.cyhee.android.rabit.client.PostClient
import com.cyhee.android.rabit.model.CommentFactory
import com.cyhee.android.rabit.model.WallInfo
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class WallPresenter(private val view: WallActivity) : WallContract.Presenter {
    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    override fun wallInfo(username: String) {
        restClient.wallInfo(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("wallInfo",it.toString())
                            userMainInfos(username, it)
                        },
                        {
                            if(it is HttpException) {
                                Log.d("wallInfo",it.response().toString())
                                Log.d("wallInfo",it.response().body().toString())
                                Log.d("wallInfo",it.response().body().toString())
                                Log.d("wallInfo",it.response().errorBody().toString())
                                Log.d("wallInfo",it.response().errorBody()?.string())
                            }
                            else {
                                Log.d("wallInfo",it.toString())
                            }
                        }
                )
    }

    override fun userMainInfos(username: String, wallInfo: WallInfo) {
        restClient.userMainInfos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe(
                        {
                            Log.d("mainInfo",it.toString())
                            view.showMainInfos(it!!.toMutableList(), wallInfo)
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

    override fun postFollow(username: String) {
        PostClient.postFollow(username, scopeProvider)
    }

    override fun toggleLikeForGoal(id: Long, post: Boolean) {
        if(post)
            PostClient.postLikeForGoal(id, scopeProvider) {
                //view.toggleLike(true)
            }
        else
            restClient.deleteLikeForGoal(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .autoDisposable(scopeProvider)
                    .subscribe(
                            {
                                //view.toggleLike(false)
                            },
                            {

                            }
                    )

    }

    override fun toggleLikeForGoalLog(id: Long, post: Boolean) {
        if(post)
            PostClient.postLikeForGoalLog(id, scopeProvider) {
               // view.toggleLike(true)
            }
        else
            restClient.deleteLikeForGoalLog(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .autoDisposable(scopeProvider)
                    .subscribe(
                            {
                                //view.toggleLike(false)
                            },
                            {

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