package com.cyhee.android.rabit.activity.mywall

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.activity.main.MainViewAdapter
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_mywall.*
import kotlinx.android.synthetic.main.item_complete_hometopbar.*
import kotlinx.android.synthetic.main.item_complete_list.*
import kotlinx.android.synthetic.main.item_complete_mywall.*
import kotlinx.android.synthetic.main.item_part_reaction.*


class MyWallActivity: AppCompatActivity(), MyWallContract.View {
    override var presenter : MyWallContract.Presenter = MyWallPresenter(this)
    private var mainAdapter: MainViewAdapter? = null

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mywall)

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

        prevBtn.setOnClickListener {
            Log.d("preBtn","clicked")
            finish()
        }
        mainBtn.setOnClickListener(IntentListener.toMainListener())
    }

    override fun showMainInfos(mainInfos : MutableList<MainInfo>, wallInfo: WallInfo) {
        if (mainAdapter == null) {
            mainAdapter = MainViewAdapter(1, mainInfos, wallInfo,
                    { id, post -> presenter.toggleLikeForGoal(id, post)},
                    { id, post -> presenter.toggleLikeForGoalLog(id, post)},
                    { id, comment: CommentFactory.Post -> presenter.postCommentForGoal(id, comment)},
                    { id, comment -> presenter.postCommentForGoalLog(id, comment)},
                    { followee: String -> })
            myWallListView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            myWallListView.adapter = mainAdapter
        } else {
            mainAdapter!!.appendMainInfos(mainInfos)
        }

        myWallSwipeRefresh?.isRefreshing = false
    }
}