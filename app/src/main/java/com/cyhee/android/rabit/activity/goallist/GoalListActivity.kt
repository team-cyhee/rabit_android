package com.cyhee.android.rabit.activity.goallist

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_goallist.*
import kotlinx.android.synthetic.main.item_complete_prevtopbar.*
import android.support.v7.widget.GridLayoutManager
import com.cyhee.android.rabit.activity.decoration.CardItemDeco


class GoalListActivity: AppCompatActivity(), GoalListContract.View {

    override var presenter : GoalListContract.Presenter = GoalListPresenter(this)
    private var goalListAdapter: GoalListViewAdapter? = null

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goallist)

        if (intent.hasExtra("username")) {
            val username = intent.getStringExtra("username")
            presenter.userGoalInfos(username)

            val gridLayoutManager = GridLayoutManager(this, 2)
            goalListListView.layoutManager = gridLayoutManager

            topName.text = username

            // swipe refresh
            goalListSwipeRefresh.setOnRefreshListener {
                Toast.makeText(this@GoalListActivity, "refreshed!", Toast.LENGTH_SHORT).show()

                goalListAdapter?.clear()
                presenter.userGoalInfos(username)
            }
        }

        prevBtn.setOnClickListener {
            Log.d("preBtn","clicked")
            finish()
        }

        myWallBtn.setOnClickListener(IntentListener.toMyWallListener(user))

    }

    override fun showGoals(goalInfos: MutableList<GoalInfo>) {
        if (goalListAdapter == null) {
            goalListAdapter = GoalListViewAdapter(goalInfos)
            goalListListView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            goalListListView.adapter = goalListAdapter
        } else {
            goalListAdapter!!.appendGoalInfos(goalInfos)
        }

        goalListListView.addItemDecoration(CardItemDeco(this))
        goalListSwipeRefresh?.isRefreshing = false
    }

}