package com.cyhee.android.rabit.activity.search

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class SearchContract {
    interface View: BaseView<Presenter> {
        fun showUserResult(users: MutableList<User>)
        fun showGoalResult(goals: MutableList<Goal>)
        fun showGoalLogResult(goalLogs: MutableList<GoalLog>)
   }

    interface Presenter: BasePresenter {
        fun searchUsers(keyword: String)
        fun searchGoals(keyword: String)
        fun searchGoalLogs(keyword: String)
    }
}