package com.cyhee.android.rabit.activity.personlist.followerlist

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class FollowerListContract {
    interface View: BaseView<Presenter> {
        fun showFollowers(followers: MutableList<User>)
   }

    interface Presenter: BasePresenter {
        fun followers(username: String)
    }
}