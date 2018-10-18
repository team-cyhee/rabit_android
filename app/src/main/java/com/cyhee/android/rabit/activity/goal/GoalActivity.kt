package com.cyhee.android.rabit.activity.goal

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.item_complete_fullgoal.*
import kotlinx.android.synthetic.main.item_complete_list.*
import kotlinx.android.synthetic.main.item_complete_prevtopbar.*
import kotlinx.android.synthetic.main.item_part_goalwriter.*
import kotlinx.android.synthetic.main.item_part_reaction.*


class GoalActivity: AppCompatActivity(), GoalContract.View {
    override var presenter : GoalContract.Presenter = GoalPresenter(this)
    private var goalAdapter: GoalViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)

        if (intent.hasExtra("goalId")) {
            val goalId = intent.getLongExtra("goalId", -1)
            presenter.goalInfo(goalId)
        } else {
            Toast.makeText(this, "전달된 goal 아이디가 없습니다", Toast.LENGTH_SHORT).show()
        }

        prevBtn.setOnClickListener {
            Log.d("preBtn","clicked")
            finish()
        }
    }

    override fun showGoalInfo(goalInfo : GoalInfo) {
        nameText.text = goalInfo.author.username
        titleText.text = goalInfo.content
        if (goalInfo.parent != null) {
            // 사람 수 추가
            val original = "${goalInfo.parent!!.author.username} 님 외 n명이 함께하는 중"
            originalWriterText.text = original
        }
        likeNumberText.text = goalInfo.likeNum.toString()
        commentNumberText.text = goalInfo.commentNum.toString()

        likeButton.setOnClickListener {
            presenter.postLikeForGoal(goalInfo.id)
        }

        commentWriteLayout.findViewById<Button>(R.id.postBtn).setOnClickListener {
            val content = commentWriteLayout.findViewById<EditText>(R.id.commentText).text.toString()
            // TODO: 내용이 없을 경우 포스트 안되도록
            val postedComment = CommentFactory.Post(content)

            presenter.postCommentForGoal(goalInfo.id, postedComment)
        }
    }

    override fun showComments(comments: MutableList<Comment>) {
        if (goalAdapter == null) {
            goalAdapter = GoalViewAdapter(comments)
            listView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            listView.adapter = goalAdapter
        } else {
            goalAdapter!!.appendComments(comments)
        }
    }
}