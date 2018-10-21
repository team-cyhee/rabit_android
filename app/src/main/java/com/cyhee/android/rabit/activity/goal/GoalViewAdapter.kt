package com.cyhee.android.rabit.activity.goal

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.App
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.item_complete_maingoal.*
import kotlinx.android.synthetic.main.item_part_goalwriter.*
import kotlinx.android.synthetic.main.item_part_reaction.*

class GoalViewAdapter (
    private val goalInfos: MutableList<GoalInfo>,
    private val sendLikeForGoal: (Long) -> Unit,
    private val sendCommentForGoal: (Long, CommentFactory.Post) -> Unit
) : RecyclerView.Adapter<GoalViewHolder>() {

    private val user = App.prefs.user

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder = GoalViewHolder(parent)

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        goalInfos[position].let { goalInfo ->
            with(holder) {
                nameText.text = goalInfo.author.username
                titleText.text = goalInfo.content

                // 함께하는 사람, 시작하는 날, 로그 수, 좋아요 수, 댓글 수
                companionText.text = when {
                    goalInfo.parent != null -> "${goalInfo.parent!!.author.username} 님 외 ${goalInfo.companionNum}명이 함께하는 중"
                    goalInfo.companionNum != 0 -> "${goalInfo.companionNum}명이 함께하는 중"
                    else -> "함께 해보세요!"
                }
                startDateText.text = "시작일 ${goalInfo.startDate}"
                endDateText.text = when {
                    goalInfo.endDate != null -> "종료일 ${goalInfo.endDate}"
                    else -> "종료일 없음"
                }
                logNumText.text = goalInfo.logNum.toString()

                when (user) {
                    goalInfo.author.username -> goalBtn.text = "당근먹기"
                    else -> goalBtn.text = "함께하기"
                }

                likeNumberText.text = goalInfo.likeNum.toString()
                commentNumberText.text = goalInfo.commentNum.toString()

                // 댓글 2개
                if (goalInfo.comments.content.size > 0) {
                    commentGoalLayout1.findViewById<TextView>(R.id.commentWriterText).text = goalInfo.comments.content[0].author.username
                    commentGoalLayout1.findViewById<TextView>(R.id.commentText).text = goalInfo.comments.content[0].content
                    commentGoalLayout1.visibility = View.VISIBLE
                } else {
                    commentGoalLayout1.visibility = View.GONE
                }

                if (goalInfo.comments.content.size > 1) {
                    commentGoalLayout2.findViewById<TextView>(R.id.commentWriterText).text = goalInfo.comments.content[1].author.username
                    commentGoalLayout2.findViewById<TextView>(R.id.commentText).text = goalInfo.comments.content[1].content
                    commentGoalLayout2.visibility = View.VISIBLE
                } else {
                    commentGoalLayout2.visibility = View.GONE
                }


                titleText.setOnClickListener(IntentListener.toGoalListener(goalInfo.id))
                logNum.setOnClickListener(IntentListener.toGoalListener(goalInfo.id))
                commentNumberText.setOnClickListener(IntentListener.toGoalListener(goalInfo.id))
                commentGoalLayout1.setOnClickListener(IntentListener.toGoalListener(goalInfo.id))
                commentGoalLayout2.setOnClickListener(IntentListener.toGoalListener(goalInfo.id))
                likeNumberText.setOnClickListener(IntentListener.toGoalLikeListListener(goalInfo.id))

                when {
                    goalInfo.parent != null -> companionText.setOnClickListener(IntentListener.toGoalListener(goalInfo.parent!!.id))
                    else -> companionText.setOnClickListener(IntentListener.toGoalListener(goalInfo.id))
                }

                // 함께하기 /
                when (user) {
                    // TODO: 이미 companion이면 버튼 안보이게
                    goalInfo.author.username -> goalBtn.setOnClickListener(IntentListener.toGoalLogWriteListener(goalInfo.id))
                    else -> goalBtn.setOnClickListener(IntentListener.toCompanionWriteListener(goalInfo.id))
                }

                // post like
                likeButton.setOnClickListener {
                    sendLikeForGoal(goalInfo.id)
                }

                commentGoalWriteLayout.findViewById<EditText>(R.id.commentText).text.clear()

                // post comment
                commentGoalWriteLayout.findViewById<Button>(R.id.postBtn).setOnClickListener {
                    val content = commentGoalWriteLayout.findViewById<EditText>(R.id.commentText).text.toString()
                    // TODO: 내용이 없을 경우 포스트 안되도록
                    val postedComment = CommentFactory.Post(content)

                    sendCommentForGoal(goalInfo.id, postedComment)
                }

                Log.d("ViewHolder", goalInfo.toString())
            }
        }
    }

    override fun getItemCount(): Int = goalInfos.size

    fun appendGoalInfos(moreGoalInfos: List<GoalInfo>) {
        val index = this.goalInfos.size
        goalInfos.addAll(moreGoalInfos)
        notifyItemRangeInserted(index, goalInfos.size)
    }
}
