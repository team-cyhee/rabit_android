package com.cyhee.android.rabit.activity.comgl

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class ComGlContract {
    interface View: BaseView<Presenter> {
        fun showComGls(goals: MutableList<GoalInfo>, comGls: MutableList<GoalLogInfo>)
        fun setWriteGoalId(id: Long)
   }

    interface Presenter: BasePresenter {
        fun goalsByUser()
        fun comGls(id: Long, goals: MutableList<GoalInfo>)
        fun postGoalLog(id: Long, goalLog: GoalLogFactory.Post)
        fun toggleLikeForGoalLog(id: Long, post:Boolean)
        fun postCommentForGoalLog(id: Long, comment: CommentFactory.Post)
    }
}