package com.cyhee.android.rabit.activity.goalwrite

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class GoalWriteContract {
    interface View : BaseView<Presenter>

    interface Presenter : BasePresenter {
        fun postGoal(goal: GoalFactory.Post)
        fun postCompanion(id: Long, goal: GoalFactory.Post)
    }
}