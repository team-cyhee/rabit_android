package com.cyhee.android.rabit.activity.main

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.GoalLog

class MainContract {
    interface View: BaseView<Presenter> {
        fun showGoalLogs(goals: MutableList<GoalLog>)
    }

    interface Presenter: BasePresenter {
        fun goalLogs()
    }
}