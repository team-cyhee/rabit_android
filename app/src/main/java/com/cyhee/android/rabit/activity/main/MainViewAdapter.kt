package com.cyhee.android.rabit.activity.main

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.goallog.GoalLogActivity
import com.cyhee.android.rabit.base.BaseViewHolder
import com.cyhee.android.rabit.model.*
import kotlinx.android.synthetic.main.item_complete_maingoal.*
import kotlinx.android.synthetic.main.item_complete_maingoallog.*
import kotlinx.android.synthetic.main.item_part_goalwriter.*
import kotlinx.android.synthetic.main.item_part_reaction.*
import kotlinx.android.synthetic.main.item_part_text.*
import java.lang.Exception

class MainViewAdapter (
    private val mainInfos: MutableList<MainInfo>,
    private val sendCommentForGoal: (Long, CommentFactory.Post) -> Unit,
    private val sendCommentForGoalLog: (Long, CommentFactory.Post) -> Unit
) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        if (mainInfos[position].type == ContentType.GOAL) {
            return 0
        }
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == 0) {
            return MainViewHolderForGoal(parent)
        } else if (viewType == 1) {
            return MainViewHolderForGoalLog(parent)
        }
        throw Exception("goal 또는 goallog만 들어와야함")
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                mainInfos[position].let { mainInfo ->
                    with(holder as MainViewHolderForGoal) {
                        var goalInfo: GoalInfo = mainInfo as GoalInfo
                        nameText.text = goalInfo.author.username
                        titleText.text = goalInfo.content
                        if (goalInfo.parent != null) {
                            // TODO: 사람 수 추가
                            val original = "${goalInfo.parent!!.author.username} 님 외 n명이 함께하는 중"
                            originalWriterText.text = original
                        }

                        // 좋아요 수, 댓글 수
                        likeNumberText.text = goalInfo.likeNum.toString()
                        commentNumberText.text = goalInfo.commentNum.toString()

                        // 댓글 2개
                        if (goalInfo.comments.content.size > 0) {
                            commentGoalLayout1.findViewById<TextView>(R.id.commentWriterText).text = goalInfo.comments.content[0].author.username
                            commentGoalLayout1.findViewById<TextView>(R.id.commentText).text = goalInfo.comments.content[0].content
                        } else {
                            commentGoalLayout1.visibility = View.GONE
                        }

                        if (goalInfo.comments.content.size > 1) {
                            commentGoalLayout2.findViewById<TextView>(R.id.commentWriterText).text = goalInfo.comments.content[1].author.username
                            commentGoalLayout2.findViewById<TextView>(R.id.commentText).text = goalInfo.comments.content[1].content
                        } else {
                            commentGoalLayout2.visibility = View.GONE
                        }
                        // TODO: goalActivity 만든 뒤 수정
                        val clickListener = View.OnClickListener {
                            val intentToGoalLog = Intent(it.context, GoalLogActivity::class.java)
                            intentToGoalLog.putExtra("goalId", mainInfo.id)
                            it.context.startActivity(intentToGoalLog)
                        }

                        // TODO: 좋아요 누르기 추가
                        commentNumberText.setOnClickListener(clickListener)

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
            1 -> {
                mainInfos[position].let { mainInfo ->
                    with(holder as MainViewHolderForGoalLog) {
                        var goalLogInfo: GoalLogInfo = mainInfo as GoalLogInfo
                        nameText.text = goalLogInfo.goal.author.username
                        titleText.text = goalLogInfo.goal.content
                        if (goalLogInfo.goal.parent != null) {
                            // TODO: 사람 수 추가
                            val original = "${goalLogInfo.goal.parent!!.author.username} 님 외 n명이 함께하는 중"
                            originalWriterText.text = original
                        }

                        text.text = goalLogInfo.content

                        likeNumberText.text = goalLogInfo.likeNum.toString()
                        commentNumberText.text = goalLogInfo.commentNum.toString()

                        if (goalLogInfo.comments.content.size > 0) {
                            commentGoalLogLayout1.findViewById<TextView>(R.id.commentWriterText).text = goalLogInfo.comments.content[0].author.username
                            commentGoalLogLayout1.findViewById<TextView>(R.id.commentText).text = goalLogInfo.comments.content[0].content
                        } else {
                            commentGoalLogLayout1.visibility = View.GONE
                        }

                        if (goalLogInfo.comments.content.size > 1) {
                            commentGoalLogLayout2.findViewById<TextView>(R.id.commentWriterText).text = goalLogInfo.comments.content[1].author.username
                            commentGoalLogLayout2.findViewById<TextView>(R.id.commentText).text = goalLogInfo.comments.content[1].content
                        } else {
                            commentGoalLogLayout2.visibility = View.GONE
                        }
                        val clickListener = View.OnClickListener {
                            val intentToGoalLog = Intent(it.context, GoalLogActivity::class.java)
                            intentToGoalLog.putExtra("goalId", mainInfo.id)
                            it.context.startActivity(intentToGoalLog)
                        }

                        textLayout.setOnClickListener(clickListener)
                        commentNumberText.setOnClickListener(clickListener)

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
        }
    }


    override fun getItemCount(): Int = mainInfos.size

    fun appendMainInfos(moreMainInfos: List<MainInfo>) {
        val index = this.mainInfos.size
        mainInfos.addAll(moreMainInfos)
        notifyItemRangeInserted(index, mainInfos.size)
    }
}
