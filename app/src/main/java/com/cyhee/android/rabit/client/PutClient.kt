package com.cyhee.android.rabit.client

import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import com.cyhee.android.rabit.model.GoalFactory
import com.cyhee.android.rabit.model.GoalLogFactory
import com.cyhee.android.rabit.model.UserFactory
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object PutClient {

    private val TAG = PutClient::class.qualifiedName
    private val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

    fun putUser(username: String, user: UserFactory.Edit, scopeProvider: AndroidLifecycleScopeProvider, callback: () -> Unit) {
        restClient.putUser(username, user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe (
                        {
                            callback()
                        },
                        {
                        }
                )
    }

    fun putGoal(id: Long, goal: GoalFactory.Post, scopeProvider: AndroidLifecycleScopeProvider, callback: () -> Unit) {
        restClient.putGoal(id, goal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe (
                        {
                            callback()
                        },
                        {
                        }
                )
    }

    fun putGoalLog(id: Long, goalLog: GoalLogFactory.Post, scopeProvider: AndroidLifecycleScopeProvider, callback: () -> Unit) {
        restClient.putGoalLog(id, goalLog)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(scopeProvider)
                .subscribe (
                        {
                            callback()
                        },
                        {
                            // TODO: post완료되면 화면 새로고침?
                        }
                )
    }
}