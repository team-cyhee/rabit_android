package com.cyhee.android.rabit.activity.wall

import android.annotation.SuppressLint
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
import kotlinx.android.synthetic.main.activity_wall.*
import kotlinx.android.synthetic.main.item_complete_hometopbar.*
import kotlinx.android.synthetic.main.item_complete_list.*
import kotlinx.android.synthetic.main.item_complete_wall.*


class WallActivity: AppCompatActivity(), WallContract.View {
    override var presenter : WallContract.Presenter = WallPresenter(this)
    private var mainAdapter: MainViewAdapter? = null

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wall)

        if (intent.hasExtra("username")) {
            val username = intent.getStringExtra("username")
            presenter.wallInfo(username)
            presenter.userMainInfos(username)

            // TODO: 팔로우 - 팔로우 취소 전환
            followBtn.setOnClickListener {
                presenter.postFollow(username)
            }
            followingText.setOnClickListener(IntentListener.toFollowingListListener(username))
            followerText.setOnClickListener(IntentListener.toFollowerListListener(username))
            goalsText.setOnClickListener(IntentListener.toGoalListListener(username))

            // swipe refresh
            swipeRefresh.setOnRefreshListener {
                Toast.makeText(this@WallActivity, "refreshed!", Toast.LENGTH_SHORT).show()

                mainAdapter?.clear()
                presenter.wallInfo(username)
                presenter.userMainInfos(username)
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

    @SuppressLint("SetTextI18n")
    override fun showWallInfo(wallInfo: WallInfo) {
        usernameText.text = wallInfo.username
        nameText.text = wallInfo.username
        followingText.text = "${wallInfo.followeeNum} 팔로잉"
        followerText.text = "${wallInfo.followerNum} 팔로워"
        goal1Text.text = wallInfo.goalContents[0]
        goal2Text.text = wallInfo.goalContents[1]
        goal3Text.text = wallInfo.goalContents[2]
    }

    override fun showMainInfos(mainInfos : MutableList<MainInfo>) {
        if (mainAdapter == null) {
            mainAdapter = MainViewAdapter(mainInfos,
                    { id -> presenter.postLikeForGoal(id)},
                    { id -> presenter.postLikeForGoalLog(id)},
                    { id, comment: CommentFactory.Post -> presenter.postCommentForGoal(id, comment)},
                    { id, comment -> presenter.postCommentForGoalLog(id, comment)})
            oneInfoListLayout.findViewById<RecyclerView>(R.id.listView).addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            oneInfoListLayout.findViewById<RecyclerView>(R.id.listView).adapter = mainAdapter
        } else {
            mainAdapter!!.appendMainInfos(mainInfos)
        }

        swipeRefresh?.isRefreshing = false
    }
}