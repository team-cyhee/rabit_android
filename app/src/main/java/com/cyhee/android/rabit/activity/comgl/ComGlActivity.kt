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

        presenter.goalsByUser()

        val linearLayoutManager = LinearLayoutManager(this)
        comGlListView.layoutManager = linearLayoutManager

        comGlRightFloatBtn.setOnClickListener(IntentListener.toGoalLogWriteListener())
        comGlLeftFloatBtn.setOnClickListener(IntentListener.toMainListener())
        searchBtn.setOnClickListener(IntentListener.toSearchListener())

        // swipe refresh
        comGlSwipeRefresh.setOnRefreshListener {
            Toast.makeText(this@ComGlActivity, "refreshed!", Toast.LENGTH_SHORT).show()

            comGlAdapter?.clear()
            presenter.goalsByUser()
        }
    }

    override fun showComGls(goals: MutableList<GoalInfo>, comGls: MutableList<GoalLogInfo>) {
        val spinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, goals)
        if (comGlAdapter == null) {
            comGlAdapter = ComGlViewAdapter(spinnerAdapter, comGls,
                    { id, post -> presenter.toggleLikeForGoalLog(id, post)},
                    { id, comment -> presenter.postCommentForGoalLog(id, comment)},
                    { id -> presenter.comGls(id, goals)})
            comGlListView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            comGlListView.adapter = comGlAdapter
        } else {
            comGlAdapter!!.appendComGls(comGls)
        }

        comGlSwipeRefresh?.isRefreshing = false
    }

    override fun setWriteGoalId(id: Long) {
    }
}