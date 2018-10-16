package com.cyhee.android.rabit.activity.main

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.Goal
import com.cyhee.android.rabit.model.GoalLogFactory
import com.cyhee.android.rabit.model.MainInfo

class MainContract {
    interface View: BaseView<Presenter> {
        fun showGoalNames(goals: MutableList<Goal>)
        fun showMainInfos(mainInfos: MutableList<MainInfo>)
    }

    interface Presenter: BasePresenter {
        fun goalNames()
        fun mainInfos()
        fun postGoaLog(goalLog: GoalLogFactory.Post)
    }
}