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
import com.cyhee.android.rabit.activity.wall.WallContract
import com.cyhee.android.rabit.activity.wall.WallPresenter
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
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
        if (wallInfo.goalContents.isNotEmpty())
            goal1Text.text = wallInfo.goalContents[0]
        if (wallInfo.goalContents.size > 1)
            goal2Text.text = wallInfo.goalContents[1]
        if (wallInfo.goalContents.size > 2)
            goal3Text.text = wallInfo.goalContents[2]
    }

    override fun showMainInfos(mainInfos : MutableList<MainInfo>) {
        if (mainAdapter == null) {
            mainAdapter = MainViewAdapter(mainInfos,
                    { id, post -> presenter.toggleLikeForGoal(id, post)},
                    { id, post -> presenter.toggleLikeForGoalLog(id, post)},
                    { id, comment: CommentFactory.Post -> presenter.postCommentForGoal(id, comment)},
                    { id, comment -> presenter.postCommentForGoalLog(id, comment)})
            myInfoListLayout.findViewById<RecyclerView>(R.id.listView).addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            myInfoListLayout.findViewById<RecyclerView>(R.id.listView).adapter = mainAdapter
        } else {
            mainAdapter!!.appendMainInfos(mainInfos)
        }

        swipeRefresh?.isRefreshing = false
    }

    fun toggleLike(on : Boolean) {
        if(on)
            likeButton.background = if(Build.VERSION.SDK_INT >= 21)
                likeButton.context.getDrawable(R.drawable.thumb_active)
            else
                likeButton.context.resources.getDrawable(R.drawable.thumb_active)
        else
            likeButton.background = if(Build.VERSION.SDK_INT >= 21)
                likeButton.context.getDrawable(R.drawable.thumb)
            else
                likeButton.context.resources.getDrawable(R.drawable.thumb)
    }
}