package com.cyhee.android.rabit.activity.sign.login

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.UserFactory

interface RegisterContract {
    interface View : BaseView<Presenter> {
        fun success()
        fun duplicatedUsername()
    }

    interface Presenter : BasePresenter {
        fun register(user : UserFactory.Post)
    }
}