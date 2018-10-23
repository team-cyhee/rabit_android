package com.cyhee.android.rabit.activity.goallist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.activity.goal.GoalViewAdapter
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_goallist.*
import kotlinx.android.synthetic.main.item_complete_list.*
import kotlinx.android.synthetic.main.item_complete_prevtopbar.*


class GoalListActivity: AppCompatActivity(), GoalListContract.View {

    override var presenter : GoalListContract.Presenter = GoalListPresenter(this)
    private var goalViewAdapter: GoalViewAdapter? = null

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goallist)

        if (intent.hasExtra("username")) {
            val username = intent.getStringExtra("username")
            presenter.userGoalInfos(username)

            // swipe refresh
            swipeRefresh.setOnRefreshListener {
                Toast.makeText(this@GoalListActivity, "refreshed!", Toast.LENGTH_SHORT).show()

                goalViewAdapter?.clear()
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
        if (goalViewAdapter == null) {
            goalViewAdapter = GoalViewAdapter(goalInfos,
                    { id -> presenter.postLikeForGoal(id)},
                    { id, comment: CommentFactory.Post -> presenter.postCommentForGoal(id, comment)})
            goalListLayout.findViewById<RecyclerView>(R.id.listView).addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            goalListLayout.findViewById<RecyclerView>(R.id.listView).adapter = goalViewAdapter
        } else {
            goalViewAdapter!!.appendGoalInfos(goalInfos)
        }
    }

}