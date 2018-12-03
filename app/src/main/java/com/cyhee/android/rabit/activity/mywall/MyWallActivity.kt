package com.cyhee.android.rabit.activity.mywall

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.activity.base.InfiniteScrollListener
import com.cyhee.android.rabit.activity.goallist.GoalListActivity
import com.cyhee.android.rabit.activity.main.MainActivity
import com.cyhee.android.rabit.activity.main.MainViewAdapter
import com.cyhee.android.rabit.activity.settings.SettingsActivity
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_mywall.*
import kotlinx.android.synthetic.main.item_complete_usertopbar.*


class MyWallActivity: AppCompatActivity(), MyWallContract.View {

    override var presenter : MyWallContract.Presenter = MyWallPresenter(this)
    private var mainAdapter: MainViewAdapter? = null

    private val TAG = MyWallActivity::class.qualifiedName
    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mywall)
        bottom_bar.selectTabAtPosition(1)

        if (intent.hasExtra("username")) {
            val username = intent.getStringExtra("username")
            presenter.wallInfo(username)
            username_text.text = username
            val layoutManager = LinearLayoutManager(this)
            my_wall_list_view.layoutManager = layoutManager

            search_btn.setOnClickListener(IntentListener.toSearchListener())
            to_up_btn.setOnClickListener{
                my_wall_list_view.smoothScrollToPosition(0)
            }

            // swipe refresh
            my_wall_swipe_refresh.setOnRefreshListener {
                Toast.makeText(this@MyWallActivity, "refreshed!", Toast.LENGTH_SHORT).show()

                mainAdapter?.clear()
                presenter.wallInfo(username)

                my_wall_swipe_refresh?.isRefreshing = false
            }

            // infinite scroll
            my_wall_list_view.addOnScrollListener(InfiniteScrollListener(layoutManager) {
                presenter.userMainInfos(user, null,  mainAdapter!!.lastTime())
            })
        } else {
            Toast.makeText(this, "전달된 username이 없습니다", Toast.LENGTH_SHORT).show()
        }

        bottom_bar.setOnTabSelectListener { tabId ->
            when (tabId) {
                R.id.tabHome -> {
                    val intentToMain = Intent(this, MainActivity:: class.java)
                    intentToMain.putExtra("username", user)
                    startActivity(intentToMain)
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
    }

    override fun showMainInfos(mainInfos : MutableList<MainInfo>, wallInfo: WallInfo?) {
        if (mainAdapter == null) {
            mainAdapter = MainViewAdapter(1, mainInfos, wallInfo,
                    { id, post -> presenter.toggleLikeForGoal(id, post)},
                    { id, post -> presenter.toggleLikeForGoalLog(id, post)},
                    { followee: String -> },
                    { id -> presenter.deleteGoal(id)},
                    { id -> presenter.deleteGoalLog(id)},
                    { type, id, reportType -> presenter.report(type, id, reportType)})
            my_wall_list_view.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            my_wall_list_view.adapter = mainAdapter
        } else {
            mainAdapter!!.appendMainInfos(mainInfos)
        }
    }

    fun toggleLike(id: Long, type: ContentType, boolean: Boolean) {
        Log.d(TAG, "toggle like $id, $type, $boolean")
        mainAdapter!!.toggleLike(id, type, boolean)
    }
}