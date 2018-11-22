package com.cyhee.android.rabit.activity.question

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.activity.base.DialogHandler.confirmDialog
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.QuestionFactory
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.item_complete_topbar.*

class QuestionActivity: AppCompatActivity(), QuestionContract.View {

    override var presenter : QuestionContract.Presenter = QuestionPresenter(this)

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        searchBtn.setOnClickListener(IntentListener.toSearchListener())

        post_question_btn.setOnClickListener {
            val content = question_content.text.toString()
            val question = QuestionFactory.Post(content)
            presenter.postQuestion(question)
        }

    }

}