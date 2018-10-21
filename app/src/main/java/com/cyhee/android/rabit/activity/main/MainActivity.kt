package com.cyhee.android.rabit.activity.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.*
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_complete_list.*
import kotlinx.android.synthetic.main.item_complete_mainwrite.*
import kotlinx.android.synthetic.main.item_complete_topbar.*


class MainActivity: AppCompatActivity(), MainContract.View {

    override var presenter : MainContract.Presenter = MainPresenter(this)
    private var mainAdapter: MainViewAdapter? = null

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.goalNames()
        presenter.mainInfos()

        // post goalLog
        mainWriteLayout.findViewById<Button>(R.id.postBtn).setOnClickListener {
            val selectedGoal = mainWriteLayout.findViewById<Spinner>(R.id.goalNameList).selectedItem as Goal
            val content = mainWriteLayout.findViewById<EditText>(R.id.dailyText).text.toString()
            val postedGoalLog = GoalLogFactory.Post(content)
            // TODO: 내용이 없경우 포스트 안되도록
            presenter.postGoaLog(selectedGoal.id, postedGoalLog)
        }

        myWallBtn.setOnClickListener(IntentListener.toMyWallListener(user))

        // swipe refresh
        swipeRefresh.setOnRefreshListener {
            Toast.makeText(this@MainActivity, "refreshed!", Toast.LENGTH_SHORT).show()

            mainAdapter?.clear()
            presenter.mainInfos()
        }
    }

    override fun showGoalNames(goals: MutableList<Goal>?) {
        if (goals == null) {
            val noGoal: Array<String> = arrayOf("새로운 토끼를 잡아보세요")
            val spinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, noGoal)
            goalNameList.adapter = spinnerAdapter
            goalNameList.setOnClickListener(IntentListener.toGoalWriteListener())
        } else {
            val spinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, goals)
            goalNameList.adapter = spinnerAdapter
        }
    }

    override fun showMainInfos(mainInfos: MutableList<MainInfo>) {
        if (mainAdapter == null) {
            mainAdapter = MainViewAdapter(mainInfos,
                    { id -> presenter.postLikeForGoal(id)},
                    { id -> presenter.postLikeForGoalLog(id)},
                    { id, comment: CommentFactory.Post -> presenter.postCommentForGoal(id, comment)},
                    { id, comment -> presenter.postCommentForGoalLog(id, comment)})
            mainInfoListLayout.findViewById<RecyclerView>(R.id.listView).addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            mainInfoListLayout.findViewById<RecyclerView>(R.id.listView).adapter = mainAdapter
        } else {
            mainAdapter!!.appendMainInfos(mainInfos)
        }

        swipeRefresh?.isRefreshing = false
    }
}