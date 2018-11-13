package com.cyhee.android.rabit.activity.goalloglist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.*
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.activity.comgl.ComGlViewAdapter
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_goalloglist.*
import kotlinx.android.synthetic.main.item_complete_topbar.*

class GoalLogListActivity: AppCompatActivity(), GoalLogListContract.View {

    override var presenter : GoalLogListContract.Presenter = GoalLogListPresenter(this)
    private var comGlAdapter: ComGlViewAdapter? = null

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goalloglist)

        if (intent.hasExtra("goalId")) {
            val goalId = intent.getLongExtra("goalId", -1)
            presenter.goalLogs(goalId)

            // swipe refresh
            glListSwipeRefresh.setOnRefreshListener {
                Toast.makeText(this@GoalLogListActivity, "refreshed!", Toast.LENGTH_SHORT).show()

                comGlAdapter?.clear()
                presenter.goalLogs(goalId)
            }
        } else {
            Toast.makeText(this, "전달된 goalId가 없습니다", Toast.LENGTH_SHORT).show()
        }

        searchBtn.setOnClickListener(IntentListener.toSearchListener())


    }

    override fun showComGls(comGls: MutableList<GoalLogInfo>) {
        if (comGlAdapter == null) {
            comGlAdapter = ComGlViewAdapter(comGls
            ) { id, post -> presenter.toggleLikeForGoalLog(id, post)}
            glListListView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            glListListView.adapter = comGlAdapter
        } else {
            comGlAdapter!!.appendComGls(comGls)
        }

        glListSwipeRefresh?.isRefreshing = false
    }

    override fun setWriteGoalId(id: Long) {
    }
}