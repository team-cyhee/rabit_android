package com.cyhee.android.rabit.activity.likelist

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class LikeListContract {
    interface View: BaseView<Presenter> {
        fun showLikes(likes: MutableList<Like>)
   }

    interface Presenter: BasePresenter {
        fun likesForGoal(id: Long)
        fun likesForGoalLog(id: Long)
    }
}