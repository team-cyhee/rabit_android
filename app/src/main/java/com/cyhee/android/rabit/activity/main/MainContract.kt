package com.cyhee.android.rabit.activity.main

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.Goal
import com.cyhee.android.rabit.model.GoalLog

class MainContract {
    interface View: BaseView<Presenter> {
        fun showGoalNames(goals: MutableList<Goal>)
        fun showGoalLogs(goalLogs: MutableList<GoalLog>)
    }

    interface Presenter: BasePresenter {
        fun goalNames()
        fun goalLogs()
        fun postGoaLog(goalLog: GoalLog)
    }
}