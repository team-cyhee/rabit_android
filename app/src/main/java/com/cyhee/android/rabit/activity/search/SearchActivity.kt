package com.cyhee.android.rabit.activity.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.item_complete_searchbar.*
import java.lang.Exception
import android.widget.TabHost
import android.widget.Toast
import kotlinx.android.synthetic.main.item_complete_searchtab.*

class SearchActivity: AppCompatActivity(), SearchContract.View {

    override var presenter : SearchContract.Presenter = SearchPresenter(this)
    private var searchAdapter: SearchViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        var text = ""
        var selectedTab = 0
        val searchTab = searchTab as TabHost
        searchTab.setup()

        var spec: TabHost.TabSpec = searchTab.newTabSpec("people")
        spec.setContent(R.id.userSearchSwipeRefresh)
        spec.setIndicator("people")
        searchTab.addTab(spec)

        spec = searchTab.newTabSpec("goal")
        spec.setContent(R.id.goalSearchSwipeRefresh)
        spec.setIndicator("goal")
        searchTab.addTab(spec)

        spec = searchTab.newTabSpec("goalLog")
        spec.setContent(R.id.goalLogSearchSwipeRefresh)
        spec.setIndicator("goalLog")
        searchTab.addTab(spec)

        searchTab.setOnTabChangedListener {
            selectedTab = searchTab.currentTab
            searchAdapter?.clear()
            searchAdapter = null
        }

        searchBtn.setOnClickListener {

            when (selectedTab) {
                0 -> {
                    text = searchText.text.toString()
                    presenter.searchUsers(text)
                }
                1 -> {
                    text = searchText.text.toString()
                    presenter.searchGoals(text)
                }
                2 -> {
                    text = searchText.text.toString()
                    presenter.searchGoalLogs(text)
                }
                else -> throw Exception("잘못된 탭 선택입니다.")
            }
        }

        prevBtn.setOnClickListener {
            Log.d("preBtn","clicked")
            finish()
        }

        userSearchSwipeRefresh.setOnRefreshListener {
            Toast.makeText(this@SearchActivity, "refreshed!", Toast.LENGTH_SHORT).show()

            searchAdapter?.clear()
            presenter.searchUsers(text)
        }

        goalSearchSwipeRefresh.setOnRefreshListener {
            Toast.makeText(this@SearchActivity, "refreshed!", Toast.LENGTH_SHORT).show()

            searchAdapter?.clear()
            presenter.searchGoals(text)
        }

        goalLogSearchSwipeRefresh.setOnRefreshListener {
            Toast.makeText(this@SearchActivity, "refreshed!", Toast.LENGTH_SHORT).show()

            searchAdapter?.clear()
            presenter.searchGoalLogs(text)
        }
    }

    override fun showUserResult(users: MutableList<User>) {
        var results: MutableList<SearchForm> = mutableListOf()
        for (user in users) {
            val result = SearchForm(user.id, ContentType.USER, user, user.username)
            results.add(result)
        }
        if (searchAdapter == null) {
            searchAdapter = SearchViewAdapter(results)
            userSearchListView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            userSearchListView.adapter = searchAdapter
        } else {
            searchAdapter!!.appendResults(results)
        }
        userSearchSwipeRefresh?.isRefreshing = false
    }

    override fun showGoalResult(goals: MutableList<Goal>) {
        var results: MutableList<SearchForm> = mutableListOf()
        for (goal in goals) {
            val result = SearchForm(goal.id, ContentType.GOAL, goal.author, goal.content)
            results.add(result)
        }
        if (searchAdapter == null) {
            searchAdapter = SearchViewAdapter(results)
            goalSearchListView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            goalSearchListView.adapter = searchAdapter
        } else {
            searchAdapter!!.appendResults(results)
        }
        goalSearchSwipeRefresh?.isRefreshing = false
    }

    override fun showGoalLogResult(goalLogs: MutableList<GoalLog>) {
        var results: MutableList<SearchForm> = ArrayList()
        for (goalLog in goalLogs) {
            val result = SearchForm(goalLog.id, ContentType.GOALLOG, goalLog.goal.author, goalLog.content)
            results.add(result)
        }
        if (searchAdapter == null) {
            searchAdapter = SearchViewAdapter(results)
            goalLogSearchListView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            goalLogSearchListView.adapter = searchAdapter
        } else {
            searchAdapter!!.appendResults(results)
        }
        goalLogSearchSwipeRefresh?.isRefreshing = false
    }
}