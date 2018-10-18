package com.cyhee.android.rabit.activity.goallog

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.Comment
import com.cyhee.android.rabit.model.CommentFactory
import com.cyhee.android.rabit.model.GoalLogInfo

class GoalLogContract {
    interface View: BaseView<Presenter> {
        fun showGoalLogInfo(goalLogInfo: GoalLogInfo)
        fun showComments(comments: MutableList<Comment>)
    }

    interface Presenter: BasePresenter {
        fun goalLogInfo(id: Long)
        fun comments(goalLogId: Long)
        fun postCommentForGoalLog(id: Long, comment: CommentFactory.Post)
    }
}