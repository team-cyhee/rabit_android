package com.cyhee.android.rabit.activity.notice

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class NoticeContract {
    interface View: BaseView<Presenter> {
        fun showNotice(notice: Notice)
    }

    interface Presenter: BasePresenter {
        fun notice(id: Long)
    }
}