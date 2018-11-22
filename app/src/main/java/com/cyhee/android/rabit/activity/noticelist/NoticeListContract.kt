package com.cyhee.android.rabit.activity.noticelist

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class NoticeListContract {
    interface View: BaseView<Presenter> {
        fun showNoticeTitles(noticeTitles: MutableList<Notice>)
    }

    interface Presenter: BasePresenter {
        fun noticeTitles()
    }
}