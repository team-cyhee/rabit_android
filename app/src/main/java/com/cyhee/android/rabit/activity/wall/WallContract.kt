package com.cyhee.android.rabit.activity.wall

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class WallContract {
    interface View : BaseView<Presenter> {
        fun showMainInfos(mainInfos: MutableList<MainInfo>, wallInfo: WallInfo?)
    }

    interface Presenter : BasePresenter {
        fun wallInfo(username: String)
        fun userMainInfos(username: String, wallInfo: WallInfo?, time: Long? = null)
        fun postFollow(username: String)
        fun toggleLikeForGoal(id: Long, post: Boolean)
        fun toggleLikeForGoalLog(id: Long, post: Boolean)
        fun postCommentForGoal(id: Long, comment: CommentFactory.Post)
        fun postCommentForGoalLog(id: Long, comment: CommentFactory.Post)
        fun deleteGoal(id: Long)
        fun deleteGoalLog(id: Long)
        fun report(type: ContentType, id: Long, reportType: ReportType)
    }
}