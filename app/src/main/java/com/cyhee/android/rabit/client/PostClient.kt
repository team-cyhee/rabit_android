package com.cyhee.android.rabit.client

import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.cyhee.android.rabit.model.CommentFactory
import com.cyhee.android.rabit.model.GoalLogFactory
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object PostClient {
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    fun postGoaLog(id: Long, goalLog: GoalLogFactory.Post, scopeProvider: AndroidLifecycleScopeProvider) {
        restClient.postGoalLog(id, goalLog)
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

    fun postLikeForGoal(id: Long, scopeProvider: AndroidLifecycleScopeProvider) {
        restClient.postLikeForGoal(id)
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

    fun postLikeForGoalLog(id: Long, scopeProvider: AndroidLifecycleScopeProvider) {
        restClient.postLikeForGoalLog(id)
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