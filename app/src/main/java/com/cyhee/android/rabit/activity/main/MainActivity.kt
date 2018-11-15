package com.cyhee.android.rabit.activity.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.*
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.activity.goallist.GoalListActivity
import com.cyhee.android.rabit.activity.mywall.MyWallActivity
import com.cyhee.android.rabit.activity.settings.SettingsActivity
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_complete_topbar.*


class MainActivity: AppCompatActivity(), MainContract.View {

    override var presenter : MainContract.Presenter = MainPresenter(this)
    private var mainAdapter: MainViewAdapter? = null

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.mainInfos()

        val linearLayoutManager = LinearLayoutManager(this)
        mainListView.layoutManager = linearLayoutManager

        searchBtn.setOnClickListener(IntentListener.toSearchListener())

        bottomBar.setOnTabSelectListener { tabId ->
            when (tabId) {
                R.id.tabWall -> {
                    val intentToMyWall = Intent(this, MyWallActivity:: class.java)
                    intentToMyWall.putExtra("username", user)
                    startActivity(intentToMyWall)
                }
                R.id.tabGoal -> {
                    val intentToGoalList = Intent(this, GoalListActivity:: class.java)
                    intentToGoalList.putExtra("username", user)
                    startActivity(intentToGoalList)
                }
                R.id.tabSetting -> {
                    val intentToSettings = Intent(this, SettingsActivity::class.java)
                    startActivity(intentToSettings)
                }
            }
        }

        // swipe refresh
        mainSwipeRefresh.setOnRefreshListener {
            Toast.makeText(this@MainActivity, "refreshed!", Toast.LENGTH_SHORT).show()

            mainAdapter?.clear()
            presenter.mainInfos()
        }
    }

    override fun showMainInfos(mainInfos: MutableList<MainInfo>) {
        if (mainAdapter == null) {
            mainAdapter = MainViewAdapter(0, mainInfos, null,
                    { id, post -> presenter.toggleLikeForGoal(id, post)},
                    { id, post -> presenter.toggleLikeForGoalLog(id, post)},
                    { followee: String -> })
            mainListView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            mainListView.adapter = mainAdapter
        } else {
            mainAdapter!!.appendMainInfos(mainInfos)
        }

        mainSwipeRefresh?.isRefreshing = false
    }
}