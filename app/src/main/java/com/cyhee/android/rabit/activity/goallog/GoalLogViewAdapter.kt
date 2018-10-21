package com.cyhee.android.rabit.activity.goallog

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import com.cyhee.android.rabit.useful.Fun
import kotlinx.android.synthetic.main.item_complete_maingoallog.*
import kotlinx.android.synthetic.main.item_part_goalwriter.*
import kotlinx.android.synthetic.main.item_part_reaction.*
import kotlinx.android.synthetic.main.item_part_text.*

class GoalLogViewAdapter (
    private val goalLogInfos: MutableList<GoalLogInfo>,
    private val sendLikeForGoalLog: (Long) -> Unit,
    private val sendCommentForGoalLog: (Long, CommentFactory.Post) -> Unit
) : RecyclerView.Adapter<GoalLogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalLogViewHolder = GoalLogViewHolder(parent)

    override fun onBindViewHolder(holder: GoalLogViewHolder, position: Int) {
        goalLogInfos[position].let { goalLogInfo ->
            with(holder) {
                nameText.text = goalLogInfo.goal.author.username
                val goalTitle = goalLogInfo.goal.content + Fun.dateDistance(goalLogInfo)
                titleText.text = goalTitle

                var companion = when {
                    goalLogInfo.goal.parent != null -> "${goalLogInfo.goal.parent!!.author.username} 님 외 ${goalLogInfo.companionNum}명이 함께하는 중"
                    goalLogInfo.companionNum != 0 -> "${goalLogInfo.companionNum}명이 함께하는 중"
                    else -> "함께 해보세요!"
                }

                companionText.text = companion
                text.text = goalLogInfo.content

                likeNumberText.text = goalLogInfo.likeNum.toString()
                commentNumberText.text = goalLogInfo.commentNum.toString()

                if (goalLogInfo.comments.content.size > 0) {
                    commentGoalLogLayout1.findViewById<TextView>(R.id.commentWriterText).text = goalLogInfo.comments.content[0].author.username
                    commentGoalLogLayout1.findViewById<TextView>(R.id.commentText).text = goalLogInfo.comments.content[0].content
                    commentGoalLogLayout1.visibility = View.VISIBLE
                } else {
                    commentGoalLogLayout1.visibility = View.GONE
                }

                if (goalLogInfo.comments.content.size > 1) {
                    commentGoalLogLayout2.findViewById<TextView>(R.id.commentWriterText).text = goalLogInfo.comments.content[1].author.username
                    commentGoalLogLayout2.findViewById<TextView>(R.id.commentText).text = goalLogInfo.comments.content[1].content
                    commentGoalLogLayout2.visibility = View.VISIBLE
                } else {
                    commentGoalLogLayout2.visibility = View.GONE
                }

                titleText.setOnClickListener(IntentListener.toGoalListener(goalLogInfo.goal.id))
                textLayout.setOnClickListener(IntentListener.toGoalLogListener(goalLogInfo.id))
                commentGoalLogLayout1.setOnClickListener(IntentListener.toGoalLogListener(goalLogInfo.id))
                commentGoalLogLayout2.setOnClickListener(IntentListener.toGoalLogListener(goalLogInfo.id))
                commentNumberText.setOnClickListener(IntentListener.toGoalLogListener(goalLogInfo.id))
                likeNumberText.setOnClickListener(IntentListener.toGoalLogLikeListListener(goalLogInfo.id))

                when {
                    goalLogInfo.goal.parent != null -> companionText.setOnClickListener(IntentListener.toGoalListener(goalLogInfo.goal.parent!!.id))
                    else -> companionText.setOnClickListener(IntentListener.toGoalListener(goalLogInfo.goal.id))
                }

                // post like
                likeButton.setOnClickListener {
                    sendLikeForGoalLog(goalLogInfo.id)
                }

                // post comment
                commentGoalLogWriteLayout.findViewById<Button>(R.id.postBtn).setOnClickListener {
                    val content = commentGoalLogWriteLayout.findViewById<EditText>(R.id.commentText).text.toString()
                    // TODO: 내용이 없을 경우 포스트 안되도록
                    val postedComment = CommentFactory.Post(content)
                    sendCommentForGoalLog(goalLogInfo.id, postedComment)
                }


                Log.d("ViewHolder", goalLogInfo.toString())
            }
        }
    }


    override fun getItemCount(): Int = goalLogInfos.size

    fun appendGoalLogInfos(moreGoalLogInfos: List<GoalLogInfo>) {
        val index = this.goalLogInfos.size
        goalLogInfos.addAll(moreGoalLogInfos)
        notifyItemRangeInserted(index, goalLogInfos.size)
    }
}
