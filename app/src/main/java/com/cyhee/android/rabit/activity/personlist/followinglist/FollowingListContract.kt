package com.cyhee.android.rabit.activity.personlist.followinglist

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class FollowingListContract {
    interface View: BaseView<Presenter> {
        fun showFollowees(followees: MutableList<User>)
   }

    interface Presenter: BasePresenter {
        fun followees(username: String)
    }
}