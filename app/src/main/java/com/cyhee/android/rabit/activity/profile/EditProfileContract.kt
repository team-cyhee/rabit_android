package com.cyhee.android.rabit.activity.profile

import android.net.Uri
import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class EditProfileContract {
    interface View : BaseView<Presenter> {
        fun showUserInfo(user: User)
    }

    interface Presenter : BasePresenter {
        fun userInfo()
        fun editUser(userInfo: UserFactory.Edit)
        fun upload(userInfo: UserFactory.Edit, fileUri: Uri?)
    }
}