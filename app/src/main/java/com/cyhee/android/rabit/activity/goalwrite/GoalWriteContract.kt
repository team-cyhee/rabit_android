package com.cyhee.android.rabit.activity.goalwrite

import android.net.Uri
import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class GoalWriteContract {
    interface View : BaseView<Presenter>

    interface Presenter : BasePresenter {
        fun postGoal(goal: GoalFactory.Post)
        fun postCompanion(id: Long, goal: GoalFactory.Post)
        fun editGoal(id: Long, goal: GoalFactory.Post)
        fun upload(goal: GoalFactory.Post, fileUri: Uri?)
        fun editUpload(id: Long, goal: GoalFactory.Post, fileUri: Uri?)
    }
}