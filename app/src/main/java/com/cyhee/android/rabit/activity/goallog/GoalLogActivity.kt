package com.cyhee.android.rabit.activity.goallog

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_goallog.*


class GoalLogActivity: AppCompatActivity(), GoalLogContract.View {
    override var presenter : GoalLogContract.Presenter = GoalLogPresenter(this)
    private var goalLogAdapter: GoalLogViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goallog)

        if (intent.hasExtra("goalLogId")) {
            val goalLogId = intent.getLongExtra("goalLogId", -1)
            presenter.goalLog(goalLogId)
        } else {
            Toast.makeText(this, "전달된 goalLog 아이디가 없습니다", Toast.LENGTH_SHORT).show()
        }
    }

    override fun showGoalLog(goalLog : GoalLog) {
        var goalLogView = goalLogLayout.findViewById<View>(R.id.goalWriterLayout)
        goalLogView.findViewById<TextView>(R.id.nameText).text = goalLog.goal.author.username
        goalLogView.findViewById<TextView>(R.id.titleText).text = goalLog.goal.content
        if (goalLog.goal.parent != null) {
            // 사람 수 추가
            val original = "${goalLog.goal.parent.author.username} 님 외 n명이 함께하는 중"
            goalLogView.findViewById<TextView>(R.id.originalWriterText).text = original
        }
        goalLogLayout.findViewById<View>(R.id.textLayout).findViewById<TextView>(R.id.text).text = goalLog.content
    }

    override fun showComments(comments: MutableList<Comment>) {
        if (goalLogAdapter == null) {
            goalLogAdapter = GoalLogViewAdapter(comments)
            goalLogLayout.findViewById<View>(R.id.commentListLayout).findViewById<RecyclerView>(R.id.listView).addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            goalLogLayout.findViewById<View>(R.id.commentListLayout).findViewById<RecyclerView>(R.id.listView).adapter = goalLogAdapter
        } else {
            goalLogAdapter!!.appendGoalLogs(comments)
        }
    }
}