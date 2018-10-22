package com.cyhee.android.rabit.activity.mywall

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class MyWallContract {
    interface View : BaseView<Presenter> {
        fun showWallInfo(wallInfo: WallInfo)
        fun showMainInfos(mainInfos: MutableList<MainInfo>)
    }

    interface Presenter : BasePresenter {
        fun wallInfo(username: String)
        fun userMainInfos(username: String)
        fun postLikeForGoal(id: Long)
        fun postLikeForGoalLog(id: Long)
        fun postCommentForGoal(id: Long, comment: CommentFactory.Post)
        fun postCommentForGoalLog(id: Long, comment: CommentFactory.Post)
    }
}