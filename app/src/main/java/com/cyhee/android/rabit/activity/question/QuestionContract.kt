package com.cyhee.android.rabit.activity.question

import com.cyhee.android.rabit.base.BasePresenter
import com.cyhee.android.rabit.base.BaseView
import com.cyhee.android.rabit.model.*

class QuestionContract {
    interface View: BaseView<Presenter>

    interface Presenter: BasePresenter {
        fun postQuestion(question: QuestionFactory.Post)
    }
}