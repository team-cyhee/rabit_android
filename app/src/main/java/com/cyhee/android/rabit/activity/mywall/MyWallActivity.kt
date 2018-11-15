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
import com.cyhee.android.rabit.activity.goallist.GoalListActivity
import com.cyhee.android.rabit.activity.main.MainActivity
import com.cyhee.android.rabit.activity.main.MainViewAdapter
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_mywall.*
import kotlinx.android.synthetic.main.item_complete_hometopbar.*


class MyWallActivity: AppCompatActivity(), MyWallContract.View {

    override var presenter : MyWallContract.Presenter = MyWallPresenter(this)
    private var mainAdapter: MainViewAdapter? = null

    private val TAG = MyWallActivity::class.qualifiedName
    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mywall)
        bottomBar.selectTabAtPosition(1)

        if (intent.hasExtra("username")) {
            val username = intent.getStringExtra("username")
            presenter.wallInfo(username)
            usernameText.text = username

            // swipe refresh
            myWallSwipeRefresh.setOnRefreshListener {
                Toast.makeText(this@MyWallActivity, "refreshed!", Toast.LENGTH_SHORT).show()

                mainAdapter?.clear()
                presenter.wallInfo(username)
            }
        } else {
            Toast.makeText(this, "전달된 username이 없습니다", Toast.LENGTH_SHORT).show()
        }

        bottomBar.setOnTabSelectListener { tabId ->
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
            }
        }

        prevBtn.setOnClickListener {
            Log.d("preBtn","clicked")
            finish()
        }
    }

    override fun showMainInfos(mainInfos : MutableList<MainInfo>, wallInfo: WallInfo) {
        if (mainAdapter == null) {
            mainAdapter = MainViewAdapter(1, mainInfos, wallInfo,
                    { id, post -> presenter.toggleLikeForGoal(id, post)},
                    { id, post -> presenter.toggleLikeForGoalLog(id, post)},
                    { followee: String -> })
            myWallListView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            myWallListView.adapter = mainAdapter
        } else {
            mainAdapter!!.appendMainInfos(mainInfos)
        }

        myWallSwipeRefresh?.isRefreshing = false
    }

    fun toggleLike(id: Long, type: ContentType, boolean: Boolean) {
        Log.d(TAG, "toggle like $id, $type, $boolean")
        mainAdapter!!.toggleLike(id, type, boolean)
    }
}