package com.cyhee.android.rabit.activity.sign.register

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.UserFactory

interface RegisterContract {
    interface View : BaseView<Presenter> {
        fun duplicatedUsername()
    }

    interface Presenter : BasePresenter {
        fun register(user:UserFactory.Post)
        fun socialRegister(type: String, token: String, user: UserFactory.Post)
    }
}