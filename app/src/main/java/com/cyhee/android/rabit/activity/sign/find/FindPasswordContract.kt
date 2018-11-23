package com.cyhee.android.rabit.activity.sign.find

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView

interface FindPasswordContract {
    interface View: BaseView<Presenter> {
        fun success()
    }

    interface  Presenter: BasePresenter {
        fun find(username: String)
    }
}