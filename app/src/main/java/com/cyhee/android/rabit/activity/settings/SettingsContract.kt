package com.cyhee.android.rabit.activity.settings

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class SettingsContract {
    interface View: BaseView<Presenter>

    interface Presenter: BasePresenter
}