package com.cyhee.android.rabit.activity.goal

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.Goal
import java.util.ArrayList

class GoalContract {
    interface View: BaseView<Presenter> {
        fun showGoals(goals: MutableList<Goal>)
    }

    interface Presenter: BasePresenter {
        fun goals()
    }
}