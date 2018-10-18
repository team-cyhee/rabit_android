package com.cyhee.android.rabit.activity.goal

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.Comment
import com.cyhee.android.rabit.model.CommentFactory
import com.cyhee.android.rabit.model.GoalInfo

class GoalContract {
    interface View : BaseView<Presenter> {
        fun showGoalInfo(goalInfo: GoalInfo)
        fun showComments(comments: MutableList<Comment>)
    }

    interface Presenter : BasePresenter {
        fun goalInfo(id: Long)
        fun comments(goalId: Long)
        fun postCommentForGoal(id: Long, comment: CommentFactory.Post)
    }
}