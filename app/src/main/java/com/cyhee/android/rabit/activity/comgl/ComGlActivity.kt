package com.cyhee.android.rabit.activity.comgl

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.*
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_comgoallog.*
import kotlinx.android.synthetic.main.item_complete_topbar.*

class ComGlActivity: AppCompatActivity(), ComGlContract.View {

    override var presenter : ComGlContract.Presenter = ComGlPresenter(this)
    private var comGlAdapter: ComGlViewAdapter? = null

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comgoallog)

        if (intent.hasExtra("goalId")) {
            val goalId = intent.getLongExtra("goalId", -1)
            presenter.comGls(goalId)

            searchBtn.setOnClickListener(IntentListener.toSearchListener())
            toUpBtn.setOnClickListener{
                comGlListView.smoothScrollToPosition(0)
            }

            // swipe refresh
            comGlSwipeRefresh.setOnRefreshListener {
                Toast.makeText(this@ComGlActivity, "refreshed!", Toast.LENGTH_SHORT).show()

                comGlAdapter?.clear()
                presenter.comGls(goalId)
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
            comGlListView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            comGlListView.adapter = comGlAdapter
        } else {
            comGlAdapter!!.appendComGls(comGls)
        }

        comGlSwipeRefresh?.isRefreshing = false
    }

    override fun setWriteGoalId(id: Long) {
    }

    fun toggleLike(id: Long, boolean: Boolean) {
        comGlAdapter!!.toggleLike(id, boolean)
    }
}