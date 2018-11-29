package com.cyhee.android.rabit.activity.comgl

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class ComGlContract {
    interface View: BaseView<Presenter> {
        fun showComGls(comGls: MutableList<GoalLogInfo>)
        fun setWriteGoalId(id: Long)
   }

    interface Presenter: BasePresenter {
        fun comGls(id: Long)
        fun postGoalLog(id: Long, goalLog: GoalLogFactory.Post)
        fun toggleLikeForGoalLog(id: Long, post:Boolean)
        fun postCommentForGoalLog(id: Long, comment: CommentFactory.Post)
        fun deleteGoalLog(id: Long)
        fun report(id:Long, reportType: ReportType)
    }
}