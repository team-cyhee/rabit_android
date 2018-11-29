package com.cyhee.android.rabit.activity.goallog

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.GoalLogInfo
import com.cyhee.android.rabit.model.ReportType

class GoalLogContract {
    interface View: BaseView<Presenter> {
        fun showGoalLogInfo(goalLogInfo: GoalLogInfo)
    }

    interface Presenter: BasePresenter {
        fun goalLogInfo(id: Long)
        fun toggleLikeForGoalLog(id: Long, post: Boolean)
        fun deleteGoalLog(id: Long)
        fun report(id: Long, reportType:ReportType)
    }
}