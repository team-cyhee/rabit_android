package com.cyhee.android.rabit.activity.splash

import com.cyhee.android.rabit.api.core.preferences.TokenSharedPreference
import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView

interface SplashContract {
    interface View : BaseView<Presenter> {
        fun startLoginActivity()
        fun startMainActivity()
    }

    interface Presenter : BasePresenter {
        fun verify(pref : TokenSharedPreference)
    }
}