package com.cyhee.android.rabit.activity.wall

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.main.MainViewAdapter
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.item_complete_hometopbar.*

class WallActivity: AppCompatActivity(), WallContract.View {
    override var presenter : WallContract.Presenter = WallPresenter(this)
    private var mainAdapter: MainViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wall)

        if (intent.hasExtra("username")) {
            val username = intent.getStringExtra("username")
            presenter.wallInfo(username)
            usernameText.text = username

            // swipe refresh
            wallSwipeRefresh.setOnRefreshListener {
                Toast.makeText(this@WallActivity, "refreshed!", Toast.LENGTH_SHORT).show()

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
            mainAdapter = MainViewAdapter(2, mainInfos, wallInfo,
                    { id, post -> presenter.toggleLikeForGoal(id, post)},
                    { id, post -> presenter.toggleLikeForGoalLog(id, post)},
                    { id, comment: CommentFactory.Post -> presenter.postCommentForGoal(id, comment)},
                    { id, comment -> presenter.postCommentForGoalLog(id, comment)},
                    { followee: String -> presenter.postFollow(followee)})
            wallListView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            wallListView.adapter = mainAdapter
        } else {
            mainAdapter!!.appendMainInfos(mainInfos)
        }

        wallSwipeRefresh?.isRefreshing = false
    }
}