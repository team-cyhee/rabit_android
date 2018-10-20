package com.cyhee.android.rabit.activity.goal

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.Comment
import com.cyhee.android.rabit.model.CommentFactory
import com.cyhee.android.rabit.model.GoalInfo
import com.cyhee.android.rabit.model.GoalLogInfo

class GoalContract {
    interface View : BaseView<Presenter> {
        fun showGoalInfo(goalInfo: GoalInfo)
        fun showComments(comments: MutableList<Comment>)
        fun showGoalLogInfos(goalLogInfo: MutableList<GoalLogInfo>)
    }

    interface Presenter : BasePresenter {
        fun goalInfo(id: Long)
        fun goalStoreGoalLogs(id: Long)
        fun comments(goalId: Long)
        fun postLikeForGoal(id: Long)
        fun postLikeForGoalLog(id: Long)
        fun postCommentForGoal(id: Long, comment: CommentFactory.Post)
        fun postCommentForGoalLog(id: Long, comment: CommentFactory.Post)
    }
}