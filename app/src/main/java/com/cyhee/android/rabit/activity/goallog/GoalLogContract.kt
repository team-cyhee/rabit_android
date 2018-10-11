package com.cyhee.android.rabit.activity.goallog

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.Comment
import com.cyhee.android.rabit.model.GoalLog

class GoalLogContract {
    interface View: BaseView<Presenter> {
        fun showGoalLog(goalLog: GoalLog)
        fun showComments(comments: MutableList<Comment>)
    }

    interface Presenter: BasePresenter {
        fun goalLog(id: Long)
        fun comments(goalLogId: Long)
    }
}