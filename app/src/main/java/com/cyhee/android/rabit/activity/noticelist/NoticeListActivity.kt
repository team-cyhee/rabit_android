package com.cyhee.android.rabit.activity.noticelist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.Notice
import kotlinx.android.synthetic.main.activity_noticelist.*
import kotlinx.android.synthetic.main.item_complete_topbar.*

class NoticeListActivity: AppCompatActivity(), NoticeListContract.View {
    override var presenter : NoticeListContract.Presenter = NoticeListPresenter(this)
    private var noticeAdapter: NoticeListViewAdapter? = null

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticelist)

        search_btn.setOnClickListener(IntentListener.toSearchListener())

        presenter.noticeTitles()
    }

    override fun showNoticeTitles(noticeTitles: MutableList<Notice>) {
        if (noticeAdapter == null) {
            noticeAdapter = NoticeListViewAdapter(noticeTitles)
            notice_list_view.adapter = noticeAdapter
        } else {
            noticeAdapter!!.appendNoticeTitles(noticeTitles)
        }
    }
}