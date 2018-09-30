package com.cyhee.android.rabit.sign.login

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.data.User

interface RegisterContract {
    interface View : BaseView<Presenter> {
        fun success()
    }

    interface Presenter : BasePresenter {
        fun register(user : User)
    }
}