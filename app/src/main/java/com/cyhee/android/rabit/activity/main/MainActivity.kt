package com.cyhee.android.rabit.activity.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity: AppCompatActivity(), MainContract.View {
    override var presenter : MainContract.Presenter = MainPresenter(this)
    private var mainAdapter: MainViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.goalLogs()

    }

    override fun showGoalLogs(goalLogs: MutableList<GoalLog>) {
        // TODO: 최신 댓글 2개정도 불러와서 보여주면 좋을 듯
        if (mainAdapter == null) {
            mainAdapter = MainViewAdapter(goalLogs)
            goalLogListLayout.findViewById<RecyclerView>(R.id.listView).addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            goalLogListLayout.findViewById<RecyclerView>(R.id.listView).adapter = mainAdapter
        } else {
            mainAdapter!!.appendGoalLogs(goalLogs)
        }
    }
}