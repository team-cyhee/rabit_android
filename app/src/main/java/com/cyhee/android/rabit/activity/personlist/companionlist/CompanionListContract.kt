package com.cyhee.android.rabit.activity.personlist.companionlist

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class CompanionListContract {
    interface View: BaseView<Presenter> {
        fun showCompanions(companions: MutableList<User>)
   }

    interface Presenter: BasePresenter {
        fun companionsForGoal(id: Long)
        fun companionsForGoalLog(id: Long)
    }
}