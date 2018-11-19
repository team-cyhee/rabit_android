package com.cyhee.android.rabit.activity.goallogwrite

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class GoalLogWriteContract {
    interface View : BaseView<Presenter> {
       //fun showGoalNames(goals: MutableList<Goal>?)
    }

    interface Presenter : BasePresenter {
        //fun goalNames()
        fun postGoalLog(id: Long, goalLog: GoalLogFactory.Post)
    }
}