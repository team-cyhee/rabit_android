package com.cyhee.android.rabit.activity.goalwrite

import com.cyhee.android.rabit.client.PostClient
import com.cyhee.android.rabit.model.GoalFactory
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

class GoalWritePresenter(private val view: GoalWriteActivity) : GoalWriteContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }

    override fun postGoal(goal: GoalFactory.Post) {
        PostClient.postGoal(goal, scopeProvider)
    }

    override fun postCompanion(id: Long, goal: GoalFactory.Post) {
        PostClient.postCompanion(id, goal, scopeProvider)
    }
}