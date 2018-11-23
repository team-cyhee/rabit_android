package com.cyhee.android.rabit.activity.alarm

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class AlarmContract {
    interface View: BaseView<Presenter> {
        fun showAlarms(alarms: MutableList<Alarm>)
    }

    interface Presenter: BasePresenter {
        fun alarms()
    }
}