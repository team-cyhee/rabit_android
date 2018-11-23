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
        val searchTab = search_tab as TabHost
        searchTab.setup()

        var spec: TabHost.TabSpec = searchTab.newTabSpec("people")
        spec.setContent(R.id.user_search_swipe_refresh)
        spec.setIndicator("people")
        searchTab.addTab(spec)

        spec = searchTab.newTabSpec("goal")
        spec.setContent(R.id.goal_search_swipe_refresh)
        spec.setIndicator("goal")
        searchTab.addTab(spec)

        spec = searchTab.newTabSpec("goalLog")
        spec.setContent(R.id.goal_log_search_swipe_refresh)
        spec.setIndicator("goalLog")
        searchTab.addTab(spec)

        selectedTab = searchTab.currentTab
        search_btn.performClick()

        searchTab.setOnTabChangedListener {
            selectedTab = searchTab.currentTab

            searchAdapter?.clear()
            searchAdapter = null
            search_btn.performClick()
        }

        search_btn.setOnClickListener {

            when (selectedTab) {
                0 -> {
                    text = search_text.text.toString()
                    presenter.searchUsers(text)
                }
                1 -> {
                    text = search_text.text.toString()
                    presenter.searchGoals(text)
                }
                2 -> {
                    text = search_text.text.toString()
                    presenter.searchGoalLogs(text)
                }
                else -> throw Exception("잘못된 탭 선택입니다.")
            }
        }

        user_search_swipe_refresh.setOnRefreshListener {
            Toast.makeText(this@SearchActivity, "refreshed!", Toast.LENGTH_SHORT).show()

            searchAdapter?.clear()
            presenter.searchUsers(text)
            user_search_swipe_refresh?.isRefreshing = false
        }

        goal_search_swipe_refresh.setOnRefreshListener {
            Toast.makeText(this@SearchActivity, "refreshed!", Toast.LENGTH_SHORT).show()

            searchAdapter?.clear()
            presenter.searchGoals(text)
            goal_search_swipe_refresh?.isRefreshing = false
        }

        goal_log_search_swipe_refresh.setOnRefreshListener {
            Toast.makeText(this@SearchActivity, "refreshed!", Toast.LENGTH_SHORT).show()

            searchAdapter?.clear()
            presenter.searchGoalLogs(text)
            goal_log_search_swipe_refresh?.isRefreshing = false
        }
    }

    override fun showUserResult(users: MutableList<User>) {
        var results: MutableList<SearchForm> = mutableListOf()
        for (user in users) {
            val result = SearchForm(user.id, ContentType.USER, user, user.username)
            results.add(result)
        }
        if (searchAdapter == null) {
            searchAdapter = SearchViewAdapter(0, results)
            user_search_list_view.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            user_search_list_view.adapter = searchAdapter
        } else {
            searchAdapter!!.appendResults(results)
        }
    }

    override fun showGoalResult(goals: MutableList<Goal>) {
        var results: MutableList<SearchForm> = mutableListOf()
        for (goal in goals) {
            val result = SearchForm(goal.id, ContentType.GOAL, goal.author, goal.content)
            results.add(result)
        }
        if (searchAdapter == null) {
            searchAdapter = SearchViewAdapter(1, results)
            goal_search_list_view.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            goal_search_list_view.adapter = searchAdapter
        } else {
            searchAdapter!!.appendResults(results)
        }
    }

    override fun showGoalLogResult(goalLogs: MutableList<GoalLog>) {
        var results: MutableList<SearchForm> = ArrayList()
        for (goalLog in goalLogs) {
            val result = SearchForm(goalLog.id, ContentType.GOALLOG, goalLog.goal.author, goalLog.content)
            results.add(result)
        }
        if (searchAdapter == null) {
            searchAdapter = SearchViewAdapter(2, results)
            goal_log_search_list_view.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            goal_log_search_list_view.adapter = searchAdapter
        } else {
            searchAdapter!!.appendResults(results)
        }
    }
}