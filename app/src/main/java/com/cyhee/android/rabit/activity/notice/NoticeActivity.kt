package com.cyhee.android.rabit.activity.notice

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.Notice
import kotlinx.android.synthetic.main.activity_notice.*
import kotlinx.android.synthetic.main.item_complete_topbar.*

class NoticeActivity: AppCompatActivity(), NoticeContract.View {
    override var presenter : NoticeContract.Presenter = NoticePresenter(this)

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)
        if (intent.hasExtra("noticeId")) {
            val noticeId = intent.getLongExtra("noticeId", -1)
            presenter.notice(noticeId)
            searchBtn.setOnClickListener(IntentListener.toSearchListener())
        }
    }

    override fun showNotice(notice: Notice) {
        noticeTitle.text = notice.title
        noticeContent.text = notice.content
    }
}