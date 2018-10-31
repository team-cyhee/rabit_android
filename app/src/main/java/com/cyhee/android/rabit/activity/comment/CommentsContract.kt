package com.cyhee.android.rabit.activity.comment

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class CommentsContract {
    interface View: BaseView<Presenter> {
        fun showComments(comments: MutableList<Comment>, id: Long, type: ContentType)
   }

    interface Presenter: BasePresenter {
        fun commentsForGoal(id: Long)
        fun commentsForGoalLog(id: Long)
        fun postCommentForGoal(id: Long, comment: CommentFactory.Post)
        fun postCommentForGoalLog(id: Long, comment: CommentFactory.Post)
    }
}