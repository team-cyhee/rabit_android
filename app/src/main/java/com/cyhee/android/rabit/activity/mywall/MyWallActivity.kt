package com.cyhee.android.rabit.activity.mywall

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
import com.cyhee.android.rabit.activity.wall.WallContract
import com.cyhee.android.rabit.activity.wall.WallPresenter
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_mywall.*
import kotlinx.android.synthetic.main.item_complete_hometopbar.*
import kotlinx.android.synthetic.main.item_complete_list.*
import kotlinx.android.synthetic.main.item_complete_mywall.*


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
            presenter.userMainInfos(username)

            editInfoBtn.setOnClickListener(IntentListener.toInfoEditListener(username))
            postGoalBtn.setOnClickListener(IntentListener.toGoalWriteListener())
            postGoalLogBtn.setOnClickListener(IntentListener.toGoalLogWriteListener())
            followingText.setOnClickListener(IntentListener.toFollowingListListener(user))
            followerText.setOnClickListener(IntentListener.toFollowerListListener(user))
            goalsText.setOnClickListener(IntentListener.toGoalListListener(user))
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
            myInfoListLayout.findViewById<RecyclerView>(R.id.listView).addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            myInfoListLayout.findViewById<RecyclerView>(R.id.listView).adapter = mainAdapter
        } else {
            mainAdapter!!.appendMainInfos(mainInfos)
        }

        swipeRefresh?.isRefreshing = false
    }
}