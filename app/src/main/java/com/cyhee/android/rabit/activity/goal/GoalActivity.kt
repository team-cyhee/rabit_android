package com.cyhee.android.rabit.activity.goal

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.activity.comment.CommentViewAdapter
import com.cyhee.android.rabit.activity.goallog.GoalLogViewAdapter
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.activity_goal.*
import kotlinx.android.synthetic.main.item_complete_fullgoal.*
import kotlinx.android.synthetic.main.item_complete_prevtopbar.*
import kotlinx.android.synthetic.main.item_part_goalwriter.*
import kotlinx.android.synthetic.main.item_part_reaction.*
import java.text.SimpleDateFormat


class GoalActivity: AppCompatActivity(), GoalContract.View {
    override var presenter : GoalContract.Presenter = GoalPresenter(this)
    private var commentAdapter: CommentViewAdapter? = null
    private var goalLogAdapter: GoalLogViewAdapter? = null

    private val user = App.prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)

        if (intent.hasExtra("goalId")) {
            val goalId = intent.getLongExtra("goalId", -1)
            presenter.goalInfos(goalId)
            presenter.goalStoreGoalLogs(goalId)

            likeNumberText.setOnClickListener(IntentListener.toGoalLikeListListener(goalId))

        } else {
            Toast.makeText(this, "전달된 goal 아이디가 없습니다", Toast.LENGTH_SHORT).show()
        }

        prevBtn.setOnClickListener {
            Log.d("preBtn","clicked")
            finish()
        }

        myWallBtn.setOnClickListener(IntentListener.toMyWallListener(user))
    }

    override fun showGoalInfo(goalInfo : GoalInfo) {
        nameText.text = goalInfo.author.username
        titleText.text = goalInfo.content

        // 함께하는 사람, 시작하는 날, 로그 수, 좋아요 수, 댓글 수
        companionText.text = when {
            goalInfo.parent != null -> "${goalInfo.parent!!.author.username} 님 외 ${goalInfo.companionNum}명이 함께하는 중"
            goalInfo.companionNum != 0 -> "${goalInfo.companionNum}명이 함께하는 중"
            else -> "함께 해보세요!"
        }
        startDateText.text = when {
            goalInfo.startDate != null -> "시작일 ${SimpleDateFormat("dd/MM/yyyy").format(goalInfo.startDate)}"
            else -> "시작일 없음"
        }
        endDateText.text = when {
            goalInfo.endDate != null -> "종료일 ${SimpleDateFormat("dd/MM/yyyy").format(goalInfo.endDate)}"
            else -> "종료일 없음"
        }
        logNumText.text = goalInfo.logNum.toString()

        when (user) {
            goalInfo.author.username -> goalBtn.text = "당근먹기"
            else -> goalBtn.text = "함께하기"
        }
        likeNumberText.text = goalInfo.likeNum.toString()
        commentNumberText.text = goalInfo.commentNum.toString()

        likeNumberText.setOnClickListener(IntentListener.toGoalLikeListListener(goalInfo.id))

        when {
            goalInfo.parent != null -> companionText.setOnClickListener(IntentListener.toGoalListener(goalInfo.parent!!.id))
            else -> companionText.setOnClickListener(IntentListener.toGoalListener(goalInfo.id))
        }

        when (user) {
            // TODO: 이미 companion이면 버튼 안보이게
            goalInfo.author.username -> goalBtn.setOnClickListener(IntentListener.toGoalLogWriteListener(goalInfo.id, goalInfo.content))
            else -> goalBtn.setOnClickListener(IntentListener.toCompanionWriteListener(goalInfo.id, goalInfo.content))
        }

        val isMy = user == goalInfo.author.username
        nameText.setOnClickListener(IntentListener.toWhichWallListListener(isMy, goalInfo.author.username))

        toggleLike(goalInfo.liked)
        likeButton.setOnClickListener {
            goalInfo.liked = !goalInfo.liked
            presenter.toggleLikeForGoal(goalInfo.id, goalInfo.liked)
        }

        commentWriteLayout.findViewById<Button>(R.id.postBtn).setOnClickListener {
            val content = commentWriteLayout.findViewById<EditText>(R.id.commentText).text.toString()
            // TODO: 내용이 없을 경우 포스트 안되도록
            val postedComment = CommentFactory.Post(content)

            presenter.postCommentForGoal(goalInfo.id, postedComment)
        }
    }

    override fun showComments(comments: MutableList<Comment>) {
        if (commentAdapter == null) {
            commentAdapter = CommentViewAdapter(comments)
            commentListLayout.findViewById<RecyclerView>(R.id.listView).addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            commentListLayout.findViewById<RecyclerView>(R.id.listView).adapter = commentAdapter
        } else {
            commentAdapter!!.appendComments(comments)
        }
    }

    override fun showGoalLogInfos(goalLogInfos: MutableList<GoalLogInfo>) {
        if (goalLogAdapter == null) {
            goalLogAdapter = GoalLogViewAdapter(goalLogInfos,
                    { id, post -> presenter.toggleLikeForGoalLog(id, post)},
                    { id, comment -> presenter.postCommentForGoalLog(id, comment)})
            goalLogListLayout.findViewById<RecyclerView>(R.id.listView).addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            goalLogListLayout.findViewById<RecyclerView>(R.id.listView).adapter = goalLogAdapter
        } else {
            goalLogAdapter!!.appendGoalLogInfos(goalLogInfos)
        }
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