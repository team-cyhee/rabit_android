package com.cyhee.android.rabit.sign.login

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.data.User
import io.reactivex.Completable

interface RegisterContract {
    interface View : BaseView<Presenter> {
        fun success()
        fun duplicatedUsername()
    }

    interface Presenter : BasePresenter {
        fun register(user : User)
    }
}