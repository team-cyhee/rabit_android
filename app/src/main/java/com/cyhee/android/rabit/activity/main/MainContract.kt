package com.cyhee.android.rabit.activity.main

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class MainContract {
    interface View: BaseView<Presenter> {
        fun showMainInfos(mainInfos: MutableList<MainInfo>)
   }

    interface Presenter: BasePresenter {
        fun mainInfos(order: Long? = null)
        fun postGoalLog(id: Long, goalLog: GoalLogFactory.Post)
        fun toggleLikeForGoal(id: Long, post:Boolean)
        fun toggleLikeForGoalLog(id: Long, post:Boolean)
        fun postCommentForGoal(id: Long, comment: CommentFactory.Post)
        fun postCommentForGoalLog(id: Long, comment: CommentFactory.Post)
        fun deleteGoal(id: Long)
        fun deleteGoalLog(id: Long)
        fun report(type: ContentType, id: Long, reportType: ReportType)
    }
}