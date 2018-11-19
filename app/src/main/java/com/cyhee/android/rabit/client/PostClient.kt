package com.cyhee.android.rabit.client

import android.util.Log
import com.cyhee.android.rabit.activity.base.DialogHandler
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.cyhee.android.rabit.model.CommentFactory
import com.cyhee.android.rabit.model.GoalFactory
import com.cyhee.android.rabit.model.GoalLogFactory
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object PostClient {

    private val TAG = PostClient::class.qualifiedName
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    fun postGoal(goal: GoalFactory.Post, scopeProvider: AndroidLifecycleScopeProvider) {
        restClient.postGoal( goal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe (
                        {
                        },
                        {
                        }
                )
    }

    fun postGoalLog(id: Long, goalLog: GoalLogFactory.Post, scopeProvider: AndroidLifecycleScopeProvider, callback: () -> Unit) {
        restClient.postGoalLog(id, goalLog)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe (
                        {
                            Log.d(TAG, "a goal log posted")
                            callback()
                        },
                        {
                            // TODO: post완료되면 화면 새로고침?
                        }
                )
    }

    fun postCompanion(id: Long, goal: GoalFactory.Post, scopeProvider: AndroidLifecycleScopeProvider) {
        restClient.postCompanion(id, goal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe (
                        {
                        },
                        {
                        }
                )
    }

    fun postFollow(username: String, scopeProvider: AndroidLifecycleScopeProvider) {
        restClient.postFollow(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe (
                        {
                        },
                        {
                        }
                )
    }

    fun postLikeForGoal(id: Long, scopeProvider: AndroidLifecycleScopeProvider, callback: () -> Unit) {
        restClient.postLikeForGoal(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe (
                        {
                            callback()
                        },
                        {
                            Log.d(TAG, "Get error from postLikeForGoal")
                        }
                )
    }

    fun postLikeForGoalLog(id: Long, scopeProvider: AndroidLifecycleScopeProvider, callback:() -> Unit) {
        restClient.postLikeForGoalLog(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe (
                        {
                            callback()
                        },
                        {
                            Log.d(TAG, "Get error from postLikeForGoalLog")
                        }
                )
    }

   fun postCommentForGoal(id: Long, comment: CommentFactory.Post, scopeProvider: AndroidLifecycleScopeProvider) {
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

   fun postCommentForGoalLog(id: Long, comment: CommentFactory.Post, scopeProvider: AndroidLifecycleScopeProvider) {
       restClient.postCommentForGoalLog(id, comment)
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