package com.cyhee.android.rabit.activity.goallog

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
import kotlinx.android.synthetic.main.item_complete_fullgoallog.*
import kotlinx.android.synthetic.main.item_complete_list.*
import kotlinx.android.synthetic.main.item_complete_prevtopbar.*
import kotlinx.android.synthetic.main.item_part_goalwriter.*
import kotlinx.android.synthetic.main.item_part_reaction.*
import kotlinx.android.synthetic.main.item_part_text.*


class GoalLogActivity: AppCompatActivity(), GoalLogContract.View {
    override var presenter : GoalLogContract.Presenter = GoalLogPresenter(this)
    private var goalLogAdapter: GoalLogViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goallog)

        if (intent.hasExtra("goalLogId")) {
            val goalLogId = intent.getLongExtra("goalLogId", -1)
            presenter.goalLogInfo(goalLogId)
        } else {
            Toast.makeText(this, "전달된 goalLog 아이디가 없습니다", Toast.LENGTH_SHORT).show()
        }

        prevBtn.setOnClickListener {
            Log.d("preBtn","clicked")
            finish()
        }
    }

    override fun showGoalLogInfo(goalLogInfo : GoalLogInfo) {
        nameText.text = goalLogInfo.goal.author.username
        titleText.text = goalLogInfo.goal.content
        if (goalLogInfo.goal.parent != null) {
            // 사람 수 추가
            val original = "${goalLogInfo.goal.parent!!.author.username} 님 외 n명이 함께하는 중"
            originalWriterText.text = original
        }
        likeNumberText.text = goalLogInfo.likeNum.toString()
        commentNumberText.text = goalLogInfo.commentNum.toString()

        text.text = goalLogInfo.content

        likeButton.setOnClickListener {
            presenter.postLikeForGoalLog(goalLogInfo.id)
        }

        commentWriteLayout.findViewById<Button>(R.id.postBtn).setOnClickListener {
            val content = commentWriteLayout.findViewById<EditText>(R.id.commentText).text.toString()
            // TODO: 내용이 없을 경우 포스트 안되도록
            val postedComment = CommentFactory.Post(content)

            presenter.postCommentForGoalLog(goalLogInfo.id, postedComment)
        }
    }

    override fun showComments(comments: MutableList<Comment>) {
        if (goalLogAdapter == null) {
            goalLogAdapter = GoalLogViewAdapter(comments)
            listView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            listView.adapter = goalLogAdapter
        } else {
            goalLogAdapter!!.appendGoalLogs(comments)
        }
    }
}