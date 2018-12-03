package com.cyhee.android.rabit.activity.goallist

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class GoalListContract {
    interface View: BaseView<Presenter> {
        fun showGoals(goalInfos: MutableList<GoalInfo>)
   }

    interface Presenter: BasePresenter {
        fun userGoalInfos(username: String, time: Long? = null)
    }
}