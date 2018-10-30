package com.cyhee.android.rabit.activity.goal

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_goal.*
import kotlinx.android.synthetic.main.item_complete_fullgoal.*
import kotlinx.android.synthetic.main.item_complete_prevtopbar.*
import kotlinx.android.synthetic.main.item_part_goalwriter.*
import kotlinx.android.synthetic.main.item_part_reaction.*
import java.text.SimpleDateFormat


class GoalActivity: AppCompatActivity(), GoalContract.View {
    override var presenter : GoalContract.Presenter = GoalPresenter(this)
    private var goalAdapter: GoalViewAdapter? = null

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)

        if (intent.hasExtra("goalId")) {
            val goalId = intent.getLongExtra("goalId", -1)
            presenter.goalInfos(goalId)

            goalSwipeRefresh.setOnRefreshListener {
                Toast.makeText(this@GoalActivity, "refreshed!", Toast.LENGTH_SHORT).show()

                goalAdapter?.clear()
                presenter.goalInfos(goalId)
            }
        } else {
            Toast.makeText(this, "전달된 goal 아이디가 없습니다", Toast.LENGTH_SHORT).show()
        }

        prevBtn.setOnClickListener {
            Log.d("preBtn","clicked")
            finish()
        }

        myWallBtn.setOnClickListener(IntentListener.toMyWallListener(user))
    }

    override fun showGoalLogInfos(goalInfo: GoalInfo, goalLogInfos: MutableList<GoalLogInfo>) {
        if (goalAdapter == null) {
            goalAdapter = GoalViewAdapter(goalInfo, goalLogInfos,
                    { id -> presenter.postLikeForGoalLog(id) },
                    { id, comment -> presenter.postCommentForGoalLog(id, comment) },
                    { id -> presenter.postLikeForGoal(id)})
            goalListView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            goalListView.adapter = goalAdapter
        } else {
            goalAdapter!!.appendGoalLogInfos(goalLogInfos)
        }

        goalSwipeRefresh?.isRefreshing = false
    }
}