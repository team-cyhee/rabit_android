package com.cyhee.android.rabit.activity.goal

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.sign.login.RegisterContract
import com.cyhee.android.rabit.activity.sign.login.RegisterPresenter
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_goallist.*
import java.util.*


class GoalListActivity: AppCompatActivity(), GoalContract.View {
    override var presenter : GoalContract.Presenter = GoalPresenter(this)
    private var goalAdapter: GoalViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goallist)

        presenter.goals()
    }

    override fun showGoals(goals: MutableList<Goal>) {
        if (goalAdapter == null) {
            goalAdapter = GoalViewAdapter(goals)
            goalListView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            goalListView.adapter = goalAdapter
        } else {
            goalAdapter!!.appendGoals(goals)
        }
    }
}