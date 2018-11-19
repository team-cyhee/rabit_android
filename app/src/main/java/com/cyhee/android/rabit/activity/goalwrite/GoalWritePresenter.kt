package com.cyhee.android.rabit.activity.goalwrite

import com.cyhee.android.rabit.client.PostClient
import com.cyhee.android.rabit.client.PutClient
import com.cyhee.android.rabit.model.GoalFactory
import com.cyhee.android.rabit.model.GoalLogFactory
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

class GoalWritePresenter(private val view: GoalWriteActivity) : GoalWriteContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }

    override fun postGoal(goal: GoalFactory.Post) {
        PostClient.postGoal(goal, scopeProvider)
    }

    override fun editGoal(id: Long, goal: GoalFactory.Post) {
        PutClient.putGoal(id, goal, scopeProvider) {
            view.finish()
        }
    }

    override fun postCompanion(id: Long, goal: GoalFactory.Post) {
        PostClient.postCompanion(id, goal, scopeProvider)
    }
}