package com.cyhee.android.rabit.activity.goal

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.Comment
import com.cyhee.android.rabit.model.CommentFactory
import com.cyhee.android.rabit.model.GoalInfo
import com.cyhee.android.rabit.model.GoalLogInfo

class GoalContract {
    interface View : BaseView<Presenter> {
        fun showGoalLogInfos(goalInfo: GoalInfo, goalLogInfos: MutableList<GoalLogInfo>)
    }

    interface Presenter : BasePresenter {
        fun goalInfos(id: Long)
        fun goalStoreGoalLogs(id: Long, goalInfo: GoalInfo)
        fun toggleLikeForGoal(id: Long, post:Boolean)
        fun toggleLikeForGoalLog(id: Long, post:Boolean)
        fun postCommentForGoal(id: Long, comment: CommentFactory.Post)
        fun postCommentForGoalLog(id: Long, comment: CommentFactory.Post)
    }
}